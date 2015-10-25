package sddc.genericapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;
import sddc.persistence.PersistenceFake;

//Kein richtiger Test

public class GenericAPIImplTest {

	private IGenericAPIFacade api;
	private IPersistenceFacade db;
	private int hash;

	@Before
	public void setUp() throws Exception {
		api = new GenericAPIImpl();
		api.connect("test:///default", false);
		
		db = new PersistenceFake();
		hash = db.store("<pool type=\"dir\"><name>virtimages</name><target><path>/home/sddc/images</path></target></pool>");
	}

	@After
	public void tearDown() throws Exception {
		api.disconnect();
	}

	@Test
	public void testCreateStorage() throws LibvirtException {
		api.createStorage(db.get(hash));
	}
	
	@Test
	public void testDeleteStorage() {
		
	}

}
