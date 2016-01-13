/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;

/**
 * resource definition resolver
 * 
 * @author brad.wu
 */
public interface IResourceDefinitionResolver {
	/**
	 * create resource by given parameters
	 * 
	 * @param applicationContext
	 * @param beanDefinition
	 *            bean definition in application context
	 * @param beanId
	 *            original bean id in application context
	 * @param annotationClass
	 *            annotation class which auto scanner according to
	 * @return
	 */
	IAnnotatedResource[] createResource(ApplicationContext applicationContext, BeanDefinition beanDefinition,
			String beanId, Class<? extends Annotation> annotationClass);
}
