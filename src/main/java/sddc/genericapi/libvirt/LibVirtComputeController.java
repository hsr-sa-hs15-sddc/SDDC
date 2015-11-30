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
import sddc.services.genericapi.GenericAPILibVirt;

public class LibVirtComputeController extends LibVirtController {
	
	private static final Logger logger = LoggerFactory.getLogger(LibVirtComputeController.class.getName());

	public LibVirtComputeController(Connect connect) {
		super(Category.Compute, connect);
	}

	@Override
	public Identifier create(ServiceModule module) {
		String config = replaceUUID(module.getConfig());
		
		logger.trace("createCompute(config: " + config + ")");
		
		try {
			Domain domain = connect.domainDefineXML(config);
			domain.create();
			return new Identifier(domain.getUUIDString(), module.getCategory(), module.getSize(), module.getProvider());
		} catch(LibvirtException libvirtException) {
			logger.error("Could not create Compute: " + libvirtException.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Identifier identifier) {
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
		
		logger.trace("getInformations(identifier: " + identifier.getUuid() + ")");
		
		DomainInfo domainInfo;
		Map<String, String> infos = new HashMap<>();
		
		try {
			domainInfo = connect.domainLookupByUUIDString(identifier.getUuid()).getInfo();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not get Informations: " + libvirtException.getMessage());
			return infos;
		}
		
		infos.put("cpuTime", String.valueOf(domainInfo.cpuTime));
		infos.put("maxMem", String.valueOf(domainInfo.maxMem));
		//...
		
		return infos;
	}

}
