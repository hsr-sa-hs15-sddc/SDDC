package sddc.persistence;

import java.util.HashMap;
import java.util.Map;

import sddc.dataaccess.IPersistenceFacade;

public class PersistenceFake implements IPersistenceFacade {
	
	private Map<Integer, String> map;
	
	public PersistenceFake() {
		map = new HashMap<>();
	}

	@Override
	public int store(String data) {
		int hash = data.hashCode();
		map.put(hash, data);
		return hash;
	}

	@Override
	public String get(int id) {
		return map.get(id);
	}

	@Override
	public void delete(int id) {
		map.remove(id);
	}

	@Override
	public void update(int id, String data) {
		map.put(id, data);
	}

}
