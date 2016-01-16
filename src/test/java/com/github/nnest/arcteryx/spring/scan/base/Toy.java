/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.base;

import com.github.nnest.arcteryx.Component;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.stereotype.AComponent;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 *
 */
@AComponent
@ResourcePath(Toy.PATH)
public class Toy extends Component {
	public static final String ID = "Toy";
	public static final String PATH = Shop.PATH + IResource.SEPARATOR + ID;

	public Toy() {
		super(ID);
	}
}
