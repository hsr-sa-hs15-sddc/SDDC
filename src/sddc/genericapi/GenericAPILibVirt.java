package sddc.genericapi;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;
import org.libvirt.Network;
import org.libvirt.StoragePool;

import sddc.dataaccess.IGenericAPIFacade;

public class GenericAPILibVirt implements IGenericAPIFacade {
	
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
	public String createCompute(String config) throws LibvirtException {
		try {
			Domain domain = conn.domainDefineXML(config);
			return domain.getUUIDString();
		} catch(LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
		
	}

	@Override
	public void deleteCompute(String uuid) throws LibvirtException {
		try {
			Domain domain = conn.domainLookupByUUIDString(uuid);
			domain.destroy();
			domain.undefine();
		} catch(LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
		
	}

	@Override
	public String getCompute(String uuid) throws LibvirtException {
		try {
			Domain domain = conn.domainLookupByUUIDString(uuid);
			return domain.toString();
		} catch(LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

	@Override
	public String createStorage(String config) throws LibvirtException {
		try {
			StoragePool storagePool = conn.storagePoolCreateXML(config, 0);
			return storagePool.getUUIDString();
		} catch (LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

	@Override
	public void deleteStorage(String uuid) throws LibvirtException {
		try {
			StoragePool storagePool = conn.storagePoolLookupByUUIDString(uuid);
			storagePool.destroy();
			storagePool.undefine();
		} catch (LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

	@Override
	public String getStorage(String uuid) throws LibvirtException {
		try {
			StoragePool storagePool = conn.storagePoolLookupByUUIDString(uuid);
			return storagePool.toString();
		} catch(LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

	@Override
	public String createNetwork(String config) throws LibvirtException {
		try {
			Network network = conn.networkCreateXML(config);
			return network.getUUIDString();
		} catch(LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

	@Override
	public void deleteNetwork(String uuid) throws LibvirtException {
		try {
			Network network = conn.networkLookupByUUIDString(uuid);
			network.destroy();
		} catch(LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

	@Override
	public String getNetwork(String uuid) throws LibvirtException {
		try {
			Network network = conn.networkLookupByUUIDString(uuid);
			return network.toString();
		} catch(LibvirtException libvirtException) {
			//Logging
			throw libvirtException;
		}
	}

	

}
