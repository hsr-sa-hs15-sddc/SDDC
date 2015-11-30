package sddc.genericapi;

import java.util.Map;

import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.Provider;
import sddc.services.domain.ServiceModule;

public abstract class ResourceController {
	
	private final Category category;
	private final Provider provider;
	
	public ResourceController(Category category, Provider provider) {
		this.category = category;
		this.provider = provider;
	}
	
	public abstract Identifier create(ServiceModule module);
	public abstract void delete(Identifier identifier);
	public abstract Map<String, String> getInformations(Identifier identifier);
	
	public Category getCategory() {
		return category;
	}
	
	public Provider getProvider() {
		return provider;
	}
}
