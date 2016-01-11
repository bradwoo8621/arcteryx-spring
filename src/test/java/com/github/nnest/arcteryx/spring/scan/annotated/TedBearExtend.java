/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.annotated;

import com.github.nnest.arcteryx.spring.AbstractAnnotatedResource;

/**
 * @author brad.wu
 */
@ResInLayerExtend(id = TedBearExtend.ID, containerId = ToySalerExtend.ID)
public class TedBearExtend extends AbstractAnnotatedResource {
	public static final String ID = TedBear.ID;
}
