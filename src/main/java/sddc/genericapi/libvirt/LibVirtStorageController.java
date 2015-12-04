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
		
		if(module == null)
			return null;
		
		String config = replaceUUID(module.getConfig());
		
		logger.trace("createStorage(config: " + config + ")");
		try {
			StoragePool storagePool = connect.storagePoolCreateXML(config, 0);
			return new Identifier(module.getName(),storagePool.getUUIDString(), module.getCategory(), module.getSize(), module.getProvider());
		} catch (LibvirtException libvirtException) {
			logger.error("Could not create Storage: " + libvirtException.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Identifier identifier) {
		
		if(identifier == null)
			return;
		
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
		
		Map<String, String> infos = new HashMap<>();
		if(identifier == null)
			return infos;
		
		logger.trace("getInformations(identifier: " + identifier.getUuid() + ")");
		
		StoragePoolInfo storagePoolInfo;
		String storageName;
		
		try {
			storagePoolInfo = connect.storagePoolLookupByUUIDString(identifier.getUuid()).getInfo();
			storageName = connect.storagePoolLookupByUUIDString(identifier.getUuid()).getName();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Informations: " + libvirtException.getMessage());
			return infos;
		}
		infos.put("name", storageName);
		infos.put("capacity", String.valueOf(storagePoolInfo.capacity));
		//...
		
		return infos;
	}

}
