package sddc.genericapi.libvirt;

import java.util.HashMap;
import java.util.Map;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.StoragePool;
import org.libvirt.StoragePoolInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.ServiceModule;


public class LibVirtStorageController extends LibVirtController {
	
	private static final Logger logger = LoggerFactory.getLogger(LibVirtStorageController.class.getName());

	public LibVirtStorageController(Connect connect) {
		super(Category.Storage, connect);
	}

	@Override
	public Identifier create(ServiceModule module) {
		String config = replaceUUID(module.getConfig());
		
		logger.trace("createStorage(config: " + config + ")");
		try {
			StoragePool storagePool = connect.storagePoolCreateXML(config, 0);
			return new Identifier(storagePool.getUUIDString(), module.getCategory(), module.getSize(), module.getProvider());
		} catch (LibvirtException libvirtException) {
			logger.error("Could not create Storage: " + libvirtException.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Identifier identifier) {
		logger.trace("deleteStorage(uuid: " + identifier.getUuid() + ")");
		try {
			StoragePool storagePool = connect.storagePoolLookupByUUIDString(identifier.getUuid());
			storagePool.destroy();
			storagePool.undefine();
		} catch (LibvirtException libvirtException) {
			logger.error("Could not delete Storage: " + libvirtException.getMessage());
		}
		
	}

	@Override
	public Map<String, String> getInformations(Identifier identifier) {
		
		logger.trace("getInformations(identifier: " + identifier.getUuid() + ")");
		
		StoragePoolInfo storagePoolInfo;
		Map<String, String> infos = new HashMap<>();
		
		try {
			storagePoolInfo = connect.storagePoolLookupByUUIDString(identifier.getUuid()).getInfo();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Informations: " + libvirtException.getMessage());
			return infos;
		}
		
		infos.put("capacity", String.valueOf(storagePoolInfo.capacity));
		infos.put("available", String.valueOf(storagePoolInfo.available));
		//...
		
		return infos;
	}

}
