/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;

import com.github.nnest.arcteryx.spring.stereotype.AResource;

/**
 * Method reference resource definition resolver
 * 
 * @author brad.wu
 */
public class MethodReferenceResourceDefinitionResolver implements IResourceDefinitionResolver {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IResourceDefinitionResolver#createResource(org.springframework.context.ApplicationContext,
	 *      org.springframework.beans.factory.config.BeanDefinition,
	 *      java.lang.String, java.lang.Class)
	 */
	public IAnnotatedResource[] createResource(ApplicationContext applicationContext, BeanDefinition beanDefinition,
			String beanId, Class<? extends Annotation> annotationClass) {
		Class<?> beanClass;
		try {
			beanClass = Class.forName(beanDefinition.getBeanClassName());
		} catch (ClassNotFoundException e) {
			throw new IllegalResourceDefinitionException(e);
		}

		List<IAnnotatedResource> resources = new ArrayList<IAnnotatedResource>();
		Method[] methods = beanClass.getDeclaredMethods();
		if (methods != null) {
			for (Method method : methods) {
				if (method.isAnnotationPresent(AResource.class)) {
					resources.add(new MethodReferenceResource(applicationContext, beanId, method));
				}
			}
		}
		return resources.toArray(new IAnnotatedResource[resources.size()]);
	}

}
