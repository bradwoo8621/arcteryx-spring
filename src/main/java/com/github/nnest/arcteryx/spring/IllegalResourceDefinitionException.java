/**
 * 
 */
package com.github.nnest.arcteryx.spring;

/**
 * illegal resource definition exception
 * 
 * @author brad.wu
 */
public class IllegalResourceDefinitionException extends RuntimeException {
	private static final long serialVersionUID = 4621063703148196370L;

	public IllegalResourceDefinitionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalResourceDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalResourceDefinitionException(String message) {
		super(message);
	}

	public IllegalResourceDefinitionException(Throwable cause) {
		super(cause);
	}
}
