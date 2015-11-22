package sddc.services.genericapi.factory;

import org.libvirt.LibvirtException;

import sddc.services.genericapi.GenericAPILibVirt;
import sddc.services.genericapi.IGenericAPIFacade;

public class GenericAPILibVirtFactory {
	
	private static IGenericAPIFacade api;

	public static IGenericAPIFacade getInstance() {
		if(api == null) {
			api = new GenericAPILibVirt();
			try {
				api.connect("test:///default", false);
			} catch (LibvirtException e) {
				e.printStackTrace();
			}
		}
		
		return api;	
	}
}
