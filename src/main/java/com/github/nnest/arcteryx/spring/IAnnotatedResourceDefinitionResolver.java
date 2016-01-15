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
public interface IAnnotatedResourceDefinitionResolver extends IResourceDefinitionResolver {
	/**
	 * get regardful annotation class
	 * 
	 * @return
	 */
	Class<? extends Annotation> getAnnotationClass();

}
