/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.one;

import com.github.nnest.arcteryx.Component;

/**
 * @author brad.wu
 */
@OneCompAnn(id = OneComponent.ID, containerId = OneApplication.ID)
public class OneComponent extends Component {
	public static final String ID = "ToySaler";

	public OneComponent() {
		super(ID);
	}
}
