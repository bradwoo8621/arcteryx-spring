/**
 * 
 */
package com.github.nest.arcteryx.spring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import com.github.nest.arcteryx.IApplication;
import com.github.nest.arcteryx.IComponent;
import com.github.nest.arcteryx.IResource;

/**
 * application spring bootstrap
 * 
 * @author brad.wu
 */
public class SpringApplicationBootstrap implements ISpringApplicationBootstrap {
	private boolean started = false;
	private ConfigurableApplicationContext context = null;
	private String applicationId = null;

	public SpringApplicationBootstrap(ConfigurableApplicationContext context) {
		this.setSpringContext(context);
	}

	public SpringApplicationBootstrap(ConfigurableApplicationContext context, String applicationId) {
		this.setSpringContext(context);
		this.setApplicationId(applicationId);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.IApplicationBootstrap#startup()
	 */
	public void startup() {
		this.doStartup();
		this.started = true;
	}

	/**
	 * do startup
	 */
	protected void doStartup() {
		IApplication app = this.getApplication();
		registerComponents(app);
		registerResources(app);
	}

	/**
	 * register resources
	 * 
	 * @param app
	 */
	protected void registerResources(IApplication app) {
		Map<String, IResource> resources = this.getSpringContext().getBeansOfType(IResource.class);
		for (IResource resource : resources.values()) {
			this.getLogger().debug(resource.getId());
		}
	}

	/**
	 * register components
	 * 
	 * @param app
	 */
	protected void registerComponents(IApplication app) {
		Map<String, IComponent> components = this.getSpringContext().getBeansOfType(IComponent.class);
		if (components == null) {
			this.getLogger().warn("Component not found of application[" + app.getId() + "]");
		} else {
			for (IComponent comp : components.values()) {
				app.registerComponent(comp);
				comp.setContainer(app);
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.IApplicationBootstrap#isStarted()
	 */
	public boolean isStarted() {
		return this.started;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.IApplicationBootstrap#shutdown()
	 */
	public void shutdown() {
		if (this.isStarted() && this.getSpringContext() != null) {
			this.getSpringContext().close();
		}
	}

	/**
	 * get application by {@linkplain #getApplicationId()} if exists, otherwise
	 * get by <code>IApplication.class</code>
	 * 
	 * @see com.github.nest.arcteryx.IApplicationBootstrap#getApplication()
	 */
	public IApplication getApplication() {
		if (!StringUtils.isEmpty(this.getApplicationId())) {
			return this.getSpringContext().getBean(this.getApplicationId(), IApplication.class);
		} else {
			return this.getSpringContext().getBean(IApplication.class);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.spring.ISpringApplicationBootstrap#getSpringContext()
	 */
	public ConfigurableApplicationContext getSpringContext() {
		return this.context;
	}

	/**
	 * set spring context
	 * 
	 * @param context
	 *            the context to set
	 */
	protected void setSpringContext(ConfigurableApplicationContext context) {
		this.context = context;
	}

	/**
	 * release spring context
	 */
	protected void releaseSpringContext() {
		this.context = null;
	}

	/**
	 * get application id
	 * 
	 * @return the applicationId
	 */
	protected String getApplicationId() {
		return this.applicationId;
	}

	/**
	 * set application id
	 * 
	 * @param applicationId
	 *            the applicationId to set
	 */
	protected void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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
