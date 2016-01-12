/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import com.github.nnest.arcteryx.IResource;

/**
 * Annotated resource.
 * 
 * @author brad.wu
 */
public interface IAnnotatedResource extends IResource {
	/**
	 * get container id
	 * 
	 * @return
	 */
	String getContainerBeanId();
}
