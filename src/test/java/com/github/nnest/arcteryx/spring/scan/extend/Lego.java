/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import com.github.nnest.arcteryx.AbstractResource;

/**
 * @author brad.wu
 */
@ResInLayerExtend(id = Lego.ID, containerId = ToySalerExtend.ID)
public class Lego extends AbstractResource {
	public static final String ID = "Lego";

	public Lego() {
		super(ID);
	}
}
