package sddc.genericapi.libvirt;

import java.util.HashMap;
import java.util.Map;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.ServiceModule;


public class LibVirtNetworkController extends LibVirtController {
	
	private static final Logger logger = LoggerFactory.getLogger(LibVirtNetworkController.class.getName());

	public LibVirtNetworkController(Connect connect) {
		super(Category.Network, connect);
	}

	@Override
	public Identifier create(ServiceModule module) {
		
		if(module == null)
			return null;
		
		String config = replaceUUID(module.getConfig());
		
		logger.trace("createNetwork(config: " + config + ")");
		try {
			Network network = connect.networkCreateXML(config);
			return new Identifier(module.getName(),network.getUUIDString(), module.getCategory(), module.getSize(), module.getProvider());
		} catch(LibvirtException libvirtException) {
			logger.error("Could not create Network: " + libvirtException.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Identifier identifier) {
		
		if(identifier == null)
			return;
		
		logger.trace("deleteNetwork(uuid: " + identifier.getUuid() + ")");
		try {
			Network network = connect.networkLookupByUUIDString(identifier.getUuid());
			network.destroy();
			//network.undefine();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not delete Network: " + libvirtException.getMessage());
		}
		
	}

	@Override
	public Map<String, String> getInformations(Identifier identifier) {
		
		Map<String, String> infos = new HashMap<>();
		
		if(identifier == null)
			return infos;
		
		logger.trace("getInformations(identifier: " + identifier.getUuid() + ")");
		
		Network network;
		
		try {
			network = connect.networkLookupByUUIDString(identifier.getUuid());
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Informations: " + libvirtException.getMessage());
			return infos;
		}
		
		try {
			infos.put("name", network.getName());
			infos.put("bridgename", network.getBridgeName());
			//...
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Informations: " + libvirtException.getMessage());
			return infos;
		}
		
		return infos;
	}

}
