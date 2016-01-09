/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import com.github.nnest.arcteryx.spring.stereotype.StereoTypeDetective;

/**
 * Resource name generator
 * 
 * @author brad.wu
 */
public class LayerResourceBeanNameGenerator extends AnnotationBeanNameGenerator {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.support.BeanNameGenerator#generateBeanName(org.springframework.beans.factory.config.BeanDefinition,
	 *      org.springframework.beans.factory.support.BeanDefinitionRegistry)
	 */
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanName = null;
		if (definition instanceof AnnotatedBeanDefinition) {
			AnnotatedBeanDefinition annotatedDefinition = (AnnotatedBeanDefinition) definition;
			beanName = StereoTypeDetective.determineBeanName(annotatedDefinition);
			if (StringUtils.isNotEmpty(beanName)) {
				return beanName;
			}
		}
		return super.generateBeanName(definition, registry);
	}
}
