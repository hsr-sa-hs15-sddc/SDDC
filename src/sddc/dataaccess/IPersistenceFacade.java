package sddc.dataaccess;

//Facade muss überarbeitet werden

public interface IPersistenceFacade {
	
	int store(String data);
	String get(int id);
	void delete(int id);
	void update(int id, String data);

}
