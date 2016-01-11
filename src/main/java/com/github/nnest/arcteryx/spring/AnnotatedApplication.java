/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import com.github.nnest.arcteryx.Application;

/**
 * Annotated application
 * 
 * @author brad.wu
 */
public class AnnotatedApplication extends Application implements IAnnotatedResource {
	public AnnotatedApplication() {
		super(null);
		this.setLayer(AnnotatedResourceUtils.determineLayer(this.getClass()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.AbstractResource#setId(java.lang.String)
	 */
	@Override
	protected void setId(String id) {
		super.setId(this.determineId());
	}

	/**
	 * determine resource id from annotation
	 * 
	 * @return
	 */
	protected String determineId() {
		return AnnotatedResourceUtils.determineResourceId(this.getClass());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IAnnotatedResource#getContainerId()
	 */
	public String getContainerId() {
		return this.getId();
	}
}
