package sddc.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import sddc.services.domain.Workflow;

public class xmltest {

	@Before
	public void setUp() throws Exception {
		
		
		
	}

	@Test
	public void test() {
		ApplicationContext context = new FileSystemXmlApplicationContext("./test.xml");
		Workflow workflow = (Workflow) context.getBean("Workflow");
		
		Assert.assertNull(workflow);
		
		System.out.println("xmlFromFile");
		
	}

}
