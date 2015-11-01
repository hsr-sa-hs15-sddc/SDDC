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
	//private String[] order = {"net", "compute", "storage"};
	
	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		persistence = new PersistenceFake();
		service = new CRUDService(api, persistence);
		orderedService = new CRUDOrderedService(api, persistence);
		file = readFile("testservice.xml",StandardCharsets.UTF_8);
		identifier = persistence.storeService(file);
	}
	
	
	@Test
	public void testOrderService() throws LibvirtException {
		assertNotNull(service.getService(identifier));
		service.orderService(identifier);
		assertTrue(Arrays.deepToString(orderedService.getOrderedServices()).length()>2);
	}
	
	/* Nicht möglich ohne id des OrderedService
	@Test
	public void testCancelOrderedServices() throws LibvirtException {
		storedidentifier = persistence.storeOrderedService(order);
		String[] str = orderedService.getOrderedService(storedidentifier);
		assertArrayEquals(order,orderedService.getOrderedService(storedidentifier));
	}*/
	
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}	
	
	
	
	
}
