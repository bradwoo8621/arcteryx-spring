/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.annotated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nnest.arcteryx.IApplication;
import com.github.nnest.arcteryx.IComponent;
import com.github.nnest.arcteryx.IEnterprise;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.ResourceUtils;
import com.github.nnest.arcteryx.spring.AutoAwareSpringEnterprise;
import com.github.nnest.arcteryx.spring.ClassReferenceResource;
import com.github.nnest.arcteryx.spring.IAnnotatedResource;

/**
 * @author brad.wu
 */
public class TestScanAnnotated {

	@SuppressWarnings("resource")
	@Test
	public void scan() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-enterprise-spring.xml", //
						"ScanTestAnnotated.xml" },
				getClass());
		AutoAwareSpringEnterprise aware = context.getBean(AutoAwareSpringEnterprise.class);
		IEnterprise enterprise = aware.getEnterprise();

		assertNotNull(enterprise);

		IApplication app = enterprise.getApplication("Shop");
		assertNotNull(app);
		assertTrue(app instanceof Shop);
		assertEquals(Shop.ID, app.getId());
		assertEquals(ResourceUtils.getLayer("top", null), app.getLayer());
		Collection<IApplication> childApplications = app.getResources(IApplication.class);
		assertEquals(1, childApplications.size());
		IAnnotatedResource extendApp = (IAnnotatedResource) childApplications.iterator().next();
		assertEquals(ResourceUtils.getLayer("extend", "top"), extendApp.getLayer());
		assertEquals("Shop", extendApp.getContainerBeanId());
		Collection<IComponent> components = app.getResources(IComponent.class);
		assertEquals(1, components.size());
		IAnnotatedResource childComponent = (IAnnotatedResource) components.iterator().next();
		assertTrue(childComponent instanceof ToySaler);
		assertEquals("Shop", childComponent.getContainerBeanId());

		IComponent comp = enterprise.findResource("Shop/ToySaler");
		assertNotNull(comp);
		assertTrue(comp instanceof ToySalerExtend);
		assertEquals(ResourceUtils.getLayer("extend", "top"), comp.getLayer());

		IResource res = enterprise.findResource("Shop/ToySaler/TedBear");
		assertNotNull(res);
		assertTrue(res instanceof TedBearExtend);

		res = enterprise.findResource("Shop/ToySaler/Lego");
		assertNotNull(res);
		assertTrue(res instanceof Lego);

		res = enterprise.findResource("Shop/ToySaler/Transformer");
		assertNotNull(res);
		assertTrue(res instanceof ClassReferenceResource);
		ClassReferenceResource crr = (ClassReferenceResource) res;
		assertEquals(Transformer.class, crr.getReferenceClass());
		assertEquals("extend-Transformer", crr.getReferenceBeanId());
	}
}
