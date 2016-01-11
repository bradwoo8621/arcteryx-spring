/**
 * 
 */
package com.github.nnest.arcteryx.spring.stereotype;

import java.beans.Introspector;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import com.github.nnest.arcteryx.ILayer;
import com.github.nnest.arcteryx.ResourceUtils;
import com.github.nnest.arcteryx.spring.IllegalResourceDefinitionException;

/**
 * stereo type detective.</br>
 * Id here means bean id in spring context, not id of resource.
 * 
 * @author brad.wu
 */
public final class StereoTypeDetective {
	public final static String LAYER_NAME_SEPARATOR = "-";
	public final static IBeanIdGenerator DEFAULT_BEAN_ID_GENERATOR = new DefaultBeanIdGenerator();
	public final static IBeanIdGenerator PARENT_APP_BEAN_ID_GENERATOR = new ParentApplicationBeanIdGenerator();

	public final static IExceptionThrower CONTAINER_EXCEPTION_THROWER = new ContainerExceptionThrower();
	public final static IExceptionThrower RESOURCE_EXCEPTION_THROWER = new ResourceExceptionThrower();

	public final static IIdGetter RESOURCE_ID_GETTER = new ResourceIdGetter();
	public final static IIdGetter CONTAINER_ID_GETTER = new ResourceContainerIdGetter();

	public final static ILayerIdGetter LAYER_ID_GETTER = new LayerIdGetter();
	public final static ILayerIdGetter PARENT_LAYER_ID_GETTER = new ParentLayerIdGetter();
	public final static ILayerIdGetter IGNORE_LAYER_ID_GETTER = new IgnoreLayerIdGetter();

	public static interface ILayerIdGetter {
		/**
		 * get id by given parameters
		 * 
		 * @param meta
		 * @param annotationClassName
		 * @param currentId
		 * @param beanClassName
		 * @return
		 */
		String getId(AnnotationMetadata meta, String annotationClassName, String currentId, String beanClassName);
	}

	public static class IgnoreLayerIdGetter implements ILayerIdGetter {
		/**
		 * always return null
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.ILayerIdGetter#getId(org.springframework.core.type.AnnotationMetadata,
		 *      java.lang.String, java.lang.String, java.lang.String)
		 */
		public String getId(AnnotationMetadata meta, String annotationClassName, String currentId,
				String beanClassName) {
			return null;
		}
	}

