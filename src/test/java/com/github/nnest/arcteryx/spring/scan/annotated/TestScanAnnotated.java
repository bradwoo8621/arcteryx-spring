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
import com.github.nnest.arcteryx.spring.IAnnotatedResource;
import com.github.nnest.arcteryx.spring.IClassReferenceResource;
import com.github.nnest.arcteryx.spring.IMethodReferenceResource;

/**
 * @author brad.wu
 */
public class TestScanAnnotated {

	@SuppressWarnings("resource")
	@Test
	public void scan() throws NoSuchMethodException, SecurityException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"ScanTestAnnotated.xml" },
				getClass());
		AutoAwareSpringEnterprise aware = context.getBean(AutoAwareSpringEnterprise.class);
		IEnterprise enterprise = aware.getEnterprise();

		assertNotNull(enterprise);

		IApplication app = enterprise.getApplication("Shop");
		assertNotNull(app);
		assertTrue(app instanceof Shop);
		assertEquals(Shop.ID, app.getId());
		assertEquals(ResourceUtils.getLayer("top", null), app.getSystem());
		Collection<IApplication> childApplications = app.getResources(IApplication.class);
		assertEquals(1, childApplications.size());
		IAnnotatedResource extendApp = (IAnnotatedResource) childApplications.iterator().next();
		assertEquals(ResourceUtils.getLayer("extend", "top"), extendApp.getSystem());
		assertEquals("Shop", extendApp.getContainerBeanId());
		Collection<IComponent> components = app.getResources(IComponent.class);
		assertEquals(1, components.size());
		IAnnotatedResource childComponent = (IAnnotatedResource) components.iterator().next();
		assertTrue(childComponent instanceof ToySaler);
		assertEquals("Shop", childComponent.getContainerBeanId());

		IComponent comp = enterprise.findResource("Shop/ToySaler");
		assertNotNull(comp);
		assertTrue(comp instanceof ToySalerExtend);
		assertEquals(ResourceUtils.getLayer("extend", "top"), comp.getSystem());

		IResource res = enterprise.findResource("Shop/ToySaler/TedBear");
		assertNotNull(res);
		assertTrue(res instanceof TedBearExtend);

		res = enterprise.findResource("Shop/ToySaler/Lego");
		assertNotNull(res);
		assertTrue(res instanceof Lego);

		res = enterprise.findResource("Shop/ToySaler/Transformer");
		assertNotNull(res);
		assertTrue(res instanceof IClassReferenceResource);
		IClassReferenceResource crr = (IClassReferenceResource) res;
		assertEquals(Transformer.class, crr.getReferenceClass());
		assertEquals("extend-Transformer", crr.getReferenceBeanId());

		res = enterprise.findResource("Shop/ToySaler/AccountService#collect");
		assertNotNull(res);
		assertTrue(res instanceof IMethodReferenceResource);
		IMethodReferenceResource mrr = (IMethodReferenceResource) res;
		assertEquals(AccountService.class, mrr.getReferenceClass());
		assertEquals("extend-AccountService", mrr.getReferenceBeanId());
		assertEquals("AccountService#collect", mrr.getId());
		assertEquals(AccountService.class.getDeclaredMethod("collect", new Class<?>[0]), mrr.getReferenceMethod());

		res = enterprise.findResource("Shop/ToySaler/AccountService#save");
		assertNotNull(res);
		assertTrue(res instanceof IMethodReferenceResource);
		mrr = (IMethodReferenceResource) res;
		assertEquals(AccountService.class, mrr.getReferenceClass());
		assertEquals("extend-AccountService", mrr.getReferenceBeanId());
		assertEquals("AccountService#save", mrr.getId());
		assertEquals(AccountService.class.getDeclaredMethod("save", new Class<?>[0]), mrr.getReferenceMethod());

		res = enterprise.findResource("Shop/ToySaler/GuideService#hello");
		assertNotNull(res);
		assertTrue(res instanceof IMethodReferenceResource);
		mrr = (IMethodReferenceResource) res;
		assertEquals(IGuideService.class, mrr.getReferenceClass());
		assertEquals("extend-GuideService", mrr.getReferenceBeanId());
		assertEquals("GuideService#hello", mrr.getId());
		assertEquals(IGuideService.class.getDeclaredMethod("hello", new Class<?>[0]), mrr.getReferenceMethod());
	}
}
