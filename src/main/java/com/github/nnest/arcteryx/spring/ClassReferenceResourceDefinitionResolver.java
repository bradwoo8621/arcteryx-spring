/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;

/**
 * Class reference resource definition resolver
 * 
 * @author brad.wu
 */
public class ClassReferenceResourceDefinitionResolver implements IResourceDefinitionResolver {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IResourceDefinitionResolver#createResource(org.springframework.context.ApplicationContext,
	 *      org.springframework.beans.factory.config.BeanDefinition,
	 *      java.lang.String, java.lang.Class)
	 */
	public IAnnotatedResource createResource(ApplicationContext applicationContext, BeanDefinition beanDefinition,
			String beanId, Class<? extends Annotation> annotationClass) {
		try {
			return new ClassReferenceResource(applicationContext, beanId,
					Class.forName(beanDefinition.getBeanClassName()));
		} catch (ClassNotFoundException e) {
			throw new IllegalResourceDefinitionException(e);
		}
	}
}
