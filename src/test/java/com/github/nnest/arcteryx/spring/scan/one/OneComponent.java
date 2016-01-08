/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.one;

import com.github.nnest.arcteryx.Component;
import com.github.nnest.arcteryx.spring.annotation.AComponent;

/**
 * @author brad.wu
 */
@AComponent(applicationId = "Shop")
public class OneComponent extends Component {
	public OneComponent(String id) {
		super("ToySaler");
	}
}
