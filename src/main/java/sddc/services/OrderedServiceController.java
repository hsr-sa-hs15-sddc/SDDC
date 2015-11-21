package sddc.services;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.OrderedService;
import sddc.services.domain.Size;
import sddc.services.domain.Workflow;

@RestController
public class OrderedServiceController {
	
	@Autowired
	private OrderedServiceRepo repo;
	
	@Autowired
	private Workflow workflow;
	
	 @PostConstruct
	 private void createInitialData() {
		 repo.deleteAll();
		 Set<Identifier> ids = new HashSet<Identifier>();
		 ids.add(new Identifier(UUID.randomUUID().toString(),Category.Compute,Size.L));
		 ids.add(new Identifier(UUID.randomUUID().toString(),Category.Network,Size.S));
		 repo.save(new OrderedService("LAMP Stack",ids));
	 }
	
    @RequestMapping("/api/orderedservices")
    @ResponseBody
    public List<OrderedService> findAllServices() {
        List<OrderedService> result = repo.findAll();
        return result;
    }
    
    @RequestMapping(value="/api/orderedservices/{id}",method = RequestMethod.GET)
    @ResponseBody
    public OrderedService findService(@PathVariable("id") long id){
        return repo.findOne(id);
    }
    
    @RequestMapping(value = "/api/orderedservices/{id}", method = RequestMethod.DELETE)
    public String cancelService(@PathVariable("id") long id){
     workflow.cancelService(repo.findOne(id));
     return "ok";
    }
}
