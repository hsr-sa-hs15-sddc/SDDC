package sddc.genericapi;

import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.StoragePool;

import sddc.dataaccess.IGenericAPIFacade;

public class GenericAPIImpl implements IGenericAPIFacade {
	
	private Connect conn;

	@Override
	public void connect(String uri, boolean readOnly) throws LibvirtException {
		try {
			conn = new Connect(uri, readOnly);
		} catch (LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createStorage(String config) throws LibvirtException {
		try {
			conn.storagePoolCreateXML(config, 0);
		} catch (LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
		
		
	}

	@Override
	public void deleteStorage(String name) throws LibvirtException {
		try {
			StoragePool storagePool = conn.storagePoolLookupByName(name);
			storagePool.destroy();
			storagePool.undefine();
		} catch (LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

}
