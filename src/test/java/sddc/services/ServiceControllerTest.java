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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@Configuration
@Profile("dev")
public class ServiceControllerTest {
	
	@Autowired
	private ServiceController controller;
	
	@Autowired
    private ApplicationContext context;
	
	@Autowired
	private ServiceRepo repo;
	
	
	private String networkconfig = FileUtil.getContentOfFile("src/test/resources/LibVirtNetworkConfigExample.xml", Charset.defaultCharset(), false);
			
			/*
			"<network><name>default6</name><bridge name=\"virbr0\" /><forward mode=\"nat\"/><ip address=\"192.168.122.1\" netmask=\"255.255.255.0\">"
			+ "<dhcp><range start=\"192.168.122.2\" end=\"192.168.122.254\" /></dhcp>"
		    + "</ip><ip family=\"ipv6\" address=\"2001:db8:ca2:2::1\" prefix=\"64\" >"
		    +  "<dhcp><range start=\"2001:db8:ca2:2:1::10\" end=\"2001:db8:ca2:2:1::ff\" /></dhcp></ip></network>";
		    */
	
	@Before
	public void setUp() {
	repo.deleteAll();
	Set<ServiceModule> modules = new HashSet<ServiceModule>();
	modules.add(new ServiceModule("Network Bridge",Size.S,Category.Network,networkconfig));
    repo.save(new Service("Network Virtual Bridge",modules));
	}
	
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
