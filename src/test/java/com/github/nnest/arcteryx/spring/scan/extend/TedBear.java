/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import com.github.nnest.arcteryx.AbstractResource;

/**
 * @author brad.wu
 */
@ResInLayerTop(id = TedBear.ID, containerId = ToySaler.ID)
public class TedBear extends AbstractResource {
	public static final String ID = "TedBear";

	public TedBear() {
		super(ID);
	}
}
