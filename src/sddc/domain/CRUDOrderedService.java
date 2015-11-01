package sddc.domain;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;

public class CRUDOrderedService {
	
	private IPersistenceFacade persistence;
	private IGenericAPIFacade api;
	
	public CRUDOrderedService(IGenericAPIFacade api, IPersistenceFacade persistence) {
		this.api = api;
		this.persistence = persistence;
	}

	public String[] getOrderedServices() {
		return persistence.getOrderedServices();
	}
	
	public String getOrderedService(int id) {
		return persistence.getOrderedService(id);
	}
	
	public void cancelOrderedService(int id) {
		
	}
	
	
	
}
