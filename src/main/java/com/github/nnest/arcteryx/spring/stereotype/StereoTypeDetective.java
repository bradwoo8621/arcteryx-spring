/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

import java.beans.Introspector;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import com.github.nnest.arcteryx.spring.IllegalResourceDefinitionException;

/**
 * stereo type detective
 * 
 * @author brad.wu
 */
public final class StereoTypeDetective {
	public final static String LAYER_NAME_SEPARATOR = "-";

	/**
	 * determine container id of application
	 * 
	 * @param annotatedDefinition
	 * @return
	 */
	public static String determinApplicationContainerId(AnnotatedBeanDefinition annotatedDefinition) {
		AnnotationMetadata meta = annotatedDefinition.getMetadata();
		Set<String> types = meta.getAnnotationTypes();

		String parentLayerId = null;
		String containerId = null;
		for (String type : types) {
			boolean isResourceStereoType = false;
			for (String metaType : meta.getMetaAnnotationTypes(type)) {
				parentLayerId = getParentLayerId(meta, metaType, parentLayerId, annotatedDefinition.getBeanClassName());
				isResourceStereoType = isResourceStereoType || isResourceStereoType(metaType);
			}
			if (isResourceStereoType) {
				if (StringUtils.isEmpty(containerId)) {
					// no container id detected
					containerId = getContainerId(meta, type);
				} else {
					// detect container id even it was already detected
					String containerIdFromAnnotation = getContainerId(meta, type);
					if (StringUtils.equals(containerIdFromAnnotation, containerId)) {
						// do nothing
					} else {
						throw new IllegalResourceDefinitionException(//
								"Conflict container ids [" + containerId + ", " + containerIdFromAnnotation
										+ "] defined in annotation for resource class ["
										+ annotatedDefinition.getBeanClassName() + "]");
					}
				}
			}
			// if layer annotation is defined in class, use its id as layer id
			parentLayerId = getParentLayerId(meta, type, parentLayerId, annotatedDefinition.getBeanClassName());
		}

		return buildResourceBeanId(parentLayerId, containerId);
	}

	/**
	 * determine container id of resource
	 * 
	 * @param annotatedDefinition
	 * @return
	 */
	public static String determineContainerId(AnnotatedBeanDefinition annotatedDefinition) {
		AnnotationMetadata meta = annotatedDefinition.getMetadata();
		Set<String> types = meta.getAnnotationTypes();

		String layerId = null;
		String containerId = null;
		for (String type : types) {
			boolean isResourceStereoType = false;
			for (String metaType : meta.getMetaAnnotationTypes(type)) {
				layerId = getLayerId(meta, metaType, layerId, annotatedDefinition.getBeanClassName());
				isResourceStereoType = isResourceStereoType || isResourceStereoType(metaType);
			}
			if (isResourceStereoType) {
				if (StringUtils.isEmpty(containerId)) {
					// no container id detected
					containerId = getContainerId(meta, type);
				} else {
					// detect container id even it was already detected
					String containerIdFromAnnotation = getContainerId(meta, type);
					if (StringUtils.equals(containerIdFromAnnotation, containerId)) {
						// do nothing
					} else {
						throw new IllegalResourceDefinitionException(//
								"Conflict container ids [" + containerId + ", " + containerIdFromAnnotation
										+ "] defined in annotation for resource class ["
										+ annotatedDefinition.getBeanClassName() + "]");
					}
				}
			}
			// if layer annotation is defined in class, use its id as layer id
			layerId = getLayerId(meta, type, layerId, annotatedDefinition.getBeanClassName());
		}

		return buildResourceBeanId(layerId, containerId);
	}

	/**
	 * determine bean name
	 * 
	 * @param annotatedDefinition
	 * @return
	 */
	public static String determineBeanName(AnnotatedBeanDefinition annotatedDefinition) {
		AnnotationMetadata meta = annotatedDefinition.getMetadata();
		Set<String> types = meta.getAnnotationTypes();

		String layerId = null;
		String beanName = null;
		for (String type : types) {
			boolean isResourceStereoType = false;
			for (String metaType : meta.getMetaAnnotationTypes(type)) {
				layerId = getLayerId(meta, metaType, layerId, annotatedDefinition.getBeanClassName());
				isResourceStereoType = isResourceStereoType || isResourceStereoType(metaType);
			}
			if (isResourceStereoType) {
				if (StringUtils.isEmpty(beanName)) {
					// no bean name detected
					beanName = getResourceId(meta, type);
				} else {
					// detect bean name even it was already detected
					String beanNameFromAnnotation = getResourceId(meta, type);
					if (StringUtils.equals(beanNameFromAnnotation, beanName)) {
						// do nothing
					} else {
						throw new IllegalResourceDefinitionException(//
								"Conflict ids [" + beanName + ", " + beanNameFromAnnotation
										+ "] defined in annotation for resource class ["
										+ annotatedDefinition.getBeanClassName() + "]");
					}
				}
			}
			// if layer annotation is defined in class, use its id as layer id
			layerId = getLayerId(meta, type, layerId, annotatedDefinition.getBeanClassName());
		}
		if (StringUtils.isEmpty(beanName)) {
			beanName = buildDefaultBeanName(annotatedDefinition);
		}

		return buildResourceBeanId(layerId, beanName);
	}

	/**
	 * build resource bean id by given layer id and bean name
	 * 
	 * @param layerId
	 * @param beanName
	 * @return
	 */
	public static String buildResourceBeanId(String layerId, String beanName) {
		if (StringUtils.isBlank(layerId)) {
			return beanName;
		} else {
			return StringUtils.join(new String[] { layerId, beanName }, LAYER_NAME_SEPARATOR);
		}
	}

