/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.base;

import com.github.nnest.arcteryx.System;
import com.github.nnest.arcteryx.spring.stereotype.ASystem;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 */
@ASystem
@ResourcePath(Base.ID)
public class Base extends System {
	public static final String ID = "Base";

	public Base() {
		super(ID);
	}
}
