/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import org.springframework.context.ApplicationContext;

/**
 * Class reference resource
 * 
 * @author brad.wu
 */
public interface IClassReferenceResource extends IAnnotatedResource {
	/**
	 * get reference class
	 * 
	 * @return
	 */
	Class<?> getReferenceClass();

	/**
	 * get reference bean id
	 * 
	 * @return
	 */
	String getReferenceBeanId();

	/**
	 * get application context
	 * 
	 * @return
	 */
	ApplicationContext getApplicationContext();

}
