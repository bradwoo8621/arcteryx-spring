/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * stereo type detective helper.</br>
 * 
 * @author brad.wu
 */
public final class StereoTypeHelper {
	/**
	 * determine derivation path by given class
	 * 
	 * @param systemClass
	 * @return
	 */
	public static String determineDerivation(Class<?> systemClass) {
		ASystem annotation = systemClass.getAnnotation(ASystem.class);
		if (annotation == null) {
			return null;
		} else {
			String derivation = annotation.deriveFrom();
			return StringUtils.isBlank(derivation) ? null : derivation;
		}
	}

	/**
	 * determine resource path by given class
	 * 
	 * @param resourceClass
	 * @return
	 */
	public static String determineResourcePath(Class<?> resourceClass) {
		ResourcePath annotation = resourceClass.getAnnotation(ResourcePath.class);
		return annotation != null ? annotation.value() : null;
	}

	/**
	 * determine resource path
	 * 
	 * @param beanDefinition
	 * @return
	 */
	public static String determineResourcePath(AnnotatedBeanDefinition beanDefinition) {
		AnnotationMetadata meta = beanDefinition.getMetadata();

		Set<String> types = meta.getAnnotationTypes();

		for (String type : types) {
			boolean isResourceStereoType = isResourceStereoType(type);
			if (!isResourceStereoType) {
				for (String metaType : meta.getMetaAnnotationTypes(type)) {
					isResourceStereoType = isResourceStereoType(metaType);
					if (isResourceStereoType) {
						break;
					}
				}
			}

			if (isResourceStereoType) {
				return getStringAttributeValue(meta, ResourcePath.class.getName(), "value");
			}
		}

		return null;
	}

	/**
	 * get annotation attributes by given annotation type
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @return
	 */
	public static AnnotationAttributes getAnnotationAttributes(AnnotationMetadata meta, String annotationClassName) {
		return AnnotationAttributes.fromMap(meta.getAnnotationAttributes(annotationClassName, false));
	}

	/**
	 * get value of property by given parameters
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @param propertyName
	 * @return
	 */
	public static String getStringAttributeValue(AnnotationMetadata meta, String annotationClassName,
			String propertyName) {
		AnnotationAttributes attributes = getAnnotationAttributes(meta, annotationClassName);
		return (String) attributes.get(propertyName);
	}

	/**
	 * check the annotation is resource stereo type or not
	 * 
	 * @param annotationClassName
	 * @return
	 */
	public static boolean isResourceStereoType(String annotationClassName) {
		return IStereoTypes.SYSTEM_ANNOTATION_TYPE.equals(annotationClassName)
				|| IStereoTypes.APPLICATION_ANNOTATION_TYPE.equals(annotationClassName)
				|| IStereoTypes.COMPONENT_ANNOTATION_TYPE.equals(annotationClassName)
				|| IStereoTypes.RESOURCE_ANNOTATION_TYPE.equals(annotationClassName);
	}
}
