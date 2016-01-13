/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * abstract annotated resource definition resolver
 * 
 * @author brad.wu
 */
public abstract class AbstractAnnotatedResourceDefinitionResolver implements IAnnotatedResourceDefinitionResolver {
	private List<Class<? extends Annotation>> annotationClasses = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IAnnotatedResourceDefinitionResolver#getAnnotationClasses()
	 */
	public List<Class<? extends Annotation>> getAnnotationClasses() {
		if (this.annotationClasses == null) {
			return Collections.emptyList();
		} else {
			return this.annotationClasses;
		}
	}

	/**
	 * @param annotationClasses
	 *            the annotationClasses to set
	 */
	public void setAnnotationClasses(List<Class<? extends Annotation>> annotationClasses) {
		this.annotationClasses = annotationClasses;
	}

	/**
	 * set annotation class.</br>
	 * replace original all
	 * 
	 * @param annotationClass
	 */
	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		List<Class<? extends Annotation>> list = new ArrayList<Class<? extends Annotation>>();
		list.add(annotationClass);
		this.setAnnotationClasses(list);
	}
}
