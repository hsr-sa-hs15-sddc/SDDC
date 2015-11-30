package sddc.genericapi;

import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
		Resource resource = new ClassPathResource("applicationContext.xml");
		
		@SuppressWarnings("deprecation")
		BeanFactory factory = new XmlBeanFactory(resource);
		
		handler = (IServiceModuleHandler) factory.getBean("LibVirtServiceModuleHandler");
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
