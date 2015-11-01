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
		order[0] = api.createNetwork(network);
		
		String storage = getContentOfXMLTagName(service, "storage");
		order[1] = api.createStorage(storage);
		
		String compute = getContentOfXMLTagName(service, "compute");
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
		return xml.substring(xml.indexOf("<" + tag + ">") + ("<" + tag + ">").length(), 
				xml.indexOf("</" + tag + ">"));
	}

}
