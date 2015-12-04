package sddc.genericapi.libvirt;

import java.util.HashMap;
import java.util.Map;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.DomainInfo;
import org.libvirt.LibvirtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.ServiceModule;


public class LibVirtComputeController extends LibVirtController {
	
	private static final Logger logger = LoggerFactory.getLogger(LibVirtComputeController.class.getName());

	public LibVirtComputeController(Connect connect) {
		super(Category.Compute, connect);
	}

	@Override
	public Identifier create(ServiceModule module) {
		
		if(module == null)
			return null;
		
		String config = replaceUUID(module.getConfig());
		
		logger.trace("createCompute(config: " + config + ")");
		
		try {
			Domain domain = connect.domainDefineXML(config);
			domain.create();
			return new Identifier(module.getName(),domain.getUUIDString(), module.getCategory(), module.getSize(), module.getProvider());
		} catch(LibvirtException libvirtException) {
			logger.error("Could not create Compute: " + libvirtException.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Identifier identifier) {
		
		if(identifier == null)
			return;
		
		logger.trace("deleteCompute(identifier: " + identifier.getUuid() + ")");
		
		try {
			Domain domain = connect.domainLookupByUUIDString(identifier.getUuid());
			domain.destroy();
			domain.undefine();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not delete Compute: " + libvirtException.getMessage());
		}
	}
	
	@Override
	public Map<String, String> getInformations(Identifier identifier) {
		
		Map<String, String> infos = new HashMap<>();
		
		if(identifier == null)
			return infos;
		
		logger.trace("getInformations(identifier: " + identifier.getUuid() + ")");
		
		DomainInfo domainInfo;
		String domainName;
		
		try {
			domainInfo = connect.domainLookupByUUIDString(identifier.getUuid()).getInfo();
			domainName = connect.domainLookupByUUIDString(identifier.getUuid()).getName();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Informations: " + libvirtException.getMessage());
			return infos;
		}
		infos.put("name", domainName);
		infos.put("memory", String.valueOf(domainInfo.memory));
		infos.put("vcpu", String.valueOf(domainInfo.nrVirtCpu));
		//...
		
		return infos;
	}

}
