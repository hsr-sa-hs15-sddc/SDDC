package sddc.genericapi;

import java.util.Map;

public interface IResourceController {
	
	String createResource(String config);
	void deleteResource(String identifier);
	Map<String, String> getInformations(String identifier);

}
