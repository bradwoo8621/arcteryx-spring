/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.base;

import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.stereotype.AResource;
import com.github.nnest.arcteryx.spring.stereotype.AMethodReferResource;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 */
@AMethodReferResource
@ResourcePath(ToySaler.PATH)
public interface ToySaler {
	public static final String ID = "Saler";
	public static final String PATH = Toy.PATH + IResource.SEPARATOR + ID;

	@AResource
	void collect();

	@AResource
	void sell();
}
