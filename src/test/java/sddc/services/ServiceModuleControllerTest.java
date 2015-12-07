package sddc.services;

import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.Assert;
import sddc.ApplicationMain;
import sddc.services.domain.Category;
import sddc.services.domain.Provider;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;
import sddc.services.domain.Workflow;
import sddc.util.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("dev")
@WebAppConfiguration
public class ServiceModuleControllerTest {
	
	@Autowired
	private ServiceModuleController controller;
	
	@Autowired
    private ApplicationContext context;
	
	@Autowired
	private ServiceModuleRepo repo;
	
	
	private String networkconfig = FileUtil.getContentOfFile("src/test/resources/LibVirtNetworkConfigExample.xml", 
			Charset.defaultCharset(), false);
	
	@Before
	public void setUp() {
		
		
		
		repo.deleteAllInBatch();
		repo.save(new ServiceModule("Network Bridges",Size.S, Provider.LibVirt, Category.Network,networkconfig));
	}
	
	@Test
	public void testFindAll() {
		Assert.assertEquals(1,controller.getServiceModules().size());
	}
	
	 @Test
	 public void verifyContext () {

		 ServiceModuleRepo repo = context.getBean(ServiceModuleRepo.class);
		 Assert.assertNotNull( repo );

		 repo = (ServiceModuleRepo) context.getBean("serviceModuleRepo");
		 Assert.assertNotNull( repo );

	 }
	
	
}
