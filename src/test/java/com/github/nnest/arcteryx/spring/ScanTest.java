/**
 * 
 */
package com.github.nnest.arcteryx.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nnest.arcteryx.IApplication;
import com.github.nnest.arcteryx.IComponent;
import com.github.nnest.arcteryx.IEnterprise;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.scan.extend.Lego;
import com.github.nnest.arcteryx.spring.scan.extend.Shop;
import com.github.nnest.arcteryx.spring.scan.extend.TedBearExtend;
import com.github.nnest.arcteryx.spring.scan.extend.ToySaler;
import com.github.nnest.arcteryx.spring.scan.extend.ToySalerExtend;

/**
 * @author brad.wu
 */
public class ScanTest {
	@SuppressWarnings("resource")
	@Test
	public void scanOne() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"scan/one/ScanTestOne.xml" },
				getClass());
		AutoAwareSpringEnterprise aware = context.getBean(AutoAwareSpringEnterprise.class);
		IEnterprise enterprise = aware.getEnterprise();

		assertNotNull(enterprise);

		IApplication app = enterprise.getApplication("Shop");
		assertNotNull(app);

		IComponent comp = enterprise.findResource("Shop/ToySaler");
		assertNotNull(comp);

		IResource res = enterprise.findResource("Shop/ToySaler/TedBear");
		assertNotNull(res);
	}

	@SuppressWarnings("resource")
	@Test
	public void scanSameLayerId() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"scan/layer/same/ScanTestLayerSame.xml" },
				getClass());
		AutoAwareSpringEnterprise aware = context.getBean(AutoAwareSpringEnterprise.class);
		IEnterprise enterprise = aware.getEnterprise();

		assertNotNull(enterprise);

		IApplication app = enterprise.getApplication("Shop");
		assertNotNull(app);
	}

	@SuppressWarnings("resource")
	@Test(expected = BeanDefinitionStoreException.class)
	public void scanConflictLayerId() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"scan/conflict/ScanTestIdConflict.xml" },
				getClass());
		context.getBean(AutoAwareSpringEnterprise.class);
	}

	@SuppressWarnings("resource")
	@Test
	public void scanExtend() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"scan/extend/ScanTestExtend.xml" },
				getClass());
		AutoAwareSpringEnterprise aware = context.getBean(AutoAwareSpringEnterprise.class);
		IEnterprise enterprise = aware.getEnterprise();

		assertNotNull(enterprise);

		IApplication app = enterprise.getApplication("Shop");
		assertNotNull(app);
		assertTrue(app instanceof Shop);
		Collection<IApplication> childApplications = app.getResources(IApplication.class);
		assertEquals(1, childApplications.size());
		Collection<IComponent> components = app.getResources(IComponent.class);
		assertEquals(1, components.size());
		assertTrue(components.iterator().next() instanceof ToySaler);

		IComponent comp = enterprise.findResource("Shop/ToySaler");
		assertNotNull(comp);
		assertTrue(comp instanceof ToySalerExtend);

		IResource res = enterprise.findResource("Shop/ToySaler/TedBear");
		assertNotNull(res);
		assertTrue(res instanceof TedBearExtend);
		
		res = enterprise.findResource("Shop/ToySaler/Lego");
		assertNotNull(res);
		assertTrue(res instanceof Lego);
	}
}
