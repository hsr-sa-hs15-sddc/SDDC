package sddc.services;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.Assert;
import sddc.ApplicationMain;
import sddc.services.domain.Category;
import sddc.services.domain.Service;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;
import sddc.util.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("dev")
@WebAppConfiguration
public class ServiceControllerTest {
	
	@Autowired
	private ServiceController controller;
	
	@Autowired
    private ApplicationContext context;
	
	@Autowired
	private ServiceRepo repo;
	
	@Autowired
	private ServiceModuleRepo moduleRepo;
	
	
	private String networkconfig = FileUtil.getContentOfFile("src/test/resources/LibVirtNetworkConfigExample.xml", 
			Charset.defaultCharset(), false);
	
	@Before
	public void setUp() {
	repo.deleteAll();
	moduleRepo.deleteAll();
	Set<ServiceModule> modules = new HashSet<ServiceModule>();
	modules.add(new ServiceModule("Network Bridge",Size.S,Category.Network,networkconfig));
    repo.save(new Service("Network Virtual Bridge",modules));
	}
	
	@Test
	public void testFindAll() {
		Assert.assertEquals(1,controller.getServices().size());
	}
	
	 @Test
	    public void verifyContext () {

	        ServiceRepo repo = context.getBean(ServiceRepo.class);
	        Assert.assertNotNull( repo );

	        repo = (ServiceRepo) context.getBean("serviceRepo");
	        Assert.assertNotNull( repo );

	    }
	
	
}
