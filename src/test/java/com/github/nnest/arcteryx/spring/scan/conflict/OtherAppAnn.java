/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.conflict;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.nnest.arcteryx.spring.stereotype.AnApplication;

/**
 * @author brad.wu
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@AnApplication
@LayerA
public @interface OtherAppAnn {
	/**
	 * application id, default zero length string
	 * 
	 * @return
	 */
	String id() default "";
}
