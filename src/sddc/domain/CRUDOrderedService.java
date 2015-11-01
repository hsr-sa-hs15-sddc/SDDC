package sddc.domain;

import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;

public class CRUDOrderedService {
	
	private IPersistenceFacade persistence;
	private IGenericAPIFacade api;
	private String[] cancel;
	
	public CRUDOrderedService(IGenericAPIFacade api, IPersistenceFacade persistence) {
		this.api = api;
		this.persistence = persistence;
	}

	public String[][] getOrderedServices() {
		return persistence.getOrderedServices();
	}
	
	public String[] getOrderedService(int id) {
		return persistence.getOrderedService(id);
	}
	
	public void cancelOrderedService(int id) throws LibvirtException {
		cancel = persistence.getOrderedService(id);
		
		api.deleteNetwork(cancel[0]);
		
		api.deleteStorage(cancel[1]);
		
		api.deleteCompute(cancel[2]);
		
	}
	
	
	
}
