package sddc.dataaccess;

import org.libvirt.LibvirtException;

public interface IGenericAPIFacade {
	
	void connect(String uri, boolean readOnly) 
			throws LibvirtException;
	
	void disconnect();
	
	String createStorage(String config) 
			throws LibvirtException;
	
	void deleteStorage(String name) 
			throws LibvirtException;

	String getStorage(String name) 
			throws LibvirtException;
}
