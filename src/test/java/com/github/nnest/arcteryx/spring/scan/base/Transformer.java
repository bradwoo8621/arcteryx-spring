/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.base;

import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.stereotype.AClassReferResource;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * do not use the predefined annotation like {@linkplain AResource}, to prevent
 * interference when resolve resource's definition
 * 
 * @author brad.wu
 */
@AClassReferResource
@ResourcePath(Transformer.PATH)
public class Transformer {
	public static final String ID = "Transformer";
	public static final String PATH = Toy.PATH + IResource.SEPARATOR + ID;

	public Transformer() {
	}
}
