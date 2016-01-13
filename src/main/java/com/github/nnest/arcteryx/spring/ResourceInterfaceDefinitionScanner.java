/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * resource which defined in interface, scanner.</br>
 * Annotation, regex and assignable class filters (only doc the including
 * filters, excluding filters are same) can be set via
 * {@linkplain #setIncludeAnnotations(String)},
 * {@linkplain #setIncludeRegex(String)},
 * {@linkplain #setIncludeAssignables(String)}. Separator "," is supported
 * for argument for all these set methods.</br>
 * other filters must be set via {@linkplain #setIncludeFilters(List)} or add
 * via {@linkplain #addIncludeFilter(TypeFilter)}
 * 
 * @author brad.wu
 */
public class ResourceInterfaceDefinitionScanner extends ClassPathBeanDefinitionScanner {
	private static class AnnotationTypeFilterCreator implements ITypeFilterCreator {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.ResourceInterfaceDefinitionScanner.ITypeFilterCreator#create(java.lang.String)
		 */
		@SuppressWarnings("unchecked")
		public TypeFilter create(String annotationClassName) {
			try {
				return new AnnotationTypeFilter((Class<? extends Annotation>) Class.forName(annotationClassName), true);
			} catch (ClassNotFoundException e) {
				throw new IllegalResourceDefinitionException("Failed to create annotation filter", e);
			}
		}
	}

	private static class AssignableTypeFilterCreator implements ITypeFilterCreator {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.ResourceInterfaceDefinitionScanner.ITypeFilterCreator#create(java.lang.String)
		 */
		public TypeFilter create(String argument) {
			try {
				return new AssignableTypeFilter(Class.forName(argument));
			} catch (ClassNotFoundException e) {
				throw new IllegalResourceDefinitionException("Failed to create assignable filter", e);
			}
		}
	}

	private static class ExcludeFilterResolver implements ITypeFilterResolver {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.ResourceInterfaceDefinitionScanner.TypeFilterResolver#addIntoScanner(com.github.nnest.arcteryx.spring.ResourceInterfaceDefinitionScanner,
		 *      org.springframework.core.type.filter.TypeFilter)
		 */
		public void addIntoScanner(ResourceInterfaceDefinitionScanner scanner, TypeFilter filter) {
			scanner.addExcludeFilter(filter);
		}
	}

	private static class IncludeFilterResolver implements ITypeFilterResolver {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.ResourceInterfaceDefinitionScanner.TypeFilterResolver#addIntoScanner(com.github.nnest.arcteryx.spring.ResourceInterfaceDefinitionScanner,
		 *      org.springframework.core.type.filter.TypeFilter)
		 */
		public void addIntoScanner(ResourceInterfaceDefinitionScanner scanner, TypeFilter filter) {
			scanner.addIncludeFilter(filter);
		}
	}

	private static interface ITypeFilterCreator {
		/**
		 * create type filter
		 * 
		 * @param argument
		 * @return
		 */
		TypeFilter create(String argument);
	}

	private static interface ITypeFilterResolver {
		/**
		 * add filter into scanner
		 * 
		 * @param scanner
		 * @param filter
		 */
		void addIntoScanner(ResourceInterfaceDefinitionScanner scanner, TypeFilter filter);
	}

	private static class RegexPatternTypeFilterCreator implements ITypeFilterCreator {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.ResourceInterfaceDefinitionScanner.ITypeFilterCreator#create(java.lang.String)
		 */
		public TypeFilter create(String pattern) {
			return new RegexPatternTypeFilter(Pattern.compile(pattern));
		}
	}

	public static final String SEPARATOR_CHAR = ",";
	private static final ITypeFilterResolver INCLUDE_FILTER_RESOLVER = new IncludeFilterResolver();
	private static final ITypeFilterResolver EXCLUDE_FILTER_RESOLVER = new ExcludeFilterResolver();
	private static final ITypeFilterCreator ANNOTATION_FILTER_CREATOR = new AnnotationTypeFilterCreator();
	private static final ITypeFilterCreator REGEX_FILTER_CREATOR = new RegexPatternTypeFilterCreator();
	private static final ITypeFilterCreator ASSIGNABLE_FILTER_CREATOR = new AssignableTypeFilterCreator();

	private String basePackages = null;

	public ResourceInterfaceDefinitionScanner() {
		super(new StaticApplicationContext(), false);
	}

	/**
	 * 
	 * @param arguments
	 * @param filterCreator
	 * @param filterResolver
	 */
	protected void addTypeFilters(String arguments, ITypeFilterCreator filterCreator,
			ITypeFilterResolver filterResolver) {
		if (StringUtils.isEmpty(arguments)) {
			// do nothing
			return;
		}

		String[] argumentArray = StringUtils.split(arguments, SEPARATOR_CHAR);
		for (String argument : argumentArray) {
			if (!StringUtils.isBlank(argument)) {
				filterResolver.addIntoScanner(this, filterCreator.create(argument));
			}
		}
	}

	/**
	 * get application context
	 * 
	 * @return
	 */
	public StaticApplicationContext getApplicationContext() {
		return (StaticApplicationContext) this.getRegistry();
	}

	/**
	 * @return the basePackages
	 */
	public String getBasePackages() {
		return basePackages;
	}

	/**
	 * get logger
	 * 
	 * @return
	 */
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	/**
	 * only interface is accepted
	 * 
	 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#isCandidateComponent(org.springframework.beans.factory.annotation.AnnotatedBeanDefinition)
	 */
	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface();
	}

	/**
	 * scan
	 * 
	 * @return number of beans registered
	 */
	public int scan() {
		String basePackages = this.getBasePackages();
		if (StringUtils.isBlank(basePackages)) {
			// no base packages defined
			this.getLogger().warn("No base packages defined, scan ignored");
			return 0;
		}

		return this.scan(StringUtils.split(basePackages, SEPARATOR_CHAR));
	}

	/**
	 * @param basePackages
	 *            the basePackages to set
	 */
	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

	/**
	 * set bean name generator
	 * 
	 * @param beanNameGenerator
	 */
	public void setBeanNameGenerator(Class<? extends BeanNameGenerator> beanNameGenerator) {
		try {
			super.setBeanNameGenerator(beanNameGenerator.newInstance());
		} catch (Exception e) {
			throw new IllegalResourceDefinitionException("Failed to create bean name generator", e);
		}
	}

	/**
	 * set include annotation filters
	 * 
	 * @param annotationClasses
	 */
	public void setExcludeAnnotations(String annotationClasses) {
		this.addTypeFilters(annotationClasses, ANNOTATION_FILTER_CREATOR, EXCLUDE_FILTER_RESOLVER);
	}

	/**
	 * set include assignable class filters
	 * 
	 * @param assignableClasses
	 */
	public void setExcludeAssignables(String assignableClasses) {
		this.addTypeFilters(assignableClasses, ASSIGNABLE_FILTER_CREATOR, EXCLUDE_FILTER_RESOLVER);
	}

	/**
	 * set exclude filters
	 * 
	 * @param excludeFilters
	 */
	public void setExcludeFilters(List<TypeFilter> excludeFilters) {
		if (excludeFilters != null) {
			for (TypeFilter filter : excludeFilters) {
				this.addExcludeFilter(filter);
			}
		}
	}

	/**
	 * set include regex pattern filters
	 * 
	 * @param patterns
	 */
	public void setExcludeRegex(String patterns) {
		this.addTypeFilters(patterns, REGEX_FILTER_CREATOR, EXCLUDE_FILTER_RESOLVER);
	}

	/**
	 * set include annotation filters
	 * 
	 * @param annotationClasses
	 */
	public void setIncludeAnnotations(String annotationClasses) {
		this.addTypeFilters(annotationClasses, ANNOTATION_FILTER_CREATOR, INCLUDE_FILTER_RESOLVER);
	}

	/**
	 * set include assignable class filters
	 * 
	 * @param assignableClasses
	 */
	public void setIncludeAssignables(String assignableClasses) {
		this.addTypeFilters(assignableClasses, ASSIGNABLE_FILTER_CREATOR, INCLUDE_FILTER_RESOLVER);
	}

	/**
	 * set include filters
	 * 
	 * @param includeFilters
	 */
	public void setIncludeFilters(List<TypeFilter> includeFilters) {
		if (includeFilters != null) {
			for (TypeFilter filter : includeFilters) {
				this.addIncludeFilter(filter);
			}
		}
	}

	/**
	 * set include regex pattern filters
	 * 
	 * @param patterns
	 */
	public void setIncludeRegex(String patterns) {
		this.addTypeFilters(patterns, REGEX_FILTER_CREATOR, INCLUDE_FILTER_RESOLVER);
	}
}
