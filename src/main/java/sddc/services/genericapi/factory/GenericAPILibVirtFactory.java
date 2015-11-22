package sddc.services.genericapi.factory;

import java.nio.charset.Charset;

import org.libvirt.LibvirtException;

import sddc.services.genericapi.GenericAPILibVirt;
import sddc.services.genericapi.IGenericAPIFacade;
import sddc.util.FileUtil;

public class GenericAPILibVirtFactory {
	
	private static IGenericAPIFacade api;
	private static final String CONFIG_PATH = "src/main/resources/GenericAPILibVirtConfig.conf";

	public static IGenericAPIFacade getInstance() {
		if(api == null) {
			api = new GenericAPILibVirt();
			
			try {
				api.connect(FileUtil.getValueOfAttributeFromFile(
						FileUtil.getContentOfFile(CONFIG_PATH, Charset.defaultCharset(), true), "ConnectionURI"), false);
				
			} catch (LibvirtException e) {
				e.printStackTrace();
			}
		}
		
		return api;	
	}
}
