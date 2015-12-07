package sddc.genericapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.Provider;
import sddc.services.domain.ServiceModule;

public class ServiceModuleHandler implements IServiceModuleHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceModuleHandler.class.getName());
	
	private Map<Category, Map<Provider, ResourceController>> controllers = new HashMap<>();

	public ServiceModuleHandler(List<ResourceController> controllers) {
		
		for(ResourceController controller : controllers) {
			
			if(this.controllers.get(controller.getCategory()) == null) {
				this.controllers.put(controller.getCategory(), new HashMap<>());
			}
			
			this.controllers.get(controller.getCategory()).put(controller.getProvider(), controller);
		}
	}
	
	@Override
	public Identifier create(ServiceModule module) {
		logger.trace("creating " + module.getName());
		return controllers.get(module.getCategory()).get(module.getProvider()).create(module);
	}

	@Override
	public void delete(Identifier identifier) {
		logger.trace("deleting " + identifier.getName());
		controllers.get(identifier.getCategory()).get(identifier.getProvider()).delete(identifier);
	}

	@Override
	public Map<String, String> getInformations(Identifier identifier) {
		logger.trace("getting informations " + identifier.getName());
		return controllers.get(identifier.getCategory()).get(identifier.getProvider()).getInformations(identifier);
	}
}
