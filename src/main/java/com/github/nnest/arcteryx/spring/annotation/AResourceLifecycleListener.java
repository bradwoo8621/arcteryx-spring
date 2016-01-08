/**
 * 
 */
package com.github.nnest.arcteryx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A resource lifecycle listener
 * 
 * @author brad.wu
 */
@Target(TYPE)
@Retention(RUNTIME)
@AResourceListener
@Documented
public @interface AResourceLifecycleListener {
	/**
	 * get resource id
	 * 
	 * @return
	 */
	String resourceId();
}
