package sddc.domain;

import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;
import sddc.genericapi.GenericAPILibVirt;
import sddc.persistence.PersistenceFake;

public class CRUDService {
	
	private IPersistenceFacade persistence;
	private IGenericAPIFacade api;
	
	public CRUDService() {
		//Factory
	}
	

	
	public void orderService(int id) {
		
	}
	
	public String getServices() {
		return null;
	}
	
	public String getService(int id) {
		return null;
	}

}
