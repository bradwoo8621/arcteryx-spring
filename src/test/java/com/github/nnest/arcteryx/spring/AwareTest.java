package com.github.nnest.arcteryx.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.github.nnest.arcteryx.IApplication;
import com.github.nnest.arcteryx.IComponent;
import com.github.nnest.arcteryx.IEnterprise;

/**
 * @author brad.wu
 *
 */
public class AwareTest {
//	@Test
	public void testOneXML() {
		ApplicationContext context = new GenericXmlApplicationContext(getClass(), "BootstrapTestOneXML.xml");
		test(context);
	}

	private void test(ApplicationContext context) {
		SpringEnterpriseAware aware = context.getBean(SpringEnterpriseAware.class);
		IEnterprise enterprise = aware.getEnterprise();
		IApplication app1 = enterprise.getApplication("App1");
		IApplication app2 = enterprise.getApplication("App2");
		IComponent comp11 = app1.findResource("Comp11");
		IComponent comp12 = app1.findResource("Comp12");
		IComponent comp21 = app2.findResource("Comp21");
		IComponent comp22 = app2.findResource("Comp22");

		assertNotNull(enterprise);

		assertEquals(2, enterprise.getApplications().size());
		assertEquals("App1", app1.getId());
		assertEquals("App2", app2.getId());

		assertEquals(2, app1.getResources().size());
		assertEquals("Comp11", comp11.getId());
		assertEquals("Comp12", comp12.getId());

		assertEquals(2, app2.getResources().size());
		assertEquals("Comp21", comp21.getId());
		assertEquals("Comp22", comp22.getId());

		assertEquals(1, comp11.getResources().size());
		assertEquals("Res111", comp11.findResource("Res111").getId());
	}

//	@Test
	public void testMultipleXML() {
		ApplicationContext context = new GenericXmlApplicationContext(getClass(), "BootstrapTestMultiXML-1.xml",
				"BootstrapTestMultiXML-2.xml");
		test(context);
	}

	@Test
	public void testHierarchyXML() {
		ApplicationContext context = new GenericXmlApplicationContext(getClass(),
				"BootstrapTestHierarchyXML-parent.xml");
		context = new ClassPathXmlApplicationContext(new String[] { "BootstrapTestHierarchyXML-child.xml" }, getClass(),
				context);
		test(context);
	}
}