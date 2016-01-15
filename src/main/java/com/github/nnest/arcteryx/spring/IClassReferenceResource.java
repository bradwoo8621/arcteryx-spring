/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import org.springframework.context.ApplicationContext;

import com.github.nnest.arcteryx.IResource;

/**
 * Class reference resource
 * 
 * @author brad.wu
 */
public interface IClassReferenceResource extends IResource {
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

	/**
	 * set id
	 * 
	 * @param id
	 */
	void setId(String id);
}
