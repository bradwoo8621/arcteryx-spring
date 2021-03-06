/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

import static java.lang.annotation.ElementType.TYPE;
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
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@AResource
public @interface AComponent {
}