	/**
	 * build default bean name
	 * 
	 * @param definition
	 * @return
	 */
	protected static String buildDefaultBeanName(BeanDefinition definition) {
		String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
		return Introspector.decapitalize(shortClassName);
	}

	/**
	 * get resource/component/application id from given annotation meta data
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @return
	 */
	protected static String getResourceId(AnnotationMetadata meta, String annotationClassName) {
		return getStringAttributeValue(meta, annotationClassName, getResourceIdPropertyName());
	}

	/**
	 * get id property name in resource/component/application annotation
	 * 
	 * @return
	 */
	protected static String getResourceIdPropertyName() {
		return IStereoTypes.RESOURCE_ANNOTATION_PROP_ID;
	}

	/**
	 * get resource/component/application container id from given annotation
	 * meta data
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @return
	 */
	protected static String getContainerId(AnnotationMetadata meta, String annotationClassName) {
		return getStringAttributeValue(meta, annotationClassName, getContainerIdPropertyName());
	}

	/**
	 * get container id property name in resource/component/application
	 * annotation
	 * 
	 * @return
	 */
	protected static String getContainerIdPropertyName() {
		return IStereoTypes.CONTAINER_ANNOTATION_PROP_ID;
	}

	/**
	 * check the annotation is resource stereo type or not
	 * 
	 * @param annotationClassName
	 * @return
	 */
	protected static boolean isResourceStereoType(String annotationClassName) {
		return IStereoTypes.APPLICATION_ANNOTATION_TYPE.equals(annotationClassName)
				|| IStereoTypes.COMPONENT_ANNOTATION_TYPE.equals(annotationClassName)
				|| IStereoTypes.RESOURCE_ANNOTATION_TYPE.equals(annotationClassName);
	}

	/**
	 * get layer parent id from given annotation meta data. </br>
	 * if layer parent id already get from another annotation and is different
	 * with layer parent id get from current annotation, throw
	 * {@linkplain IllegalResourceDefinitionException}
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @param currentParentLayerId
	 * @param beanClassName
	 * @return
	 */
	protected static String getParentLayerId(AnnotationMetadata meta, String annotationClassName,
			String currentParentLayerId, String beanClassName) {
		if (isLayerAnnotationType(annotationClassName)) {
			String parentLayerId = getStringAttributeValue(meta, annotationClassName, getLayerParentIdPropertyName());
			if (!StringUtils.isEmpty(currentParentLayerId) && StringUtils.equals(currentParentLayerId, parentLayerId)) {
				throw new IllegalResourceDefinitionException(//
						"Conflict parent layer ids [" + currentParentLayerId + ", " + parentLayerId
								+ "] defined in annotation for resource class[" + beanClassName + "]");
			} else {
				return parentLayerId;
			}
		} else {
			return null;
		}
	}

	/**
	 * get parent id property name in layer annotation
	 * 
	 * @return
	 */
	protected static String getLayerParentIdPropertyName() {
		return IStereoTypes.LAYER_PARENT_ANNOTATION_PROP_ID;
	}

	/**
	 * get layer id from given annotation meta data. </br>
	 * if layer id already get from another annotation and is different with
	 * layer id get from current annotation, throw
	 * {@linkplain IllegalResourceDefinitionException}
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @param currentLayerId
	 * @param beanClassName
	 * @return
	 */
	protected static String getLayerId(AnnotationMetadata meta, String annotationClassName, String currentLayerId,
			String beanClassName) {
		if (isLayerAnnotationType(annotationClassName)) {
			String layerId = getStringAttributeValue(meta, annotationClassName, getLayerIdPropertyName());
			if (!StringUtils.isEmpty(currentLayerId) && StringUtils.equals(currentLayerId, layerId)) {
				throw new IllegalResourceDefinitionException(//
						"Conflict layer ids [" + currentLayerId + ", " + layerId
								+ "] defined in annotation for resource class[" + beanClassName + "]");
			} else {
				return layerId;
			}
		} else {
			return null;
		}
	}

	/**
	 * get value of property by given parameters
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @param propertyName
	 * @return
	 */
	protected static String getStringAttributeValue(AnnotationMetadata meta, String annotationClassName,
			String propertyName) {
		AnnotationAttributes attributes = getAnnotationAttributes(meta, annotationClassName);
		return (String) attributes.get(propertyName);
	}

	/**
	 * get id property name in layer annotation
	 * 
	 * @return
	 */
	protected static String getLayerIdPropertyName() {
		return IStereoTypes.LAYER_ANNOTATION_PROP_ID;
	}

	/**
	 * check the given type is layer annotation type or not
	 * 
	 * @param annotationClassName
	 * @return
	 * @see com.github.nnest.arcteryx.spring.stereotype.Layer
	 * @see IStereoTypes#LAYER_ANNOTATION_TYPE
	 */
	protected static boolean isLayerAnnotationType(String annotationClassName) {
		return IStereoTypes.LAYER_ANNOTATION_TYPE.equals(annotationClassName);
	}

	/**
	 * get annotation attributes by given annotation type
	 * 
	 * @param meta
	 * @param type
	 * @return
	 */
	protected static AnnotationAttributes getAnnotationAttributes(AnnotationMetadata meta, String type) {
		return AnnotationAttributes.fromMap(meta.getAnnotationAttributes(type, false));
	}
}
