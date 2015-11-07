package sddc.genericapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;
import org.libvirt.Network;
import org.libvirt.StoragePool;

import sddc.dataaccess.IGenericAPIFacade;

public class GenericAPILibVirt implements IGenericAPIFacade {
	
	private Connect conn;
	private static final Logger logger = LogManager.getLogger("GenericAPILibVirt");

	@Override
	public void connect(String uri, boolean readOnly) throws LibvirtException {
		logger.trace("connect(uri: " + uri + ", readOnly: " + readOnly + ")");
		try {
			conn = new Connect(uri, readOnly);
		} catch (LibvirtException libvirtException) {
			logger.error("Could not connect to: " + uri);
			throw libvirtException;
		}
		
	}

	@Override
	public void disconnect() throws LibvirtException {
		logger.trace("disconnect()");
		try {
			conn.close();
		} catch (LibvirtException libvirtException) {
			logger.error("Could not disconnect: " + libvirtException.getMessage());
			throw libvirtException;
		}
	}
	
	@Override
	public String createCompute(String config) throws LibvirtException {
		logger.trace("createCompute(congig: " + config + ")");
		try {
			Domain domain = conn.domainDefineXML(config);
			return domain.getUUIDString();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not create Compute: " + libvirtException.getMessage());
			throw libvirtException;
		}
		
	}

	@Override
	public void deleteCompute(String uuid) throws LibvirtException {
		logger.trace("deleteCompute(uuid: " + uuid + ")");
		try {
			Domain domain = conn.domainLookupByUUIDString(uuid);
			domain.destroy();
			domain.undefine();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not delete Compute: " + libvirtException.getMessage());
			throw libvirtException;
		}
		
	}

	@Override
	public String getCompute(String uuid) throws LibvirtException {
		logger.trace("getCompute(uuid: " + uuid + ")");
		try {
			Domain domain = conn.domainLookupByUUIDString(uuid);
			return domain.toString();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Compute: " + libvirtException.getLocalizedMessage());
			throw libvirtException;
		}
	}

	@Override
	public String createStorage(String config) throws LibvirtException {
		logger.trace("createStorage(config: " + config + ")");
		try {
			StoragePool storagePool = conn.storagePoolCreateXML(config, 0);
			return storagePool.getUUIDString();
		} catch (LibvirtException libvirtException) {
			logger.error("Could not create Storage: " + libvirtException.getMessage());
			throw libvirtException;
		}
	}

	@Override
	public void deleteStorage(String uuid) throws LibvirtException {
		logger.trace("deleteStorage(uuid: " + uuid + ")");
		try {
			StoragePool storagePool = conn.storagePoolLookupByUUIDString(uuid);
			storagePool.destroy();
			storagePool.undefine();
		} catch (LibvirtException libvirtException) {
			logger.error("Could not delete Storage: " + libvirtException.getMessage());
			throw libvirtException;
		}
	}

	@Override
	public String getStorage(String uuid) throws LibvirtException {
		logger.trace("getStorage(uuid: " + uuid + ")");
		try {
			StoragePool storagePool = conn.storagePoolLookupByUUIDString(uuid);
			return storagePool.toString();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Storage: " + libvirtException.getMessage());
			throw libvirtException;
		}
	}

	@Override
	public String createNetwork(String config) throws LibvirtException {
		logger.trace("createNetwork(config: " + config + ")");
		try {
			Network network = conn.networkCreateXML(config);
			return network.getUUIDString();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not create Network: " + libvirtException.getMessage());
			throw libvirtException;
		}
	}

	@Override
	public void deleteNetwork(String uuid) throws LibvirtException {
		logger.trace("deleteNetwork(uuid: " + uuid + ")");
		try {
			Network network = conn.networkLookupByUUIDString(uuid);
			network.destroy();
			//network.undefine();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not delete Network: " + libvirtException.getMessage());
			throw libvirtException;
		}
	}

	@Override
	public String getNetwork(String uuid) throws LibvirtException {
		logger.trace("getNetwork(uuid: " + uuid + ")");
		try {
			Network network = conn.networkLookupByUUIDString(uuid);
			return network.toString();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Network: " + libvirtException.getMessage());
			throw libvirtException;
		}
	}
}
