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
	
	/**
	 * Creates the resource described in the ServiceModule
	 * and returns an identifier
	 * @param module A ServiceModule describing a Resource/Module
	 * @return An Identifier that stores information about the created Resource
	 */
	public abstract Identifier create(ServiceModule module);
	
	/**
	 * Deletes an existing resource
	 * @param identifier 
	 */
	public abstract void delete(Identifier identifier);
	
	/**
	 * Gets information about an existing Resource/Module
	 * @param identifier
	 * @return 
	 */
	public abstract Map<String, String> getInformations(Identifier identifier);
	
	public Category getCategory() {
		return category;
	}
	
	public Provider getProvider() {
		return provider;
	}
}
