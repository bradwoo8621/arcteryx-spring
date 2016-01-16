/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.base;

import com.github.nnest.arcteryx.Application;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.stereotype.AnApplication;
import com.github.nnest.arcteryx.spring.stereotype.ResourcePath;

/**
 * @author brad.wu
 */
@AnApplication
@ResourcePath(Shop.PATH)
public class Shop extends Application {
	public static final String ID = "Shop";
	public static final String PATH = Base.ID + IResource.SEPARATOR + ID;

	public Shop() {
		super(ID);
	}
}
