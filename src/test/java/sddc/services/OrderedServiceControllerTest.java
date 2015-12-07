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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.Assert;
import sddc.ApplicationMain;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.OrderedService;
import sddc.services.domain.Provider;
import sddc.services.domain.Size;
import sddc.services.domain.Workflow;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("dev")
@WebAppConfiguration
public class OrderedServiceControllerTest {
	
	
	private OrderedServiceController controller;
	
	@Autowired
    private ApplicationContext context;
	
	@Autowired
	private OrderedServiceRepo repo;
	
	@Before
	 public void setUp() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		controller = new OrderedServiceController((Workflow) context.getBean("Workflow"));
		
		((ConfigurableApplicationContext)context).close();
		
		
		 repo.deleteAll();
		 Set<Identifier> ids = new HashSet<Identifier>();
		 ids.add(new Identifier("Storage Pool",UUID.randomUUID().toString(),Category.Compute,Size.L, Provider.LibVirt));
		 ids.add(new Identifier("Network Bridge",UUID.randomUUID().toString(),Category.Network,Size.S, Provider.LibVirt));
		 repo.save(new OrderedService("LAMP Stack",ids));
	 }
	
	@Test
	public void testFindAll() {
		Assert.assertEquals(1,controller.getOrderedServices().size());
	}
	
	 @Test
	    public void verifyContext () {

	        OrderedServiceRepo repo = context.getBean(OrderedServiceRepo.class);
	        Assert.assertNotNull( repo );

	        repo = (OrderedServiceRepo) context.getBean("orderedServiceRepo");
	        Assert.assertNotNull( repo );

	    }
	
	
}
