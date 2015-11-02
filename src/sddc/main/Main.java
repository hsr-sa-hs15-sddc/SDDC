package sddc.main;


import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.libvirt.*;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.dataaccess.IPersistenceFacade;
import sddc.domain.CRUDOrderedService;
import sddc.domain.CRUDService;
import sddc.genericapi.GenericAPILibVirt;
import sddc.persistence.PersistenceFake;
public class Main {

	private static CRUDService service;
	private static CRUDOrderedService orderedservice;
	
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public static void main(String[] args) throws IOException {
		 
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		 
		 
		 boolean connected = false;
		 IGenericAPIFacade api = new GenericAPILibVirt();
		 
		 //Login
		 do {
			 System.out.print("Please enter Connection uri : ");
			 String uri = console.readLine();
			 try {
					api.connect(uri, false);
					connected = true;
				} catch (LibvirtException e) {
					e.printStackTrace();
				}
		 } while(!connected);
		 
		 
		 
		 
		 IPersistenceFacade persistence = new PersistenceFake();
		 System.out.println("testservice id = " + persistence.storeService(readFile("testservice.xml", StandardCharsets.UTF_8)));
		 
		 service = new CRUDService(api, persistence);
		 orderedservice = new CRUDOrderedService(api, persistence);
	    
		 boolean exit = false;
		 
	     while(!exit) {
	    	 System.out.print(">");
	    	 String input = console.readLine();
	    	 
	    	 if(input.equals("order")) {
	    		 System.out.print("Enter service ID: ");
	    		 String id = console.readLine();
	    		 try {
					System.out.println("testservice ordered id =" + service.orderService(Integer.parseInt(id)));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (LibvirtException e) {
					e.printStackTrace();
				}
	    	 }
	    	 
	    	 if(input.equals("cancel")) {
	    		 System.out.print("Enter service ID: ");
	    		 String id = console.readLine();
	    		 try {
	    			 orderedservice.cancelOrderedService(Integer.parseInt(id));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (LibvirtException e) {
					e.printStackTrace();
				}
	    	 }
	    	 
	    	 
	    	 
	     } 
	}
}
