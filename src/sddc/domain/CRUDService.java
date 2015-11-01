package sddc.domain;

import org.libvirt.LibvirtException;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;

public class CRUDService {
	
	private IPersistenceFacade persistence;
	private IGenericAPIFacade api;
	
	public CRUDService(IGenericAPIFacade api, IPersistenceFacade persistence) {
		this.api = api;
		this.persistence = persistence;
	}
	
	public void orderService(int id) throws LibvirtException {
		String service = persistence.getService(id);
		
		String network = getContentOfXMLTagName(service, "net");
		api.createNetwork(network);
		
		String storage = getContentOfXMLTagName(service, "storage");
		api.createStorage(storage);
		
		String compute = getContentOfXMLTagName(service, "compute");
		api.createCompute(compute);
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
