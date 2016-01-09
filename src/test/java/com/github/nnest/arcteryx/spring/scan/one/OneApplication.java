/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.one;

import com.github.nnest.arcteryx.Application;

/**
 * @author brad.wu
 */
@OneAppAnn(id = OneApplication.ID)
public class OneApplication extends Application {
	public static final String ID = "Shop";

	public OneApplication() {
		super(ID);
	}
}
