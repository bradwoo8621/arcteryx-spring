/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.stereotype.StereoTypeHelper;

/**
 * default resource definition resolver.</br>
 * to resolve the resources which implements the resource interfaces, treat them
 * as singleton instance, created by spring application context
 * 
 * @author brad.wu
 */
public class DefaultResourceDefintionResolver implements IResourceDefinitionResolver {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.spring.IResourceDefinitionResolver#createResource(org.springframework.context.ApplicationContext)
	 */
	public Map<String, IResource> createResource(ApplicationContext applicationContext) {
		Map<String, IResource> resourceMap = new HashMap<String, IResource>();
		// if a class implements IResource, must was defined as a singleton bean
		Map<String, IResource> resources = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
				IResource.class);
		for (IResource resource : resources.values()) {
			resourceMap.put(StereoTypeHelper.determineResourcePath(resource.getClass()), resource);
		}
		return resourceMap;
	}
}
