/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import org.springframework.context.ApplicationContext;

import com.github.nnest.arcteryx.AbstractResource;

/**
 * class reference resource, which refers to a class
 * 
 * @author brad.wu
 */
public class ClassReferenceResource extends AbstractResource implements IClassReferenceResource {
	private ApplicationContext applicationContext = null;
	private String referenceBeanId = null;
	private Class<?> referenceClass = null;
	private String containerBeanId;

	public ClassReferenceResource(ApplicationContext applicationContext, String referenceBeanId,
			Class<?> referenceClass) {
		super(null);
		this.setApplicationContext(applicationContext);
		this.setReferenceBeanId(referenceBeanId);
		this.setReferenceClass(referenceClass);
		this.setId(this.determineId());
		this.containerBeanId = AnnotatedResourceUtils.determineContainerId(this.getReferenceClass());
		this.setLayer(AnnotatedResourceUtils.determineLayer(this.getReferenceClass()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.AbstractResource#assertIdNotEmpty(java.lang.String)
	 */
	@Override
	protected void assertIdNotEmpty(String id) {
	}

	/**
	 * determine resource id from annotation
	 * 
	 * @return
	 */
	protected String determineId() {
		return AnnotatedResourceUtils.determineResourceId(this.getReferenceClass());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IAnnotatedResource#getContainerBeanId()
	 */
	public String getContainerBeanId() {
		return this.containerBeanId;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IClassReferenceResource#getApplicationContext()
	 */
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	protected void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IClassReferenceResource#getReferenceBeanId()
	 */
	public String getReferenceBeanId() {
		return this.referenceBeanId;
	}

	/**
	 * @param referenceBeanId
	 *            the referenceBeanId to set
	 */
	protected void setReferenceBeanId(String referenceBeanId) {
		this.referenceBeanId = referenceBeanId;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IClassReferenceResource#getReferenceClass()
	 */
	public Class<?> getReferenceClass() {
		return this.referenceClass;
	}

	/**
	 * @param referenceClass
	 *            the referenceClass to set
	 */
	protected void setReferenceClass(Class<?> referenceClass) {
		this.referenceClass = referenceClass;
	}
}
