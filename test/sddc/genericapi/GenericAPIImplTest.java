package sddc.genericapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;

public class GenericAPIImplTest {

	private IGenericAPIFacade api,api2;
	private String storageConfig, networkConfig, domainConfig;

	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		storageConfig = "<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>";
	}

	@After
	public void tearDown() throws Exception {
		api.disconnect();
	}
	
	/* Connect Test */
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testConnect() throws LibvirtException {
		api.connect("test://", false);
	}
	
	/* Storage Tests*/
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testCreateStorage() throws LibvirtException {
		String failingConfig = "<pool>";
		String storageUuid = api.createStorage(failingConfig);
		Assert.assertNull(storageUuid);
		storageUuid = api.createStorage(storageConfig);
		Assert.assertNotNull(storageUuid);
	}
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testDeleteStorage() throws LibvirtException {
		String storageUuid = api.createStorage(storageConfig);
		api.deleteStorage(storageUuid);
		Assert.assertNull(api.getStorage(storageUuid));
	}
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testDeleteStoragefailing() throws LibvirtException {
		String failingUuid = "12345";
		api.deleteStorage(failingUuid);
	}
	
	@Test
	public void testGetStorage() throws LibvirtException {
		String storageUuid = api.createStorage(storageConfig);
		Assert.assertNotNull(api.getStorage(storageUuid));
	}
	
	/* Network Tests */
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testNetwork() throws LibvirtException {
		String networkConfig = "<network><name>default6</name><bridge name=\"virbr0\" /><forward mode=\"nat\"/><ip address=\"192.168.122.1\" netmask=\"255.255.255.0\">"
				+ "<dhcp><range start=\"192.168.122.2\" end=\"192.168.122.254\" /></dhcp>"
        + "</ip><ip family=\"ipv6\" address=\"2001:db8:ca2:2::1\" prefix=\"64\" >"
        +  "<dhcp><range start=\"2001:db8:ca2:2:1::10\" end=\"2001:db8:ca2:2:1::ff\" /></dhcp></ip></network>";
		String networkUuid;
		
		networkUuid = api.createNetwork(networkConfig);
		Assert.assertNotNull(networkUuid);
		Assert.assertNotNull(api.getNetwork(networkUuid));
		api.deleteNetwork(networkUuid);
		Assert.assertNull(api.getNetwork(networkUuid));
		
	}
	
	
}
