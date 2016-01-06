/**
 * 
 */
package com.github.nnest.arcteryx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A resource registration listener
 * 
 * @author brad.wu
 */
@Target(TYPE)
@Retention(RUNTIME)
@AResourceListener
public @interface AResourceRegistrationListener {
	/**
	 * get container id
	 * 
	 * @return
	 */
	String containerId();
}
