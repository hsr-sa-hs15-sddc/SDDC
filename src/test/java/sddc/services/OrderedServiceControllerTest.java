package sddc.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
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
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.OrderedService;
import sddc.services.domain.Size;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@Configuration
@Profile("dev")
public class OrderedServiceControllerTest {
	
	@Autowired
	private OrderedServiceController controller;
	
	@Autowired
    private ApplicationContext context;
	
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
	public void testFindAll() {
		Assert.assertEquals(1,controller.findAllServices().size());
	}
	
	 @Test
	    public void verifyContext () {

	        OrderedServiceRepo repo = context.getBean(OrderedServiceRepo.class);
	        Assert.assertNotNull( repo );

	        repo = (OrderedServiceRepo) context.getBean("orderedServiceRepo");
	        Assert.assertNotNull( repo );

	    }
	
	
}
