/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.annotated;

import com.github.nnest.arcteryx.spring.stereotype.AResource;

/**
 * @author brad.wu
 */
@ServiceInLayerExtend(id = IGuideService.ID, containerId = ToySalerExtend.ID)
public interface IGuideService {
	String ID = "GuideService";
	
	@AResource
	void hello();
}
