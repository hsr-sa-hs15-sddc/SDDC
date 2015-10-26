package sddc.genericapi;

import org.junit.After;
import org.junit.Assert;
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
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		
		db = new PersistenceFake();
		hash = db.storeService("<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>");
	}

	@After
	public void tearDown() throws Exception {
		api.disconnect();
	}

	@Test
	public void testCreateStorage() throws LibvirtException {
		String uuid = api.createStorage(db.getService(hash));
		Assert.assertNotNull(uuid);
	}
	
	@Test
	public void testDeleteStorage() {
		
	}
}
