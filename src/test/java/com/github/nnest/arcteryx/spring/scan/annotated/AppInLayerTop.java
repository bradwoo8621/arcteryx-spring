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
 * declare the annotation as an application, each class defined with this
 * annotation is an application.</br>
 * declare the customized {@linkplain OneLayer} to identify the layer prefix
 * 
 * @author brad.wu
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@AnApplication
@LayerTop
public @interface AppInLayerTop {
	/**
	 * application id, default zero length string
	 * 
	 * @return
	 */
	String id() default "";
}
