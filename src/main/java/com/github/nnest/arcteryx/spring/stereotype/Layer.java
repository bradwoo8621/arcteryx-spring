/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author brad.wu
 */
@Target({ ANNOTATION_TYPE, TYPE })
@Retention(RUNTIME)
@Documented
public @interface Layer {
	/**
	 * get layer id of resource
	 * 
	 * @return
	 */
	String layerId();

	/**
	 * get parent layer id
	 * 
	 * @return
	 */
	String parentLayerId() default "";
}
