/**
 * 
 */
package com.github.nnest.arcteryx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A component.<br/>
 * Class which annotated by this should be treated as a component.
 * 
 * @author brad.wu
 */
@Target({ TYPE, ANNOTATION_TYPE, METHOD })
@Retention(RUNTIME)
@Documented
@AResource
public @interface AComponent {
	/**
	 * get id
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * get application id
	 * 
	 * @return
	 */
	String applicationId();
}
