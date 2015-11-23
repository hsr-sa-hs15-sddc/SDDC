package sddc.services.gernericapi;

import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.libvirt.LibvirtException;

import sddc.services.genericapi.GenericAPILibVirt;
import sddc.services.genericapi.IGenericAPIFacade;
import sddc.util.FileUtil;

public class GenericAPIImplTest {

	private IGenericAPIFacade api;
	private String storageConfig, networkConfig, computeConfig;

	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		
		storageConfig = FileUtil.getContentOfFile("src/test/resources/LibVirtStorageConfigExample.xml", 
				Charset.defaultCharset(), false);
						
		networkConfig = FileUtil.getContentOfFile("src/test/resources/LibVirtNetworkConfigExample.xml", 
				Charset.defaultCharset(), false);
				
		computeConfig = FileUtil.getContentOfFile("src/test/resources/LibVirtComputeConfigExample.xml", 
				Charset.defaultCharset(), false);
				
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
