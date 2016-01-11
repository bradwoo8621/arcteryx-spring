/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import org.springframework.core.type.StandardAnnotationMetadata;

import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.ResourceUtils;
import com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective;
import com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.BeanIdDeterminerHelper;

/**
 * Annotated resource utilities
 * 
 * @author brad.wu
 */
public class AnnotatedResourceUtils extends ResourceUtils {
	/**
	 * determine resource id from given class
	 * 
	 * @param resourceClass
	 * @return
	 */
	public static String determineResourceId(Class<? extends IResource> resourceClass) {
		StandardAnnotationMetadata meta = new StandardAnnotationMetadata(resourceClass, true);
		return StereoTypeDetective.determineResourceBeanId(resourceClass.getName(), meta,
				new BeanIdDeterminerHelper(StereoTypeDetective.IGNORE_LAYER_ID_GETTER,
						StereoTypeDetective.RESOURCE_ID_GETTER, StereoTypeDetective.RESOURCE_EXCEPTION_THROWER));
	}

	/**
	 * determine container id from given class
	 * 
	 * @param class1
	 * @return
	 */
	public static String determineContainerId(Class<? extends IResource> resourceClass) {
		StandardAnnotationMetadata meta = new StandardAnnotationMetadata(resourceClass, true);
		return StereoTypeDetective.determineResourceBeanId(resourceClass.getName(), meta,
				new BeanIdDeterminerHelper(StereoTypeDetective.IGNORE_LAYER_ID_GETTER,
						StereoTypeDetective.CONTAINER_ID_GETTER, StereoTypeDetective.CONTAINER_EXCEPTION_THROWER));
	}
}
