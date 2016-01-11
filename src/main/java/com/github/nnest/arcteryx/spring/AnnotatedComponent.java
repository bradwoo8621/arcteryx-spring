/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import com.github.nnest.arcteryx.Component;

/**
 * Annotated component
 * 
 * @author brad.wu
 */
public class AnnotatedComponent extends Component implements IAnnotatedResource {
	public AnnotatedComponent() {
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
	 * @see com.github.nnest.arcteryx.spring.IAnnotatedResource#getContainerId()
	 */
	public String getContainerId() {
		return AnnotatedResourceUtils.determineContainerId(this.getClass());
	}
}
