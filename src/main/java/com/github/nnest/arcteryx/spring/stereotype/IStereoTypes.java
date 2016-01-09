/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

/**
 * Stereo types. </br>
 * Define names of resource/layer annotation types
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
	 * id property name of resource/component/application annotations
	 */
	String RESOURCE_ANNOTATION_PROP_ID = "id";
	/**
	 * container id property name of resource/component/application annotations
	 */
	String CONTAINER_ANNOTATION_PROP_ID = "containerId";

	/**
	 * layer annotation type
	 */
	String LAYER_ANNOTATION_TYPE = Layer.class.getName();
	/**
	 * id property name of layer annotation
	 */
	String LAYER_ANNOTATION_PROP_ID = "id";

	/**
	 * parent id property name of layer annotation
	 */
	String LAYER_PARENT_ANNOTATION_PROP_ID = "parentId";
}
