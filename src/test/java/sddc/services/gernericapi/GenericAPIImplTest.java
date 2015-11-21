package sddc.services.gernericapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.libvirt.LibvirtException;

import sddc.services.genericapi.GenericAPILibVirt;
import sddc.services.genericapi.IGenericAPIFacade;

public class GenericAPIImplTest {

	private IGenericAPIFacade api;
	private String storageConfig, networkConfig, computeConfig;

	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		storageConfig = "<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>";
		networkConfig = "<network><name>default6</name><bridge name=\"virbr0\" /><forward mode=\"nat\"/><ip address=\"192.168.122.1\" netmask=\"255.255.255.0\">"
			+ "<dhcp><range start=\"192.168.122.2\" end=\"192.168.122.254\" /></dhcp>"
		    + "</ip><ip family=\"ipv6\" address=\"2001:db8:ca2:2::1\" prefix=\"64\" >"
		    +  "<dhcp><range start=\"2001:db8:ca2:2:1::10\" end=\"2001:db8:ca2:2:1::ff\" /></dhcp></ip></network>";
		computeConfig = "<domain type='test' xmlns:qemu='http://libvirt.org/schemas/domain/qemu/1.0'>"
		  + "<name>QEmu-fedora-i686</name><memory>219200</memory><os><type arch='i686' machine='pc'>hvm</type></os>"
		  + "<devices><emulator>/usr/bin/qemu-system-x86_64</emulator></devices><qemu:commandline>"
		  + "<qemu:arg value='-newarg'/><qemu:env name='QEMU_ENV' value='VAL'/></qemu:commandline></domain>";
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
	
	/* Compute Tests */
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testCreateCompute() throws LibvirtException {
		String failingConfig = "<domain>";
		String computeUuid = api.createCompute(failingConfig);
		Assert.assertNull(computeUuid);
		computeUuid = api.createCompute(computeConfig);
		Assert.assertNotNull(computeUuid);
	}
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testDeleteCompute() throws LibvirtException {
		String computeUuid = api.createCompute(computeConfig);
		api.deleteCompute(computeUuid);
		Assert.assertNull(api.getCompute(computeUuid));
	}
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testDeleteComputefailing() throws LibvirtException {
		String failingUuid = "12345";
		api.deleteCompute(failingUuid);
	}
	
	@Test
	public void testGetCompute() throws LibvirtException {
		String computeUuid = api.createCompute(computeConfig);
		Assert.assertNotNull(api.getCompute(computeUuid));
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
	public void testCreateNetwork() throws LibvirtException {
		String failingConfig = "<network>";
		String networkUuid = api.createNetwork(failingConfig);
		Assert.assertNull(networkUuid);
		networkUuid = api.createNetwork(networkConfig);
		Assert.assertNotNull(networkUuid);
	}
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testDeleteNetwork() throws LibvirtException {
		String networkUuid = api.createNetwork(networkConfig);
		api.deleteNetwork(networkUuid);
		Assert.assertNull(api.getNetwork(networkUuid));
	}
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testDeleteNetworkfailing() throws LibvirtException {
		String failingUuid = "12345";
		api.deleteNetwork(failingUuid);
	}
	
	@Test
	public void testGetNetwork() throws LibvirtException {
		String networkUuid = api.createNetwork(networkConfig);
		Assert.assertNotNull(api.getNetwork(networkUuid));
	}
	
	
}
