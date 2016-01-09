/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.conflict;

import com.github.nnest.arcteryx.Application;
import com.github.nnest.arcteryx.spring.stereotype.Layer;

/**
 * {@linkplain LayerB} is nearest, so read {@linkplain Layer} from
 * {@linkplain LayerB}.</br>
 * But application bean id are defined in {@linkplain OneAppAnn} and
 * {@linkplain OtherAppAnn}, and doesn't equal.
 * 
 * @author brad.wu
 */
@LayerB
@OneAppAnn(id = OneApplication.ID)
@OtherAppAnn(id = "Other")
public class OneApplication extends Application {
	public static final String ID = "Shop";

	public OneApplication() {
		super(ID);
	}
}
