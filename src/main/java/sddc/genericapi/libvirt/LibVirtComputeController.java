package sddc.genericapi.libvirt;

import java.util.HashMap;
import java.util.Map;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.DomainInfo;
import org.libvirt.LibvirtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sddc.services.genericapi.GenericAPILibVirt;

public class LibVirtComputeController extends LibVirtController {
	
	private static final Logger logger = LoggerFactory.getLogger(LibVirtComputeController.class.getName());

	public LibVirtComputeController(Connect connect) {
		super(connect);
	}

	@Override
	public String createResource(String config) {
		
		config = replaceUUID(config);
		
		logger.trace("createCompute(config: " + config + ")");
		
		try {
			Domain domain = connect.domainDefineXML(config);
			domain.create();
			return domain.getUUIDString();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not create Compute: " + libvirtException.getMessage());
			return "";
		}
	}

	@Override
	public void deleteResource(String identifier) {
		
		logger.trace("deleteCompute(identifier: " + identifier + ")");
		
		try {
			Domain domain = connect.domainLookupByUUIDString(identifier);
			domain.destroy();
			domain.undefine();
		} catch(LibvirtException libvirtException) {
			logger.error("Could not delete Compute: " + libvirtException.getMessage());
		}
		
	}

	@Override
	public Map<String, String> getInformations(String identifier) {
		
		logger.trace("getInformations(identifier: " + identifier + ")");
		
		DomainInfo domainInfo;
		Map<String, String> infos = new HashMap<>();
		
		try {
			domainInfo = connect.domainLookupByUUIDString(identifier).getInfo();
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
