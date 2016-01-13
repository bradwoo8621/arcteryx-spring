/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.annotated;

import com.github.nnest.arcteryx.spring.stereotype.AResource;

/**
 * @author brad.wu
 *
 */
@ServiceInLayerExtend(id = AccountService.ID, containerId = ToySalerExtend.ID)
public class AccountService {
	public final static String ID = "AccountService";

	@AResource
	public void collect() {
	}

	@AResource
	public void save() {
	}
}
