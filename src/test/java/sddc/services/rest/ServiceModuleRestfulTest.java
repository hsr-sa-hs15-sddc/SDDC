package sddc.services.rest;

import java.nio.charset.Charset;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import junit.framework.Assert;
import sddc.ApplicationMain;
import sddc.services.ServiceModuleRepo;
import sddc.services.domain.Category;
import sddc.services.domain.Provider;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;
import sddc.util.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("dev")
@WebIntegrationTest
public class ServiceModuleRestfulTest {
	RestTemplate template = new TestRestTemplate();
	
	@Autowired
	private ServiceModuleRepo repo;
	
	private String networkconfig = FileUtil.getContentOfFile("src/test/resources/LibVirtNetworkConfigExample.xml", Charset.defaultCharset(), false);
	
	@Before
	public void setUp() {
	repo.deleteAllInBatch();
    repo.save(new ServiceModule("Network Bridge",Size.S, Provider.LibVirt, Category.Network,networkconfig));
	}
	 
	@Test
	public void testGetServiceModules () {
	    ServiceModule[] result = template.getForObject("http://localhost:8080/api/servicemodules", ServiceModule[].class);

	    Assert.assertEquals(1, result.length);
	 }
	
	@Test
	public void testCreateServiceModule() {
		ServiceModule module = new ServiceModule("KVM VM",Size.L, Provider.LibVirt, Category.Compute,networkconfig);
		ServiceModule result = template.postForObject("http://localhost:8080/api/servicemodules/new", module, ServiceModule.class);
		Assert.assertEquals("KVM VM", result.getName());
		Assert.assertEquals(Size.L, result.getSize());
		Assert.assertEquals(Category.Compute, result.getCategory());
	}
	    
	    
	 @Test
	 public void testGetServiceModule() {
		long id = repo.findByName("Network Bridge").getId();
	    ServiceModule result = template.getForObject("http://localhost:8080/api/servicemodules/{id}", ServiceModule.class,id);
	    Assert.assertEquals("Network Bridge",result.getName());
	 }
	 
	 @Test
	 public void testDeleteServiceModule() {
		 long id = repo.findByName("Network Bridge").getId();
		 template.delete("http://localhost:8080/api/servicemodules/{id}", id);
		 Assert.assertNull(repo.findByName("Network Bridge"));
	 }
	
	@Test 
	 public void testUpdateServiceModule() {
		long id = repo.findByName("Network Bridge").getId();
		ServiceModule module = repo.findOne(id);
		module.setConfig("Hello World");
		module.setName("Hello World");
		module.setCategory(Category.Storage);
		module.setSize(Size.L);
		template.put("http://localhost:8080/api/servicemodules/{id}", module, id);
		Assert.assertEquals("Hello World",repo.findOne(id).getName());
		Assert.assertEquals("Hello World",repo.findOne(id).getConfig());
		Assert.assertEquals(Category.Storage,repo.findOne(id).getCategory());
		Assert.assertEquals(Size.L,repo.findOne(id).getSize());
	}
	
	@Test
	public void testGetProviders() {
		Provider[] result = template.getForObject("http://localhost:8080/api/servicemodules/providers", Provider[].class);

	    Assert.assertTrue(result.length > 0);
	}
	
	@Test
	public void testGetCategories() {
		Category[] result = template.getForObject("http://localhost:8080/api/servicemodules/categories", Category[].class);

	    Assert.assertTrue(result.length > 0);
	}
	
	@Test
	public void testGetSizes() {
		Size[] result = template.getForObject("http://localhost:8080/api/servicemodules/sizes", Size[].class);

	    Assert.assertTrue(result.length > 0);
	}
	 
	 
}
