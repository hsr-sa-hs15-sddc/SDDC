package sddc.genericapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;

public class GenericAPIImplTest {

	private IGenericAPIFacade api;
	private String storageConfig = "<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>";
	private String storageUuid;

	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		
		storageUuid = api.createStorage(storageConfig);
	}

	@After
	public void tearDown() throws Exception {
		api.disconnect();
	}
	
	@Test
	public void testCreateStorage() throws LibvirtException {
		String uuid = api.createStorage(storageConfig);
		Assert.assertNotNull(uuid);
		Assert.assertNotNull(api.getStorage(uuid));
	}
	
	@Test
	public void testDeleteStorage() throws LibvirtException {
		api.deleteStorage(storageUuid);
		Assert.assertNull(api.getStorage(storageUuid));
	}
	
	@Test
	public void testGetStorage() throws LibvirtException {
		Assert.assertNotNull(api.getStorage(storageUuid));
		api.deleteStorage(storageUuid);
		Assert.assertNull(api.getStorage(storageUuid));
	}
}
