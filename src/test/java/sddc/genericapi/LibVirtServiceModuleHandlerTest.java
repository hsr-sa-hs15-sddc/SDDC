package sddc.genericapi;

import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import junit.framework.Assert;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.Provider;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;
import sddc.util.FileUtil;

public class LibVirtServiceModuleHandlerTest {
	
	private IServiceModuleHandler handler;

	@Before
	public void setUp() throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
		handler = (IServiceModuleHandler) context.getBean("LibVirtServiceModuleHandler");
		
		((ConfigurableApplicationContext)context).close();
	}

	@Test
	public void createTest() {
		
		String config = FileUtil.getContentOfFile("src/test/resources/LibVirtStorageConfigExample.xml", 
				Charset.defaultCharset(), false);
		
		ServiceModule module = new ServiceModule("StorageConfigTest", Size.L, Provider.LibVirt, Category.Storage, config);
				
		Identifier identifier = handler.create(module);
		
		Assert.assertNotNull(identifier);
	}

}
