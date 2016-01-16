/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.branch;

import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.scan.base.ToySaler;
import com.github.nnest.arcteryx.spring.stereotype.AMethodReferResource;
import com.github.nnest.arcteryx.spring.stereotype.AResource;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 */
@AMethodReferResource
@ResourcePath(BranchToySaler.PATH)
public interface BranchToySaler extends ToySaler {
	public static final String ID = ToySaler.ID;
	public static final String PATH = BranchToy.PATH + IResource.SEPARATOR + ID;

	@AResource
	void guide();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.scan.base.ToySaler#sell()
	 */
	@AResource
	void sell();
}
