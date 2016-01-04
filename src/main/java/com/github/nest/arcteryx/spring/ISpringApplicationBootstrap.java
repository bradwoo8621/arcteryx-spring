/**
 * 
 */
package com.github.nest.arcteryx.spring;

import org.springframework.context.ApplicationContext;

import com.github.nest.arcteryx.IApplicationBootstrap;

/**
 * spring application bootstrap
 * 
 * @author brad.wu
 */
public interface ISpringApplicationBootstrap extends IApplicationBootstrap {
	/**
	 * get spring context
	 * 
	 * @return
	 */
	ApplicationContext getSpringContext();
}
