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

public class CRUDServiceImplTest {
	
	private IGenericAPIFacade api;
	private IPersistenceFacade persistence;
	private CRUDService service;
	private int identifier;
	private String file;
	
	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		persistence = new PersistenceFake();
		service = new CRUDService(api, persistence);
		file = readFile("testservice.xml",StandardCharsets.UTF_8);
		identifier = persistence.storeService(file);
	}
	
	
	@Test
	public void testOrderService() throws LibvirtException {
		service.orderService(identifier);
		assertNotNull(service.getService(identifier));
	}
	
	@Test
	public void testGetServices() {
		assertEquals("[" + file + "]",Arrays.toString(service.getServices()));
	}
	

	
static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}


