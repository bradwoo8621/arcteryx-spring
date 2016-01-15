/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

/**
 * Stereo types. </br>
 * Define names of resource annotation types
 * 
 * @author brad.wu
 */
public interface IStereoTypes {
	/**
	 * resource annotation type
	 */
	String RESOURCE_ANNOTATION_TYPE = AResource.class.getName();
	/**
	 * component annotation type
	 */
	String COMPONENT_ANNOTATION_TYPE = AComponent.class.getName();
	/**
	 * application annotation type
	 */
	String APPLICATION_ANNOTATION_TYPE = AnApplication.class.getName();
	/**
	 * system annotation type
	 */
	String SYSTEM_ANNOTATION_TYPE = ASystem.class.getName();
}
