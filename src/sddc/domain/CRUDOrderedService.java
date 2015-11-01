package sddc.domain;

import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;

public class CRUDOrderedService {
	
	private IPersistenceFacade persistence;
	private IGenericAPIFacade api;
	private String[] cancel = new String[3];
	
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
		if(cancel[0] != null)
			api.deleteNetwork(cancel[0]);
		if(cancel[1] != null)
			api.deleteStorage(cancel[1]);
		if(cancel[2] != null)
			api.deleteCompute(cancel[2]);
	}
	
	
	
}
