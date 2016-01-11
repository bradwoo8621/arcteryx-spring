/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.one;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nnest.arcteryx.IApplication;
import com.github.nnest.arcteryx.IComponent;
import com.github.nnest.arcteryx.IEnterprise;
import com.github.nnest.arcteryx.IResource;
import com.github.nnest.arcteryx.spring.AutoAwareSpringEnterprise;

/**
 * @author brad.wu
 */
public class ScanTestOneLayer {
	@SuppressWarnings("resource")
	@Test
	public void scan() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"ScanTestOne.xml" },
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
}
