/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * A resource listener
 * 
 * @author brad.wu
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
@Component
public @interface AResourceListener {
}
