/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.annotated;

import com.github.nnest.arcteryx.spring.AnnotatedApplication;

/**
 * @author brad.wu
 *
 */
@AppInLayerExtend(id = ShopExtend.ID)
public class ShopExtend extends AnnotatedApplication {
	public static final String ID = Shop.ID;
}
