package com.github.nnest.arcteryx.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nnest.arcteryx.IApplication;
import com.github.nnest.arcteryx.IComponent;
import com.github.nnest.arcteryx.IEnterprise;

/**
 * @author brad.wu
 *
 */
public class AwareTest {
	@Test
	public void testOneXML() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"AwareTestOneXML.xml" },
				getClass());
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

	@Test
	public void testMultipleXML() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"AwareTestMultiXML-1.xml", //
						"AwareTestMultiXML-2.xml" },
				getClass());
		test(context);
	}

	@Test
	public void testHierarchyXMLAwareInChild() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-enterprise-spring.xml", //
						"AwareTestHierarchyXML-parent.xml" },
				getClass());
		context = new ClassPathXmlApplicationContext(new String[] { //
				"/META-INF/nnest/default-aware-spring.xml", //
				"AwareTestHierarchyXML-child.xml" }, getClass(), context);
		test(context);
	}

	@Test
	public void testHierarchyXMLAwareInBoth() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"AwareTestHierarchyXML-parent.xml" },
				getClass());
		context = new ClassPathXmlApplicationContext(new String[] { //
				"/META-INF/nnest/default-aware-spring.xml", //
				"AwareTestHierarchyXML-child.xml" }, getClass(), context);
		test(context);
	}

	@Test
	public void testHierarchyXMLEnterpriseInChild() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "AwareTestHierarchyXML-parent.xml" }, getClass());
		context = new ClassPathXmlApplicationContext(new String[] { //
				"/META-INF/nnest/default-aware-spring.xml", //
				"/META-INF/nnest/default-enterprise-spring.xml", //
				"AwareTestHierarchyXML-child.xml" }, getClass(), context);
		test(context);
	}

	@Test
	public void testHierarchyXMLEnterpriseInBoth() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "AwareTestHierarchyXML-parent.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml" },
				getClass());
		context = new ClassPathXmlApplicationContext(new String[] { //
				"/META-INF/nnest/default-aware-spring.xml", //
				"/META-INF/nnest/default-enterprise-spring.xml", //
				"AwareTestHierarchyXML-child.xml" }, getClass(), context);

		test(context);
	}

	@SuppressWarnings("resource")
	@Test(expected = BeanCreationException.class)
	public void testMissContainer() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"AwareTestMissContainer.xml" },
				getClass());
		context.getBean(SpringEnterpriseAware.class);
	}

	@SuppressWarnings("resource")
	@Test
	public void testApplicationExtend() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"AwareTestApplicationExtend-parent.xml" },
				getClass());
		context = new ClassPathXmlApplicationContext(new String[] { //
				"/META-INF/nnest/default-aware-spring.xml", //
				"AwareTestApplicationExtend-child.xml" }, getClass(), context);
		SpringEnterpriseAware aware = context.getBean(SpringEnterpriseAware.class);
		IEnterprise enterprise = aware.getEnterprise();
		assertEquals(1, enterprise.getApplications().size());
		assertEquals("App1", enterprise.getApplication("App1").getId());

		IApplication application = enterprise.getApplication("App1");
		assertEquals(2, application.getResources().size());
		assertEquals(1, application.getResources(IComponent.class).size());
		assertEquals(1, application.getResources(IApplication.class).size());

		IComponent highLevelComponent = application.getResources(IComponent.class).iterator().next();
		IComponent lowLevelComponent = application.findResource("Comp11");
		assertNotEquals(highLevelComponent, lowLevelComponent);
	}
}
