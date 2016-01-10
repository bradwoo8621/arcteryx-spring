/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import com.github.nnest.arcteryx.Application;

/**
 * @author brad.wu
 *
 */
@AppInLayerExtend(id = ShopExtend.ID)
public class ShopExtend extends Application {
	public static final String ID = Shop.ID;

	public ShopExtend() {
		super(ShopExtend.ID);
	}
}
