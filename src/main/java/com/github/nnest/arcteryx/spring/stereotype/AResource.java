/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A resource
 * 
 * @author brad.wu
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface AResource {
}
