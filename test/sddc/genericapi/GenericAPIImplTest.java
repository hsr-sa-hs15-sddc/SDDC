package sddc.genericapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;

public class GenericAPIImplTest {

	private IGenericAPIFacade api;
	
	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
	}

	@After
	public void tearDown() throws Exception {
		api.disconnect();
	}
	
	@Test
	public void testStorage() throws LibvirtException {
		String storageConfig = "<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>";
		String storageUuid;
		
		storageUuid = api.createStorage(storageConfig);
		Assert.assertNotNull(storageUuid);
		Assert.assertNotNull(api.getStorage(storageUuid));
		api.deleteStorage(storageUuid);
		//Assert.assertNull(api.getStorage(storageUuid));
		
	}
}