	public static class LayerIdGetter implements ILayerIdGetter {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.ILayerIdGetter#getId(org.springframework.core.type.AnnotationMetadata,
		 *      java.lang.String, java.lang.String, java.lang.String)
		 */
		public String getId(AnnotationMetadata meta, String annotationClassName, String currentId,
				String beanClassName) {
			return StereoTypeDetective.getLayerId(meta, annotationClassName, currentId, beanClassName);
		}
	}

	public static class ParentLayerIdGetter implements ILayerIdGetter {

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.ILayerIdGetter#getId(org.springframework.core.type.AnnotationMetadata,
		 *      java.lang.String, java.lang.String, java.lang.String)
		 */
		public String getId(AnnotationMetadata meta, String annotationClassName, String currentId,
				String beanClassName) {
			return StereoTypeDetective.getParentLayerId(meta, annotationClassName, currentId, beanClassName);
		}
	}

	public static interface IIdGetter {
		/**
		 * get id from given parameters
		 * 
		 * @param meta
		 * @param annotationClassName
		 * @return
		 */
		String getId(AnnotationMetadata meta, String annotationClassName);

		/**
		 * get default id
		 * 
		 * @param beanClassName
		 * @return
		 */
		String getDefaultId(String beanClassName);

		/**
		 * is default id allowed or not
		 * 
		 * @return
		 */
		boolean isDefaultIdAllowed();
	}

	public static class ResourceIdGetter implements IIdGetter {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IIdGetter#getId(org.springframework.core.type.AnnotationMetadata,
		 *      java.lang.String)
		 */
		public String getId(AnnotationMetadata meta, String annotationClassName) {
			return StereoTypeDetective.getResourceId(meta, annotationClassName);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IIdGetter#getDefaultId(java.lang.String)
		 */
		public String getDefaultId(String beanClassName) {
			return StereoTypeDetective.buildDefaultBeanName(beanClassName);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IIdGetter#isDefaultIdAllowed()
		 */
		public boolean isDefaultIdAllowed() {
			return true;
		}
	}

	public static class ResourceContainerIdGetter implements IIdGetter {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IIdGetter#getId(org.springframework.core.type.AnnotationMetadata,
		 *      java.lang.String)
		 */
		public String getId(AnnotationMetadata meta, String annotationClassName) {
			return StereoTypeDetective.getContainerId(meta, annotationClassName);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IIdGetter#getDefaultId(java.lang.String)
		 */
		public String getDefaultId(String beanClassName) {
			throw new UnsupportedOperationException("Operation not supported yet");
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IIdGetter#isDefaultIdAllowed()
		 */
		public boolean isDefaultIdAllowed() {
			return false;
		}
	}

	public static interface IExceptionThrower {
		IllegalResourceDefinitionException raiseConflictIdsException(String beanClassName, String... resourceIds);
	}

	public static class ResourceExceptionThrower implements IExceptionThrower {

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IExceptionThrower#raiseConflictIdsException(java.lang.String,
		 *      java.lang.String[])
		 */
		public IllegalResourceDefinitionException raiseConflictIdsException(String beanClassName,
				String... resourceIds) {
			return new IllegalResourceDefinitionException(//
					"Conflict resource ids [" + StringUtils.join(resourceIds, ",")
							+ "] defined in annotation for resource class [" + beanClassName + "]");
		}
	}

	public static class ContainerExceptionThrower implements IExceptionThrower {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IExceptionThrower#raiseConflictIdsException(java.lang.String,
		 *      java.lang.String[])
		 */
		public IllegalResourceDefinitionException raiseConflictIdsException(String beanClassName,
				String... containerIds) {
			return new IllegalResourceDefinitionException(//
					"Conflict container ids [" + StringUtils.join(containerIds, ",")
							+ "] defined in annotation for resource class [" + beanClassName + "]");
		}
	}

	public static interface IBeanIdGenerator {
		/**
		 * generate bean id by given layer id and bean id
		 * 
		 * @param layerId
		 * @param beanId
		 * @return
		 */
		String generate(String layerId, String beanId);
	}

	public static class DefaultBeanIdGenerator implements IBeanIdGenerator {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.IBeanIdGenerator#generate(java.lang.String,
		 *      java.lang.String)
		 */
		public String generate(String layerId, String beanId) {
			return StereoTypeDetective.buildResourceBeanId(layerId, beanId);
		}
	}

	public static class ParentApplicationBeanIdGenerator extends DefaultBeanIdGenerator {
		/**
		 * Parent application bean id is same as current application. So if no
		 * parent layerId found, treat it as no parent application bean id
		 * 
		 * @see com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective.DefaultBeanIdGenerator#generate(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public String generate(String parentLayouerId, String beanId) {
			if (StringUtils.isEmpty(parentLayouerId)) {
				// no parent layer found, no parent application
				return null;
			}
			return super.generate(parentLayouerId, beanId);
		}
	}

	public static class BeanIdDeterminerHelper {
		private ILayerIdGetter layerIdGetter = null;
		private IIdGetter idGetter = null;
		private IExceptionThrower exceptionThrower = null;
		private IBeanIdGenerator beanIdGenerator = null;

		public BeanIdDeterminerHelper(ILayerIdGetter layerIdGetter, IIdGetter idGetter,
				IExceptionThrower exceptionThrower) {
			this(layerIdGetter, idGetter, StereoTypeDetective.DEFAULT_BEAN_ID_GENERATOR, exceptionThrower);
		}

		public BeanIdDeterminerHelper(ILayerIdGetter layerIdGetter, IIdGetter idGetter,
				IBeanIdGenerator beanIdGenerator, IExceptionThrower exceptionThrower) {
			this.layerIdGetter = layerIdGetter;
			this.idGetter = idGetter;
			this.exceptionThrower = exceptionThrower;
			this.beanIdGenerator = beanIdGenerator;
		}

		/**
		 * @return the layerIdGetter
		 */
		public ILayerIdGetter getLayerIdGetter() {
			return layerIdGetter;
		}

		/**
		 * @return the idGetter
		 */
		public IIdGetter getIdGetter() {
			return idGetter;
		}

		/**
		 * @return the beanIdGenerator
		 */
		public IBeanIdGenerator getBeanIdGenerator() {
			return beanIdGenerator;
		}

		/**
		 * @return the exceptionThrower
		 */
		public IExceptionThrower getExceptionThrower() {
			return exceptionThrower;
		}
	}

	/**
	 * determine resource bean id
	 * 
	 * @param annotatedDefinition
	 * @param helper
	 * @return
	 */
	public static String determineResourceBeanId(AnnotatedBeanDefinition annotatedDefinition,
			BeanIdDeterminerHelper helper) {
		String beanClassName = annotatedDefinition.getBeanClassName();

		AnnotationMetadata meta = annotatedDefinition.getMetadata();
		return determineResourceBeanId(beanClassName, meta, helper);
	}

	/**
	 * determine resource bean id
	 * 
	 * @param beanClassName
	 * @param meta
	 * @param helper
	 * @return
	 */
	public static String determineResourceBeanId(String beanClassName, AnnotationMetadata meta,
			BeanIdDeterminerHelper helper) {
		Set<String> types = meta.getAnnotationTypes();

		String layerId = null;
		String beanId = null;
		for (String type : types) {
			boolean isResourceStereoType = false;
			for (String metaType : meta.getMetaAnnotationTypes(type)) {
				layerId = helper.getLayerIdGetter().getId(meta, metaType, layerId, beanClassName);
				isResourceStereoType = isResourceStereoType || isResourceStereoType(metaType);
			}
			if (isResourceStereoType) {
				if (StringUtils.isEmpty(beanId)) {
					// no container id detected
					beanId = helper.getIdGetter().getId(meta, type);
				} else {
					// detect container id even it was already detected
					String anotherBeanId = helper.getIdGetter().getId(meta, type);
					if (!StringUtils.equals(anotherBeanId, beanId)) {
						throw helper.getExceptionThrower().raiseConflictIdsException(beanClassName, beanId,
								anotherBeanId);
					}
				}
			}
			// if layer annotation is defined in class, use its id as layer id
			layerId = helper.getLayerIdGetter().getId(meta, type, layerId, beanClassName);
		}
		if (StringUtils.isEmpty(beanId) && helper.getIdGetter().isDefaultIdAllowed()) {
			beanId = helper.getIdGetter().getDefaultId(beanClassName);
		}

		return helper.getBeanIdGenerator().generate(layerId, beanId);
	}

	/**
	 * determine layer of given bean class name
	 * 
	 * @param beanClassName
	 * @param meta
	 * @return
	 */
	public static ILayer determineLayer(String beanClassName, AnnotationMetadata meta) {
		Set<String> types = meta.getAnnotationTypes();

		String layerId = null;
		String parentLayerId = null;
		for (String type : types) {
			for (String metaType : meta.getMetaAnnotationTypes(type)) {
				layerId = LAYER_ID_GETTER.getId(meta, metaType, layerId, beanClassName);
				parentLayerId = PARENT_LAYER_ID_GETTER.getId(meta, metaType, parentLayerId, beanClassName);
			}
			// if layer annotation is defined in class, use its id as layer id
			layerId = LAYER_ID_GETTER.getId(meta, type, layerId, beanClassName);
			parentLayerId = PARENT_LAYER_ID_GETTER.getId(meta, type, parentLayerId, beanClassName);
		}

		return ResourceUtils.getLayer(layerId, parentLayerId);
	}

	/**
	 * determine container id of application
	 * 
	 * @param annotatedDefinition
	 * @return
	 */
	public static String determineParentApplicationBeanId(AnnotatedBeanDefinition annotatedDefinition) {
		return determineResourceBeanId(annotatedDefinition, //
				new BeanIdDeterminerHelper(PARENT_LAYER_ID_GETTER, //
						RESOURCE_ID_GETTER, //
						PARENT_APP_BEAN_ID_GENERATOR, //
						CONTAINER_EXCEPTION_THROWER));
	}

	/**
	 * determine container id of resource
	 * 
	 * @param annotatedDefinition
	 * @return
	 */
	public static String determineContainerBeanId(AnnotatedBeanDefinition annotatedDefinition) {
		return determineResourceBeanId(annotatedDefinition, //
				new BeanIdDeterminerHelper(LAYER_ID_GETTER, //
						CONTAINER_ID_GETTER, //
						CONTAINER_EXCEPTION_THROWER));
	}

	/**
	 * determine bean name
	 * 
	 * @param annotatedDefinition
	 * @return
	 */
	public static String determineResourceBeanId(AnnotatedBeanDefinition annotatedDefinition) {
		return determineResourceBeanId(annotatedDefinition, //
				new BeanIdDeterminerHelper(LAYER_ID_GETTER, //
						RESOURCE_ID_GETTER, //
						RESOURCE_EXCEPTION_THROWER));
	}

	/**
	 * build resource bean id by given layer id and bean name
	 * 
	 * @param layerId
	 * @param beanId
	 * @return
	 */
	public static String buildResourceBeanId(String layerId, String beanId) {
		if (StringUtils.isBlank(layerId)) {
			return beanId;
		} else if (StringUtils.isBlank(beanId)) {
			// no bean id defined
			return null;
		} else {
			return StringUtils.join(new String[] { layerId, beanId }, LAYER_NAME_SEPARATOR);
		}
	}

	/**
	 * build default bean name
	 * 
	 * @param beanClassName
	 * @return
	 */
	public static String buildDefaultBeanName(String beanClassName) {
		String shortClassName = ClassUtils.getShortName(beanClassName);
		return Introspector.decapitalize(shortClassName);
	}

	/**
	 * get resource/component/application id from given annotation meta data
	 * 
	 * @param meta
	 * @param annotationClassName
	 * @return
	 */
	public static String getResourceId(AnnotationMetadata meta, String annotationClassName) {
		return getStringAttributeValue(meta, annotationClassName, getResourceIdPropertyName());
	}

	/**
	 * get id property name in resource/component/application annotation
	 * 
	 * @return
	 */
	public static String getResourceIdPropertyName() {
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
	public static String getContainerId(AnnotationMetadata meta, String annotationClassName) {
		return getStringAttributeValue(meta, annotationClassName, getContainerIdPropertyName());
	}

	/**
	 * get container id property name in resource/component/application
	 * annotation
	 * 
	 * @return
	 */
	public static String getContainerIdPropertyName() {
		return IStereoTypes.CONTAINER_ANNOTATION_PROP_ID;
	}

	/**
	 * check the annotation is resource stereo type or not
	 * 
	 * @param annotationClassName
	 * @return
	 */
	public static boolean isResourceStereoType(String annotationClassName) {
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
	public static String getParentLayerId(AnnotationMetadata meta, String annotationClassName,
			String currentParentLayerId, String beanClassName) {
		if (isLayerAnnotationType(annotationClassName)) {
			return getStringAttributeValue(meta, annotationClassName, getLayerParentIdPropertyName());
		} else {
			return currentParentLayerId;
		}
	}

	/**
	 * get parent id property name in layer annotation
	 * 
	 * @return
	 */
	public static String getLayerParentIdPropertyName() {
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
	public static String getLayerId(AnnotationMetadata meta, String annotationClassName, String currentLayerId,
			String beanClassName) {
		if (isLayerAnnotationType(annotationClassName)) {
			return getStringAttributeValue(meta, annotationClassName, getLayerIdPropertyName());
		} else {
			return currentLayerId;
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
	public static String getStringAttributeValue(AnnotationMetadata meta, String annotationClassName,
			String propertyName) {
		AnnotationAttributes attributes = getAnnotationAttributes(meta, annotationClassName);
		return (String) attributes.get(propertyName);
	}

	/**
	 * get id property name in layer annotation
	 * 
	 * @return
	 */
	public static String getLayerIdPropertyName() {
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
	public static boolean isLayerAnnotationType(String annotationClassName) {
		return IStereoTypes.LAYER_ANNOTATION_TYPE.equals(annotationClassName);
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
}
