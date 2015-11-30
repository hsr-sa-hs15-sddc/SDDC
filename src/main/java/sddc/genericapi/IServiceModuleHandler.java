package sddc.genericapi;

import java.util.Map;

import sddc.services.domain.Identifier;
import sddc.services.domain.ServiceModule;

public interface IServiceModuleHandler {
	
	Identifier create(ServiceModule module);
	void delete(Identifier identifier);
	Map<String, String> getInformations(Identifier identifier);

}
