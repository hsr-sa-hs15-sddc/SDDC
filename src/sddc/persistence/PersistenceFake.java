package sddc.persistence;

import java.util.HashMap;
import java.util.Map;

import sddc.dataaccess.IPersistenceFacade;

public class PersistenceFake implements IPersistenceFacade {
	
	private Map<Integer, String> services;
	private Map<Integer, String> orderedServices;
	
	public PersistenceFake() {
		services = new HashMap<>();
		orderedServices = new HashMap<>();
	}

	@Override //Refactor
	public int storeService(String data) {
		int hashCode = data.hashCode();
		services.put(hashCode, data);
		return hashCode;
	}

	@Override
	public String[] getServices() {
		return (String[]) services.values().toArray(new String[services.size()]);
	}

	@Override
	public String getService(int id) {
		return services.get(id);
	}

	@Override //Refactor
	public int storeOrderedService(String data) {
		int hashCode = data.hashCode();
		orderedServices.put(hashCode, data);
		return hashCode;
	}

	@Override
	public String[] getOrderedServices() {
		return (String[]) orderedServices.values().toArray(new String[orderedServices.size()]);
	}

	@Override
	public String getOrderedService(int id) {
		return orderedServices.get(id);
	}

	

}
