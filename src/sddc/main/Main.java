package sddc.main;


import java.io.Console;
import java.io.File;

import org.libvirt.*;

import sddc.dataaccess.IGenericAPIFacade;
import sddc.genericapi.GenericAPILibVirt;
public class Main {
	
private static String connection,filename;
private static IGenericAPIFacade api;

	public static void main(String[] args) {
		 Console console = System.console();
	     connection = console.readLine("Please enter Connection String : ");
	     api = new GenericAPILibVirt();
	     try {
			api.connect(connection, false);
		} catch (LibvirtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     filename = console.readLine("Please enter Name of File : ");
	     File file = new File(filename);
	     //createMethod(file.toString(););
	}
	
}
