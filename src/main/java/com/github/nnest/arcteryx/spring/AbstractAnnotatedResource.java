/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import com.github.nnest.arcteryx.AbstractResource;

/**
 * abstract annotated resource
 * 
 * @author brad.wu
 */
public abstract class AbstractAnnotatedResource extends AbstractResource implements IAnnotatedResource {
	private String containerBeanId = null;

	public AbstractAnnotatedResource() {
		super(null);
		this.setLayer(AnnotatedResourceUtils.determineLayer(this.getClass()));
		this.containerBeanId = AnnotatedResourceUtils.determineContainerId(this.getClass());
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
	 * @see com.github.nnest.arcteryx.spring.IAnnotatedResource#getContainerBeanId()
	 */
	public String getContainerBeanId() {
		return this.containerBeanId;
	}
}
