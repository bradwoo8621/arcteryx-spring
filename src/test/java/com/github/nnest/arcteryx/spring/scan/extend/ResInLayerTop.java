/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.nnest.arcteryx.spring.stereotype.AResource;

/**
 * @author brad.wu
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@AResource
@LayerTop
public @interface ResInLayerTop {
	/**
	 * component id, default zero length string
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * container id
	 * 
	 * @return
	 */
	String containerId();
}
