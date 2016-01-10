/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import com.github.nnest.arcteryx.AbstractResource;

/**
 * @author brad.wu
 */
@ResInLayerExtend(id = TedBearExtend.ID, containerId = ToySalerExtend.ID)
public class TedBearExtend extends AbstractResource {
	public static final String ID = TedBear.ID;

	public TedBearExtend() {
		super(ID);
	}
}
