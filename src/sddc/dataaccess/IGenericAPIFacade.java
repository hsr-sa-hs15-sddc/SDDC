package sddc.dataaccess;

import org.libvirt.LibvirtException;

public interface IGenericAPIFacade {
	
	void connect(String uri, boolean readOnly) 
			throws LibvirtException;
	
	void disconnect();
	
	//Compute
	
	String createCompute(String config) 
			throws LibvirtException;
	
	void deleteCompute(String uuid) 
			throws LibvirtException;
	
	String getCompute(String uuid) 
			throws LibvirtException;
	
	//Storage
	
	String createStorage(String config) 
			throws LibvirtException;
	
	void deleteStorage(String uuid) 
			throws LibvirtException;

	String getStorage(String uuid) 
			throws LibvirtException;
}
