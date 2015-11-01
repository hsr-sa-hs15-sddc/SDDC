package sddc.dataaccess;

//Facade muss �berarbeitet werden

public interface IPersistenceFacade {
	
	int storeService(String data);
	String[] getServices();
	String getService(int id);
	
	int storeOrderedService(String[] data);
	String[][] getOrderedServices();
	String[] getOrderedService(int id);
}
