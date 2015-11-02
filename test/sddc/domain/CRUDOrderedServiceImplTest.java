package sddc.domain;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.*;
import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;
import sddc.genericapi.GenericAPILibVirt;
import sddc.persistence.PersistenceFake;



public class CRUDOrderedServiceImplTest {
	
	private IGenericAPIFacade api;
	private IPersistenceFacade persistence;
	private CRUDService service;
	private CRUDOrderedService orderedService;
	private int identifier, storedidentifier;
	private String file;
	
	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		persistence = new PersistenceFake();
		service = new CRUDService(api, persistence);
		orderedService = new CRUDOrderedService(api, persistence);
		file = readFile("testservice.xml",StandardCharsets.UTF_8);
		identifier = persistence.storeService(file);
		storedidentifier = service.orderService(identifier);
	}
	
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testCancelNetwork() throws LibvirtException {
		assertNotNull(service.getService(identifier));
		assertTrue(Arrays.deepToString(orderedService.getOrderedServices()).length()>2);
		orderedService.cancelOrderedService(storedidentifier);
		String[] str = orderedService.getOrderedService(storedidentifier);
		api.getNetwork(str[0]);
	}
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testCancelStorage() throws LibvirtException {
		assertNotNull(service.getService(identifier));
		assertTrue(Arrays.deepToString(orderedService.getOrderedServices()).length()>2);
		orderedService.cancelOrderedService(storedidentifier);
		String[] str = orderedService.getOrderedService(storedidentifier);
		api.getStorage(str[1]);
	}
	
	
	@Test(expected=org.libvirt.LibvirtException.class)
	public void testCancelCompute() throws LibvirtException {
		assertNotNull(service.getService(identifier));
		assertTrue(Arrays.deepToString(orderedService.getOrderedServices()).length()>2);
		orderedService.cancelOrderedService(storedidentifier);
		String[] str = orderedService.getOrderedService(storedidentifier);
		api.getCompute(str[2]);
	}
	
	
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}	
	
	
	
	
}
