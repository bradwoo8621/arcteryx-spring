/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.stereotype.AResource;
import com.github.nnest.arcteryx.spring.stereotype.StereoTypeHelper;

/**
 * Method reference resource definition resolver
 * 
 * @author brad.wu
 */
public class MethodReferenceResourceDefinitionResolver extends AbstractAnnotatedResourceDefinitionResolver {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IResourceDefinitionResolver#createResource(org.springframework.context.ApplicationContext)
	 */
	public Map<String, IResource> createResource(ApplicationContext applicationContext) {
		Map<String, IResource> resourceMap = new HashMap<String, IResource>();

		ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) applicationContext;
		ConfigurableListableBeanFactory beanFactory = configurableContext.getBeanFactory();

		String[] beanIds = applicationContext.getBeanNamesForAnnotation(this.getAnnotationClass());
		if (beanIds != null) {
			for (String beanId : beanIds) {
				BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanId);
				String beanClassName = beanDefinition.getBeanClassName();
				Class<?> beanClass = null;
				try {
					beanClass = Class.forName(beanClassName);
				} catch (ClassNotFoundException e) {
					throw new IllegalResourceDefinitionException(e);
				}

				String path = StereoTypeHelper.determineResourcePath(beanClass);
				if (StringUtils.isBlank(path)) {
					throw new IllegalResourceDefinitionException(
							"Path of resource [" + beanClass + "] cannot be blank");
				}
				String[] pathSegments = StringUtils.split(path, IResource.SEPARATOR_CHAR);
				String idPrefix = pathSegments[pathSegments.length - 1];

				Method[] methods = beanClass.getDeclaredMethods();
				if (methods != null) {
					for (Method method : methods) {
						if (method.isAnnotationPresent(AResource.class)) {
							IMethodReferenceResource resource = new MethodReferenceResource(applicationContext, beanId,
									method);
							resource.setId(
									idPrefix + IMethodReferenceResource.METHOD_BEAN_ID_SEPARATOR + method.getName());
							resourceMap.put(path, resource);
						}
					}
				}
			}
		}

		return resourceMap;
	}

}
