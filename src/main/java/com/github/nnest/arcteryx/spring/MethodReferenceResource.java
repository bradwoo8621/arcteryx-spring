/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

/**
 * class reference resource, which refers to a class
 * 
 * @author brad.wu
 */
public class MethodReferenceResource extends ClassReferenceResource implements IMethodReferenceResource {
	public static final char METHOD_BEAN_ID_SEPARATOR = '#';
	private Method referenceMethod = null;

	public MethodReferenceResource(ApplicationContext applicationContext, String referenceBeanId,
			Method referenceMethod) {
		this.setApplicationContext(applicationContext);
		this.setReferenceBeanId(referenceBeanId);
		this.setReferenceClass(referenceMethod.getDeclaringClass());
		this.setReferenceMethod(referenceMethod);
		this.setId(this.determineId());
		this.setContainerBeanId(AnnotatedResourceUtils.determineContainerId(this.getReferenceClass()));
		this.setLayer(AnnotatedResourceUtils.determineLayer(this.getReferenceClass()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.ClassReferenceResource#determineId()
	 */
	@Override
	protected String determineId() {
		return StringUtils.join(new String[] { super.determineId(), this.getReferenceMethod().getName() },
				METHOD_BEAN_ID_SEPARATOR);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IMethodReferenceResource#getReferenceMethod()
	 */
	public Method getReferenceMethod() {
		return referenceMethod;
	}

	/**
	 * @param referenceMethod
	 *            the referenceMethod to set
	 */
	protected void setReferenceMethod(Method referenceMethod) {
		this.referenceMethod = referenceMethod;
	}
}
