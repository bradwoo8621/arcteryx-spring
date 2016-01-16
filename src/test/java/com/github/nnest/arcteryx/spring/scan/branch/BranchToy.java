/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.branch;

import com.github.nnest.arcteryx.Component;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.scan.base.Toy;
import com.github.nnest.arcteryx.spring.stereotype.AComponent;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 *
 */
@AComponent
@ResourcePath(BranchToy.PATH)
public class BranchToy extends Component {
	public static final String ID = Toy.ID;
	public static final String PATH = BranchShop.PATH + IResource.SEPARATOR + ID;

	public BranchToy() {
		super(ID);
	}
}
