/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.branch;

import com.github.nnest.arcteryx.Application;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.scan.base.Shop;
import com.github.nnest.arcteryx.spring.stereotype.AnApplication;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 */
@AnApplication
@ResourcePath(BranchShop.PATH)
public class BranchShop extends Application {
	public static final String ID = Shop.ID;
	public static final String PATH = Branch.ID + IResource.SEPARATOR + ID;

	public BranchShop() {
		super(ID);
	}
}
