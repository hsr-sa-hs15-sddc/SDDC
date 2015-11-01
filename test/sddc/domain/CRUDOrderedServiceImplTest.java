package sddc.domain;

import org.junit.Before;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;
import sddc.genericapi.GenericAPILibVirt;
import sddc.persistence.PersistenceFake;



public class CRUDOrderedServiceImplTest {
	
	private IGenericAPIFacade api;
	private IPersistenceFacade persistence;
	
	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		api.connect("test:///default", false);
		persistence = new PersistenceFake();
	}
	
	
	
	
	
	
	
}
