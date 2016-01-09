/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.layer.same;

import com.github.nnest.arcteryx.Application;

/**
 * @author brad.wu
 */
@LayerB
@OneAppAnn(id = OneApplication.ID)
public class OneApplication extends Application {
	public static final String ID = "Shop";

	public OneApplication() {
		super(ID);
	}
}
