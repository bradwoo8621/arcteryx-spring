/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import com.github.nnest.arcteryx.IApplication;
import com.github.nnest.arcteryx.IComponent;
import com.github.nnest.arcteryx.IContainer;
import com.github.nnest.arcteryx.IEnterprise;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.ISystem;
import com.github.nnest.arcteryx.ResourceUtils;
import com.github.nnest.arcteryx.spring.stereotype.StereoTypeHelper;

/**
 * Enterprise spring aware.</br>
 * Any type of {@linkplain ApplicationContext} is accepted.</br>
 * </br>
 * <b>SpringEnterpriseAware</b>: Only one {@linkplain AutoAwareSpringEnterprise}
 * is allowed in application context. In hierarchy application contexts, it must
 * be defined in lowest level at least. Aware in high level will be skipped by
 * spring if low level existed. Note keep id of
 * {@linkplain AutoAwareSpringEnterprise} not defined, use spring default name
 * generator.</br>
 * Default {@linkplain AutoAwareSpringEnterprise} XML in
 * <b>META-INF/nnest/default-aware-spring.xml</b></br>
 * </br>
 * <b>Enterprise</b>: Only one {@linkplain IEnterprise} is allowed in
 * application context. In hierarchy application contexts,
 * {@linkplain IEnterprise} can be defined in any level, otherwise low level
 * will override the high level. Note keep id of {@linkplain IEnterprise} not
 * defined, use spring default name generator.</br>
 * Default {@linkplain IEnterprise} XML in
 * <b>META-INF/nnest/default-enterprise-spring.xml</b></br>
 * </br>
 * <b>Resourse</b>:</br>
 * {@linkplain ISystem} will be added into enterprise automatically.</br>
 * In normal spring application context (with no spring hierarchy application
 * context), bean id is not necessary. but container of resource is required.
 * {@linkplain AutoAwareSpringEnterprise} use container configuration to build
 * the system/application/component[/component]/resource tree.</br>
 * In hierarchy application context:</br>
 * 1. if not in lowest level, each resource must have its bean id explicit to
 * prevent the duplicated auto generated bean id is same as other bean in lower
 * level. Actually, always cannot ensure current is the lowest layer.</br>
 * 2. Lowest level, bean id is not necessary.</br>
 * For resource type: </br>
 * 1. {@linkplain IApplication}, if want to replace the existed resource in high
 * level, define it use same resource id.</br>
 * 2. {@linkplain IComponent}, if want to replace the existed component in
 * higher level, define it use same resource id.</br>
 * 3. {@linkplain IResource}, if want to replace the existed resource in high
 * level, define it use same resource id.</br>
 * 4. {@linkplain ISystem}, define derivation if it is derived from another
 * system.</br>
 * </br>
 * <font color='red'>Recommended: Define a prefix or suffix for bean id of each
 * level. {@linkplain AutoAwareSpringEnterprise} use resource id to build
 * hierarchy tree, not spring bean id, so always keep spring bean id
 * unique.</font></br>
 * </br>
 * An incorrect example:. </br>
 * parent: context-parent.xml</br>
 * 
 * <pre>
 * &lt;bean id="App1" class="com.github.nnest.arcteryx.Application"&gt;
 *		&lt;constructor-arg type="java.lang.String" value="App1" /&gt;
 * &lt;/bean&gt;
 * &lt;bean class="com.github.nnest.arcteryx.Component" p:container-ref="App1"&gt;
 *		&lt;constructor-arg type="java.lang.String" value="Comp11" /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * child: context-child.xml</br>
 * 
 * <pre>
 * &lt;bean class="com.github.nnest.arcteryx.Component" p:container-ref="App1"&gt;
 *		&lt;constructor-arg type="java.lang.String" value="Comp12" /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * ids of components in parent and child are both auto generated by spring as
 * <code>com.github.nnest.arcteryx.Component#0</code>, then component in parent
 * is overridden by child, cannot be aware. </br>
 * 
 * @author brad.wu
 */
public class AutoAwareSpringEnterprise extends ApplicationObjectSupport implements InitializingBean {
	private List<ResourceInterfaceDefinitionScanner> interfaceScanners = new LinkedList<ResourceInterfaceDefinitionScanner>();
	private List<IResourceDefinitionResolver> resourceDefinitionResolvers = new LinkedList<IResourceDefinitionResolver>();

	/**
	 * @return the interfaceScanners
	 */
	public List<ResourceInterfaceDefinitionScanner> getInterfaceScanners() {
		if (this.interfaceScanners == null) {
			return Collections.emptyList();
		} else {
			return this.interfaceScanners;
		}
	}

