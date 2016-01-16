/**
 * 
 */
package com.github.nnest.arcteryx.spring.scan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nnest.arcteryx.IEnterprise;
import com.github.nnest.arcteryx.spring.AutoAwareSpringEnterprise;
import com.github.nnest.arcteryx.spring.IClassReferenceResource;
import com.github.nnest.arcteryx.spring.IMethodReferenceResource;
import com.github.nnest.arcteryx.spring.scan.base.Base;
import com.github.nnest.arcteryx.spring.scan.base.Shop;
import com.github.nnest.arcteryx.spring.scan.base.Ted;
import com.github.nnest.arcteryx.spring.scan.base.Toy;
import com.github.nnest.arcteryx.spring.scan.base.ToySaler;
import com.github.nnest.arcteryx.spring.scan.base.Transformer;
import com.github.nnest.arcteryx.spring.scan.branch.Branch;
import com.github.nnest.arcteryx.spring.scan.branch.BranchToySaler;

/**
 * @author brad.wu
 */
public class ScanTest {
	@SuppressWarnings("resource")
	@Test
	public void scan() throws NoSuchMethodException, SecurityException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/META-INF/nnest/default-aware-spring.xml", //
						"/META-INF/nnest/default-enterprise-spring.xml", //
						"ScanTest.xml" },
				getClass());
		AutoAwareSpringEnterprise aware = context.getBean(AutoAwareSpringEnterprise.class);
		IEnterprise enterprise = aware.getEnterprise();

		assertNotNull(enterprise);

		assertEquals(2, enterprise.getSystems().size());
		assertNotNull(enterprise.getSystem(Base.ID));
		assertNotNull(enterprise.findResource(Base.ID));
		assertNotNull(enterprise.findResource(Branch.ID));

		assertNotNull(enterprise.findResource(Shop.PATH));
		assertNotNull(enterprise.findResource(Toy.PATH));
		assertNotNull(enterprise.findResource(Ted.PATH));

		IClassReferenceResource transformer = enterprise.findResource(Transformer.PATH);
		assertNotNull(transformer);

		// sell is override by branch
		IMethodReferenceResource sell = enterprise
				.findResource(ToySaler.PATH + IMethodReferenceResource.METHOD_BEAN_ID_SEPARATOR + "sell");
		assertNotNull(sell);

		IMethodReferenceResource branchSell = enterprise
				.findResource(BranchToySaler.PATH + IMethodReferenceResource.METHOD_BEAN_ID_SEPARATOR + "sell");
		assertNotNull(branchSell);
		assertNotEquals(sell, branchSell);

		// use base collect as branch collect
		IMethodReferenceResource collect = enterprise
				.findResource(ToySaler.PATH + IMethodReferenceResource.METHOD_BEAN_ID_SEPARATOR + "collect");
		assertNotNull(collect);

		IMethodReferenceResource branchCollect = enterprise
				.findResource(BranchToySaler.PATH + IMethodReferenceResource.METHOD_BEAN_ID_SEPARATOR + "collect");
		assertNotNull(branchCollect);
		assertEquals(collect, branchCollect);

		// guide is added in branch
		IMethodReferenceResource guide = enterprise
				.findResource(BranchToySaler.PATH + IMethodReferenceResource.METHOD_BEAN_ID_SEPARATOR + "guide");
		assertNotNull(guide);
	}
}
