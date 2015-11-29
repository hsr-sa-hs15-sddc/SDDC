package sddc.genericapi;

import java.util.Map;

import sddc.services.domain.Identifier;
import sddc.services.domain.ServiceModule;

public interface IServiceModuleHandler {
	
	String create(ServiceModule module);
	void delete(Identifier identifier);
	Map<String, String> getInformations(Identifier identifier);
	
	void addResourceController(IResourceController controller);
	
}
