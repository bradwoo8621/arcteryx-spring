/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.one;

import com.github.nnest.arcteryx.Application;
import com.github.nnest.arcteryx.spring.annotation.AnApplication;

/**
 * @author brad.wu
 */
@AnApplication
public class OneApplication extends Application {
	public OneApplication() {
		super("Shop");
	}
}
