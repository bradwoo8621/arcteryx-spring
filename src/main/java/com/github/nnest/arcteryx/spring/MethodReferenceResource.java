/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

/**
 * class reference resource, which refers to a class
 * 
 * @author brad.wu
 */
public class MethodReferenceResource extends ClassReferenceResource implements IMethodReferenceResource {
	private Method referenceMethod = null;

	public MethodReferenceResource(ApplicationContext applicationContext, String referenceBeanId,
			Method referenceMethod) {
		this.setApplicationContext(applicationContext);
		this.setReferenceBeanId(referenceBeanId);
		this.setReferenceClass(referenceMethod.getDeclaringClass());
		this.setReferenceMethod(referenceMethod);
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
