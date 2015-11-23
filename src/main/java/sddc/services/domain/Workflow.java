package sddc.services.domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import java.util.HashSet;
import java.util.Set;

import org.libvirt.LibvirtException;


import sddc.services.OrderedServiceRepo;
import sddc.services.genericapi.IGenericAPIFacade;
import sddc.services.genericapi.factory.GenericAPILibVirtFactory;

@Controller
public class Workflow {

	@Autowired
	private OrderedServiceRepo orderedServiceRepo;
	
	private IGenericAPIFacade api;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Workflow.class);

	public Workflow() {
		api = GenericAPILibVirtFactory.getInstance();
	}
	
	public void orderService(Service service) {
		
		Set<Identifier> ids = new HashSet<Identifier>();
		for(ServiceModule serviceModule : service.getServiceModules(Category.Network)) {
			try {
				String identifier = api.createNetwork(serviceModule.getConfig());
				ids.add(new Identifier(identifier, serviceModule.getCategory(), serviceModule.getSize()));
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Network: " + e.getMessage());
				rollback(ids);
				return;
			}
		}
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Storage)) {
			try {
				String identifier = api.createStorage(serviceModule.getConfig());
				ids.add(new Identifier(identifier, serviceModule.getCategory(), serviceModule.getSize()));
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Storage: " + e.getMessage());
				rollback(ids);
				return;
			}
		}
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Compute)) {
			try {
				String identifier = api.createCompute(serviceModule.getConfig());
				ids.add(new Identifier(identifier, serviceModule.getCategory(), serviceModule.getSize()));
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Compute: " + e.getMessage());
				rollback(ids);
				return;
			}
		}
		
		orderedServiceRepo.save(new OrderedService(service.getServiceName(),ids));
	}
	
	public void cancelService(OrderedService orderedService) {
		for(Identifier identifier : orderedService.getIdentifiers(Category.Compute)) {
			try {
				api.deleteCompute(identifier.getUuid());
			} catch (LibvirtException e) {
				LOGGER.error("Could not delete Compute: " + e.getMessage());
			}
		}
		
		for(Identifier identifier : orderedService.getIdentifiers(Category.Storage)) {
			try {
				api.deleteStorage(identifier.getUuid());
			} catch (LibvirtException e) {
				LOGGER.error("Could not delete Storage: " + e.getMessage());
			}
		}
		
		for(Identifier identifier : orderedService.getIdentifiers(Category.Network)) {
			try {
				api.deleteNetwork(identifier.getUuid());
			} catch (LibvirtException e) {
				LOGGER.error("Could not delete Network: " + e.getMessage());
			}
		}
		
		orderedServiceRepo.delete(orderedService);
	}
	
	//Nur Testweise
	private void rollback(Set<Identifier> identifiers) {
		cancelService(new OrderedService("rollback", identifiers));
	}

}
