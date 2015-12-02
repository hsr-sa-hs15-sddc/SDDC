package sddc.genericapi.libvirt;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.Assert;
import sddc.genericapi.ResourceController;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.Provider;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;
import sddc.util.FileUtil;

public class LibVirtComputeControllerTest {
	
	private ResourceController controller;

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		controller = (ResourceController) context.getBean("LibVirtComputeController");
		
		((ConfigurableApplicationContext)context).close();
	}

	@Test
	public void ComputeControllerTest() {
		String config = FileUtil.getContentOfFile("src/test/resources/LibVirtComputeConfigExample.xml", Charset.defaultCharset(), false);
		ServiceModule module = new ServiceModule("LibVirtComputeConfigTest", Size.L, Provider.LibVirt, Category.Compute, config);
		
		Identifier identifier = controller.create(module);
		Assert.assertNotNull(identifier);
		
		Map<String, String> infos = controller.getInformations(identifier);
		Assert.assertFalse(infos.isEmpty());
		
		controller.delete(identifier);
		
		Assert.assertTrue(controller.getInformations(identifier).isEmpty());
	}

}
