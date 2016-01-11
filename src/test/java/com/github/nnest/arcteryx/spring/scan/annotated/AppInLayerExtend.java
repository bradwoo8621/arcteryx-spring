/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.annotated;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.nnest.arcteryx.spring.stereotype.AnApplication;

/**
 * no <code>containerId</code> needed, since extended application must have same
 * id with its parent application.
 * 
 * @author brad.wu
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@AnApplication
@LayerExtend
public @interface AppInLayerExtend {
	/**
	 * application id, default zero length string
	 * 
	 * @return
	 */
	String id() default "";
}
