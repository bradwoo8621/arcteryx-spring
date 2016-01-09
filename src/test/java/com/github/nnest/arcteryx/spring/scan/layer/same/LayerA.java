/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.layer.same;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.nnest.arcteryx.spring.stereotype.Layer;

/**
 * @author brad.wu
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Layer(layerId = "one")
public @interface LayerA {
}
