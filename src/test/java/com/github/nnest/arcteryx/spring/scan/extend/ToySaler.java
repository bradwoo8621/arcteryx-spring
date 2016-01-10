/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import com.github.nnest.arcteryx.Component;

/**
 * @author brad.wu
 */
@CompInLayerTop(id = ToySaler.ID)
public class ToySaler extends Component {
	public static final String ID = "ToySaler";

	public ToySaler() {
		super(ID);
	}
}
