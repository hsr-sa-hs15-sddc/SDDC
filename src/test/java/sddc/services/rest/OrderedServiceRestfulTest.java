package sddc.services.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
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
import sddc.services.OrderedServiceRepo;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.OrderedService;
import sddc.services.domain.Size;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@Configuration
@Profile("dev")
@WebIntegrationTest

public class OrderedServiceRestfulTest {
	
	RestTemplate template = new TestRestTemplate();
	
	@Autowired
	private OrderedServiceRepo repo;
	
	@Before
	 public void setUp() {
		 repo.deleteAll();
		 Set<Identifier> ids = new HashSet<Identifier>();
		 ids.add(new Identifier(UUID.randomUUID().toString(),Category.Compute,Size.L));
		 ids.add(new Identifier(UUID.randomUUID().toString(),Category.Network,Size.S));
		 repo.save(new OrderedService("LAMP Stack",ids));
	 }
	
	@Test
	public void testGetOrderedServices () {
	    OrderedService[] result = template.getForObject("http://localhost:8080/api/orderedservices", OrderedService[].class);

	    Assert.assertEquals(1, result.length);
	 }
	
	 @Test
	 public void testGetOrderedService() {
		long id = repo.findByName("LAMP Stack").getId();
		OrderedService result = template.getForObject("http://localhost:8080/api/orderedservices/{id}", OrderedService.class,id);
	    Assert.assertEquals("LAMP Stack",result.getOrderedServiceName());
	 }
	 
	 @Test
	 public void testDeleteService() {
		 long id = repo.findByName("LAMP Stack").getId();
		 template.delete("http://localhost:8080/api/orderedservices/{id}", id);
		 Assert.assertNull(repo.findByName("LAMP Stack"));
	 }
	
}
