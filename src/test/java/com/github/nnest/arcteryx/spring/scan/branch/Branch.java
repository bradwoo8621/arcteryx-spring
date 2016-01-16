/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.branch;

import com.github.nnest.arcteryx.System;
import com.github.nnest.arcteryx.spring.scan.base.Base;
import com.github.nnest.arcteryx.spring.stereotype.ASystem;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 */
@ASystem(deriveFrom = Base.ID)
@ResourcePath(Branch.ID)
public class Branch extends System {
	public static final String ID = "Branch";

	public Branch() {
		super(ID);
	}
}
