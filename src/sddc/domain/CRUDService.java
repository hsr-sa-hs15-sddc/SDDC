package sddc.domain;

import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;

public class CRUDService {
	
	private IPersistenceFacade persistence;
	private IGenericAPIFacade api;
	private String[] order = new String[3];
	
	public CRUDService(IGenericAPIFacade api, IPersistenceFacade persistence) {
		this.api = api;
		this.persistence = persistence;
	}
	
	public void orderService(int id) throws LibvirtException {
		String service = persistence.getService(id);

		String network = getContentOfXMLTagName(service, "net");
		if(network != null)
			order[0] = api.createNetwork(network);
		
		String storage = getContentOfXMLTagName(service, "storage");
		if(storage != null)
			order[1] =	api.createStorage(storage);
		
		String compute = getContentOfXMLTagName(service, "compute");
		if(compute != null)
			order[2] = api.createCompute(compute);
		
		persistence.storeOrderedService(order);
	}
	
	public String[] getServices() {
		return persistence.getServices();
	}
	
	public String getService(int id) {
		return persistence.getService(id);
	}
	
	private String getContentOfXMLTagName(String xml, String tag) {
		if(xml.indexOf("<" + tag + ">") <= -1)
			return null;
		
		return xml.substring(xml.indexOf("<" + tag + ">") + ("<" + tag + ">").length(), 
				xml.indexOf("</" + tag + ">"));
	}

}
