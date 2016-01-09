/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.one;

import com.github.nnest.arcteryx.AbstractResource;

/**
 * @author brad.wu
 */
@OneResAnn(id = OneResource.ID, containerId = OneComponent.ID)
public class OneResource extends AbstractResource {
	public static final String ID = "TedBear";

	public OneResource() {
		super(ID);
	}
}
