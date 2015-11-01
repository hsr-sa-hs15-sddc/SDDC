package sddc.main;


import java.io.Console;
import java.io.File;

import org.libvirt.*;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;
import sddc.domain.CRUDService;
import sddc.genericapi.GenericAPILibVirt;
import sddc.persistence.PersistenceFake;
public class Main {

	private static CRUDService service;
	
	public static void main(String[] args) {
		 Console console = System.console();
		 
		 boolean connected = false;
		 IGenericAPIFacade api = new GenericAPILibVirt();
		 
		 //Login
		 do {
			 String uri = console.readLine("Please enter Connection uri : ");
			 try {
					api.connect(uri, false);
					connected = true;
				} catch (LibvirtException e) {
					e.printStackTrace();
				}
		 } while(!connected);
		 
		 IPersistenceFacade persistence = new PersistenceFake();
		 persistence.storeService(new File("testservice.xml").toString());
		 
		 service = new CRUDService(api, persistence);
		 
	    
		 boolean exit = false;
		 
	     while(!exit) {
	    	 String input = console.readLine(">");
	    	 
	    	 if(input.equals("order")) {
	    		 String id = console.readLine("Enter service ID: ");
	    		 try {
					service.orderService(Integer.valueOf(id));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (LibvirtException e) {
					e.printStackTrace();
				}
	    	 }
	    	 
	    	 
	    	 
	     } 
	}
}
