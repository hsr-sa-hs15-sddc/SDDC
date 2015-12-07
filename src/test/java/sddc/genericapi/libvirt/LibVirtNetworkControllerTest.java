package sddc.genericapi.libvirt;




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

public class LibVirtNetworkControllerTest {
	
	private ResourceController controller;

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
		controller = (ResourceController) context.getBean("LibVirtNetworkControllerTest");
		
		((ConfigurableApplicationContext)context).close();
	}

	@Test
	public void NetworkControllerTest() {
		String config = FileUtil.getContentOfFile("src/test/resources/LibVirtNetworkConfigExample.xml", Charset.defaultCharset(), false);
		ServiceModule module = new ServiceModule("LibVirtNetworkConfigTest", Size.L, Provider.LibVirt, Category.Network, config);
		
		Identifier identifier = controller.create(module);
		Assert.assertNotNull(identifier);
		
		Map<String, String> infos = controller.getInformations(identifier);
		Assert.assertFalse(infos.isEmpty());
		
		controller.delete(identifier);
		
		Assert.assertTrue(controller.getInformations(identifier).isEmpty());
	}
	
	@Test
	public void ComputeControllerErrorTest() {
		ServiceModule module = new ServiceModule("LibVirtNetworkConfigErrorTest", Size.L, Provider.LibVirt, Category.Network, "<<");
		
		Identifier identifier = controller.create(module);
		Assert.assertNull(identifier);
		
		Map<String, String> infos = controller.getInformations(identifier);
		Assert.assertTrue(infos.isEmpty());
		
	}

}
