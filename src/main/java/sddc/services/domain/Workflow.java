package sddc.services.domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import java.util.HashSet;
import java.util.Set;

import sddc.genericapi.IServiceModuleHandler;
import sddc.services.OrderedServiceRepo;


@Controller
public class Workflow {

	@Autowired
	private OrderedServiceRepo orderedServiceRepo;
	
	private IServiceModuleHandler handler;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Workflow.class);
	
	private static final Category[] workflowOrder = new Category[] {Category.Network, Category.Storage, Category.Compute};
	private static final Category[] workflowCancel = new Category[] {Category.Compute, Category.Storage, Category.Network};
	
	public Workflow() {
		ApplicationContext context = new FileSystemXmlApplicationContext("./Config.xml");
		handler = (IServiceModuleHandler) context.getBean("LibVirtServiceModuleHandler");
		
		((ConfigurableApplicationContext)context).close();
	}
	
	public void orderService(Service service) {
		
		Set<Identifier> identifiers = new HashSet<>();
		
		for(Category category : workflowOrder) {
			for(ServiceModule module : service.getServiceModules(category)) {
				
				Identifier identifier = handler.create(module);
				
				if(identifier == null) {
					rollback(identifiers);
					return;
				}
				identifier.setInfos(handler.getInformations(identifier));
				identifiers.add(identifier);
				
			}
		}
		
		orderedServiceRepo.save(new OrderedService(service.getServiceName(),identifiers));
		
	}
	
	public void cancelService(OrderedService orderedService) {
		
		for(Category category : workflowCancel) {
			for(Identifier identifier : orderedService.getIdentifiers(category)) {
				handler.delete(identifier);
			}
		}
		
		orderedServiceRepo.delete(orderedService);
	}
	
	private void rollback(Set<Identifier> identifiers) {
		OrderedService orderedService = new OrderedService("rollback", identifiers);
		orderedServiceRepo.save(orderedService);
		cancelService(orderedService);
	}

}
