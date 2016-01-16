/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.base;

import com.github.nnest.arcteryx.AbstractResource;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.stereotype.AResource;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 *
 */
@AResource
@ResourcePath(Ted.PATH)
public class Ted extends AbstractResource {
	public static final String ID = "Ted";
	public static final String PATH = Toy.PATH + IResource.SEPARATOR + ID;

	public Ted() {
		super(ID);
	}
}
