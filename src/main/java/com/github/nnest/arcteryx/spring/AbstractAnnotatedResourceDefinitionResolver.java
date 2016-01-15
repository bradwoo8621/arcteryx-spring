/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.annotation.Annotation;

/**
 * annotated resource definition resolver
 * 
 * @author brad.wu
 */
public abstract class AbstractAnnotatedResourceDefinitionResolver implements IAnnotatedResourceDefinitionResolver {
	private Class<? extends Annotation> annotationClass = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IResourceDefinitionResolver#getAnnotationClass()
	 */
	public Class<? extends Annotation> getAnnotationClass() {
		return this.annotationClass;
	}

	/**
	 * set annotation class.
	 * 
	 * @param annotationClass
	 */
	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}
}
