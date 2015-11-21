package sddc.services.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import junit.framework.Assert;
import sddc.ApplicationMain;
import sddc.services.ServiceRepo;
import sddc.services.domain.Service;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@Configuration
@Profile("dev")
@WebIntegrationTest
public class ServiceRestfulTest {
	RestTemplate template = new TestRestTemplate();
	
	@Autowired
	private ServiceRepo repo;
	
	@Test
	public void testGetServices () {
	    Service[] result = template.getForObject("http://localhost:8080/api/services", Service[].class);

	    Assert.assertEquals(1, result.length);
	 }
	    
	    
	 @Test
	 public void testGetService() {
		long id = repo.findByServiceName("Network Virtual Bridge").getId();
	    Service result = template.getForObject("http://localhost:8080/api/services/{id}", Service.class,id);
	    Assert.assertEquals("Network Virtual Bridge",result.getServiceName());
	 }
	 
	 @Test
	 public void testPutService() {
		 
	 }
}