	/**
	 * determine interface scanner
	 * 
	 * @param applicationContext
	 */
	protected void determineInterfaceScanner(ApplicationContext applicationContext) {
		Map<String, ResourceInterfaceDefinitionScanner> scanners = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext, ResourceInterfaceDefinitionScanner.class);
		this.interfaceScanners.addAll(scanners.values());
	}

	/**
	 * get regardful annotations
	 * 
	 * @return the regardfulAnnotations
	 */
	public List<IResourceDefinitionResolver> getResourceDefinitionResolvers() {
		return this.resourceDefinitionResolvers;
	}

	/**
	 * add resource definition resolver
	 * 
	 * @param resolver
	 */
	public void addResourceDefinitionResolver(IResourceDefinitionResolver resolver) {
		if (!this.resourceDefinitionResolvers.contains(resolver)) {
			this.resourceDefinitionResolvers.add(resolver);
		}
	}

	/**
	 * determine annotated resource definition resolvers from application
	 * context
	 * 
	 * @param applicationContext
	 */
	protected void determineAnnotatedResourceDefinitionResolvers(ApplicationContext applicationContext) {
		Map<String, IResourceDefinitionResolver> resolvers = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext, IResourceDefinitionResolver.class);
		this.resourceDefinitionResolvers.addAll(resolvers.values());
	}

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
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		IEnterprise enterprise = this.getEnterprise();
		this.setupSystems(this.getApplicationContext(), enterprise);
		enterprise.startup();
	}

	/**
	 * setup applications
	 * 
	 * @param applicationContext
	 * @param enterprise
	 */
	protected void setupSystems(ApplicationContext applicationContext, IEnterprise enterprise) {
		List<ApplicationContext> allContexts = new LinkedList<ApplicationContext>();
		allContexts.add(applicationContext);
		Map<String, IResource> resourceMap = new HashMap<String, IResource>();

		this.determineInterfaceScanner(applicationContext);
		List<ResourceInterfaceDefinitionScanner> scanners = this.getInterfaceScanners();
		if (scanners != null && scanners.size() != 0) {
			for (ResourceInterfaceDefinitionScanner scanner : scanners) {
				int beanCount = scanner.scan();
				if (beanCount != 0) {
					allContexts.add(scanner.getApplicationContext());
				}
			}
		}

		this.determineAnnotatedResourceDefinitionResolvers(applicationContext);
		for (ApplicationContext context : allContexts) {
			// process the beans which doesn't implement the interface
			List<IResourceDefinitionResolver> resolvers = this.getResourceDefinitionResolvers();
			if (resolvers != null && resolvers.size() != 0) {
				// loop resolver and its annotations
				// scan application context to find resources
				for (IResourceDefinitionResolver resolver : resolvers) {
					resourceMap.putAll(resolver.createResource(context));
				}
			} else {
				this.getLogger().info("No regardful annotations declared");
			}
		}

		// find container and register, build resource hierarchy
		for (Map.Entry<String, IResource> entry : resourceMap.entrySet()) {
			IResource resource = entry.getValue();
			if (resource instanceof ISystem) {
				ISystem system = (ISystem) resource;
				ISystem derivation = system.getDerivation();
				if (derivation == null) {
					String derivationPath = StereoTypeHelper.determineDerivation(system.getClass());
					if (derivationPath != null) {
						IResource derivationSystem = resourceMap.get(derivationPath);
						if (derivationSystem == null) {
							throw new IllegalResourceDefinitionException(
									"Derivation [" + derivationPath + "] of system [" + entry.getKey() + "] not found");
						} else if (!(derivationSystem instanceof ISystem)) {
							throw new IllegalResourceDefinitionException(
									"Resource [" + derivationPath + "] is not a system");
						} else {
							system.setDerivation((ISystem) derivationSystem);
						}
					}
				}
				enterprise.prepareSystem(system);
			} else {
				String path = entry.getKey();
				String containerPath = StringUtils.substring(path, 0, path.lastIndexOf(IResource.SEPARATOR_CHAR));
				IResource container = resourceMap.get(containerPath);
				if (container == null) {
					throw new IllegalResourceDefinitionException("Container of resource [" + path + "] not found");
				} else if (!(container instanceof IContainer)) {
					throw new IllegalResourceDefinitionException("Resource [" + containerPath + "] is not a container");
				} else {
					ResourceUtils.registerResource((IContainer) container, resource);
				}
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
