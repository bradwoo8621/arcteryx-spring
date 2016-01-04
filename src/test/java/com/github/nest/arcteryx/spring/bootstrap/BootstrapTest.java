package com.github.nest.arcteryx.spring.bootstrap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nest.arcteryx.IApplication;
import com.github.nest.arcteryx.IApplicationBootstrap;
import com.github.nest.arcteryx.IComponent;
import com.github.nest.arcteryx.spring.SpringApplicationBootstrap;

/**
 * @author brad.wu
 *
 */
public class BootstrapTest {
	@Test
	public void test() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/github/nest/arcteryx/spring/bootstrap/BootstrapTest.xml");
		IApplicationBootstrap bootstrap = new SpringApplicationBootstrap(context);
		bootstrap.startup();
		assertEquals("TestApplication", bootstrap.getApplication().getId());
	}

	@Test(expected = NoUniqueBeanDefinitionException.class)
	public void testDuplicate() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/github/nest/arcteryx/spring/bootstrap/BootstrapTest2.xml");
		IApplicationBootstrap bootstrap = new SpringApplicationBootstrap(context);
		bootstrap.startup();
		assertEquals("TestApplication", bootstrap.getApplication().getId());
	}

	@Test
	public void testById() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/github/nest/arcteryx/spring/bootstrap/BootstrapTest3.xml");
		IApplicationBootstrap bootstrap = new SpringApplicationBootstrap(context, "TestApplication");
		bootstrap.startup();
		assertEquals("TestApplication", bootstrap.getApplication().getId());
	}

	@Test
	public void testComponent() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/github/nest/arcteryx/spring/bootstrap/BootstrapTest4.xml");
		IApplicationBootstrap bootstrap = new SpringApplicationBootstrap(context);
		bootstrap.startup();
		IApplication app = bootstrap.getApplication();
		IComponent compA = app.findComponent("CompA");
		assertNotNull(compA);
		IComponent compB = app.findComponent("CompB");
		assertNotNull(compB);
	}
	
	@Test
	public void testCustom() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/github/nest/arcteryx/spring/bootstrap/BootstrapTest5.xml");
		IApplicationBootstrap bootstrap = new SpringApplicationBootstrap(context);
		bootstrap.startup();
		IApplication app = bootstrap.getApplication();
		IComponent comp = app.findComponent("CustomComp");
		assertNotNull(comp);
	}
}
