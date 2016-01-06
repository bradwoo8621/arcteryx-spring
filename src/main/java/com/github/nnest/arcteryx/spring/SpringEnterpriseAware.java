/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import com.github.nnest.arcteryx.IApplication;
import com.github.nnest.arcteryx.IContainer;
import com.github.nnest.arcteryx.IEnterprise;
import com.github.nnest.arcteryx.IResource;

/**
 * enterprise spring aware
 * 
 * @author brad.wu
 */
public class SpringEnterpriseAware extends ApplicationObjectSupport {
	/**
	 * get enterprise
	 * 
	 * @return
	 */
	public IEnterprise getEnterprise() {
		return this.getApplicationContext().getBean(IEnterprise.class);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.support.ApplicationObjectSupport#initApplicationContext()
	 */
	@Override
	protected void initApplicationContext() throws BeansException {
		IEnterprise enterprise = this.getEnterprise();
		this.setupApplications(this.getApplicationContext(), enterprise);
		enterprise.startup();
	}

	/**
	 * setup applications
	 * 
	 * @param applicationContext
	 * @param enterprise
	 */
	protected void setupApplications(ApplicationContext applicationContext, IEnterprise enterprise) {
//		String[] names = BeanFactoryUtils.beanNamesIncludingAncestors(applicationContext);
//		for (String name : names) {
//			System.out.println(name);
//		}
		Map<String, IResource> resources = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
				IResource.class);
		for (IResource resource : resources.values()) {
			IContainer container = resource.getContainer();
			if (container == null) {
				if (resource instanceof IApplication) {
					// only prepare the top level applications
					enterprise.prepareApplication((IApplication) resource);
				} else {
					throw new IllegalResourceDefinitionException("Container not defined with resource "
							+ resource.getClass() + "[" + resource.getId() + "]");
				}
			} else {
				container.registerResource(resource);
			}
		}
	}

	/**
	 * get logger
	 * 
	 * @return
	 */
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
}
