/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.github.nnest.arcteryx.IResource;

/**
 * resource definition resolver
 * 
 * @author brad.wu
 */
public interface IResourceDefinitionResolver {
	/**
	 * create resource by given parameters
	 * 
	 * @param applicationContext
	 * @return key: absolute path of resource
	 */
	Map<String, IResource> createResource(ApplicationContext applicationContext);
}
