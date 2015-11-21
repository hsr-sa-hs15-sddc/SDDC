package sddc.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import sddc.ApplicationMain;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@Configuration
@Profile("dev")
public class ServiceControllerTest {
	
	@Autowired
	private ServiceController controller;
	
	@Autowired
    private ApplicationContext context;
	
	
	@Test
	public void testFindAll() {
		Assert.assertEquals(1,controller.findAllServices().size());
	}
	
	 @Test
	    public void verifyContext () {

	        ServiceRepo repo = context.getBean(ServiceRepo.class);
	        Assert.assertNotNull( repo );

	        repo = (ServiceRepo) context.getBean("serviceRepo");
	        Assert.assertNotNull( repo );

	    }
	
	
}
