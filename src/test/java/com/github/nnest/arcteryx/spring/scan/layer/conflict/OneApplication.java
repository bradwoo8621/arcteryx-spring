/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.layer.conflict;

import com.github.nnest.arcteryx.Application;

/**
 * {@linkplain LayerB} is nearest, so read {@linkplain Layer} from
 * {@linkplain LayerB}
 * 
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
