package sddc.services.rest;

import java.util.HashSet;
import java.util.Set;

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
import sddc.services.ServiceRepo;
import sddc.services.domain.Category;
import sddc.services.domain.Service;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;

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
	
	@Autowired
	private OrderedServiceRepo orderedRepo;
	
	private String networkconfig = "<network><name>default6</name><bridge name=\"virbr0\" /><forward mode=\"nat\"/><ip address=\"192.168.122.1\" netmask=\"255.255.255.0\">"
			+ "<dhcp><range start=\"192.168.122.2\" end=\"192.168.122.254\" /></dhcp>"
		    + "</ip><ip family=\"ipv6\" address=\"2001:db8:ca2:2::1\" prefix=\"64\" >"
		    +  "<dhcp><range start=\"2001:db8:ca2:2:1::10\" end=\"2001:db8:ca2:2:1::ff\" /></dhcp></ip></network>";
	
	@Before
	public void setUp() {
	repo.deleteAll();
	orderedRepo.deleteAll();
	Set<ServiceModule> modules = new HashSet<ServiceModule>();
	modules.add(new ServiceModule("Network Bridge",Size.S,Category.Network,networkconfig));
    repo.save(new Service("Network Virtual Bridge",modules));
	}
	
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
	 public void testPostService() {
		 long id = repo.findByServiceName("Network Virtual Bridge").getId();
		 Service service = repo.findByServiceName("Network Virtual Bridge");
		 String result = template.postForObject("http://localhost:8080/api/services/{id}", service, String.class, id);
		 Assert.assertEquals("ok", result);
		 Assert.assertNotNull(orderedRepo.findByName("Network Virtual Bridge"));
	 }
	 
	 @Test
	 public void testDeleteService() {
		 long id = repo.findByServiceName("Network Virtual Bridge").getId();
		 template.delete("http://localhost:8080/api/services/{id}", id);
		 Assert.assertNull(repo.findByServiceName("Network Virtual Bridge"));
	 }
	 
	 
	@Test 
	 public void testPutService() {
		 Long id = repo.findByServiceName("Network Virtual Bridge").getId();
		 Service service = repo.findOne(id);
		 service.setServiceName("Some new Service");
		 template.put("http://localhost:8080/api/services/{id}", service, id);
		 Assert.assertEquals("Some new Service",repo.findOne(id).getServiceName());
	 }
	 
}
