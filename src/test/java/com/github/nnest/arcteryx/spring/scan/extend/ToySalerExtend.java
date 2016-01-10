/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import com.github.nnest.arcteryx.Component;

/**
 * @author brad.wu
 */
@CompInLayerExtend(id = ToySalerExtend.ID)
public class ToySalerExtend extends Component {
	public static final String ID = ToySaler.ID;

	public ToySalerExtend() {
		super(ToySalerExtend.ID);
	}
}
