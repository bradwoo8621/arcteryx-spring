/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan.conflict;

import org.junit.Test;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nnest.arcteryx.spring.AutoAwareSpringEnterprise;

/**
 * @author brad.wu
 */
public class ScanTestConflictLayerId {
	@SuppressWarnings("resource")
	@Test(expected = BeanDefinitionStoreException.class)
	public void scan() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"ScanTestIdConflict.xml" },
				getClass());
		context.getBean(AutoAwareSpringEnterprise.class);
	}
}
