/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.extend;

import com.github.nnest.arcteryx.Application;

/**
 * @author brad.wu
 */
@AppInLayerTop(id = Shop.ID)
public class Shop extends Application {
	public static final String ID = "Shop";

	public Shop() {
		super(ID);
	}
}
