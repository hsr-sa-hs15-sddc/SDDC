package sddc.genericapi;

import java.util.List;
import java.util.Map;

import sddc.services.domain.Identifier;
import sddc.services.domain.ServiceModule;

public class ServiceModuleHandler implements IServiceModuleHandler {
	
	private List<IResourceController> controllers;

	
	public ServiceModuleHandler(List<IResourceController> controllers) {
		this.controllers = controllers;
	}
	
	@Override
	public String create(ServiceModule module) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Identifier identifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getInformations(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addResourceController(IResourceController controller) {
		// TODO Auto-generated method stub
		
	}

}
