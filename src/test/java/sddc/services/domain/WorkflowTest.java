package sddc.services.domain;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import sddc.ApplicationMain;
import sddc.services.OrderedServiceRepo;
import sddc.services.ServiceModuleRepo;
import sddc.services.ServiceRepo;
import sddc.util.FileUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("dev")
@WebAppConfiguration
public class WorkflowTest {

	private String storageConfig, networkConfig, computeConfig;
	private ServiceModule module1, module2, module3, computeModule, storageModule, networkModule;
	private Set<ServiceModule> modules = new HashSet<ServiceModule>();
	private Service  service;
	private String failingConfig = "Hello World-{{UUID}}";
	
	@Autowired
	private OrderedServiceRepo orderedRepo;
	
	@Autowired
	private ServiceModuleRepo modulesRepo;
	
	@Autowired
	private ServiceRepo repo;
	
	private Workflow workflow = new Workflow();

	@Before
	public void setUp() throws Exception {
		orderedRepo.deleteAll();
		repo.deleteAll();
		orderedRepo.deleteAll();
		modulesRepo.deleteAll();
		storageConfig = FileUtil.getContentOfFile("src/test/resources/LibVirtStorageConfigExample.xml",
				Charset.defaultCharset(), false);
						
		networkConfig = FileUtil.getContentOfFile("src/test/resources/LibVirtNetworkConfigExample.xml",
Charset.defaultCharset(), false);

		computeConfig = FileUtil.getContentOfFile("src/test/resources/LibVirtComputeConfigExample.xml",
				Charset.defaultCharset(), false);
				
		module1 = new ServiceModule("Compute",Size.S, Provider.LibVirt, Category.Compute,computeConfig);
		module2 = new ServiceModule("Storage",Size.M, Provider.LibVirt, Category.Storage,storageConfig);
		module3 = new ServiceModule("Network",Size.L, Provider.LibVirt, Category.Network,networkConfig);
		modules.add(module1);
		modules.add(module2);
		modules.add(module3);
		service = new Service("Testservice",modules);
		repo.save(service);
	}
	
	
	/* Test Service */
	@Test
	public void testService() {
		Assert.assertEquals("Testservice",repo.findByServiceName("Testservice").getServiceName());
		Assert.assertTrue(repo.findByServiceName("Testservice").getModules().size() == 3);
	}
	
	/* Test Modules */
	@Test
	public void testModules() {
		Assert.assertEquals("Network",modulesRepo.findByName("Network").getName());
		Assert.assertEquals( "Storage", modulesRepo.findByName("Storage").getName());
		Assert.assertEquals("Compute", modulesRepo.findByName("Compute").getName());
		Assert.assertTrue(modulesRepo.findAll().size() == 3);
	}
	
	/* Order Service */
	
	@Test
	public void testOrderAndCancelService() {
		workflow.orderService(service);
		Assert.assertEquals("Testservice",orderedRepo.findByName("Testservice").getOrderedServiceName());
		Assert.assertTrue(orderedRepo.count() > 0);
		Assert.assertTrue(orderedRepo.findByName("Testservice").getIdentifiers().size() == 3);
		workflow.cancelService(orderedRepo.findByName("Testservice"));
		Assert.assertNull(orderedRepo.findByName("Testservice"));
	}
	
	@Test
	public void testExceptionCompute() {
		Set<ServiceModule> modules = new HashSet<ServiceModule>();
		computeModule = new ServiceModule("FailingCompute",Size.S, Provider.LibVirt, Category.Compute, failingConfig );
		modules.add(computeModule);
		Service service = new Service("Hello Exception",modules);
		repo.save(service);
		modulesRepo.save(modules);
		workflow.orderService(service);
	}
	
	@Test
	public void testExceptionStorage() {
		Set<ServiceModule> modules = new HashSet<ServiceModule>();
		storageModule = new ServiceModule("FailingStorage",Size.S, Provider.LibVirt, Category.Compute, failingConfig );
		modules.add(storageModule);
		Service service = new Service("Hello Exception",modules);
		repo.save(service);
		modulesRepo.save(modules);
		workflow.orderService(service);
	}
	
	@Test
	public void testExceptionNetwork() {
		Set<ServiceModule> modules = new HashSet<ServiceModule>();
		networkModule = new ServiceModule("FailingNetwork", Size.S, Provider.LibVirt, Category.Compute, failingConfig );
		modules.add(networkModule);
		Service service = new Service("Hello Exception",modules);
		repo.save(service);
		modulesRepo.save(modules);
		workflow.orderService(service);
	}
	
	
}
