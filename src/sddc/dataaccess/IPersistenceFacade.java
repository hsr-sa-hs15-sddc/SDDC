package sddc.dataaccess;

//Facade muss überarbeitet werden

public interface IPersistenceFacade {
	
	int storeService(String data);
	String[] getServices();
	String getService(int id);
	
	int storeOrderedService(String data);
	String[] getOrderedServices();
	String getOrderedService(int id);
}
