/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.reflect.Method;

/**
 * Method reference resource
 * 
 * @author brad.wu
 */
public interface IMethodReferenceResource extends IClassReferenceResource {
	char METHOD_BEAN_ID_SEPARATOR = '#';
	/**
	 * get reference method
	 * 
	 * @return
	 */
	Method getReferenceMethod();
}
