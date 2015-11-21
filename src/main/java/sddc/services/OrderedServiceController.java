package sddc.services;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sddc.services.domain.OrderedService;
import sddc.services.domain.Workflow;

@RestController
public class OrderedServiceController {
	
	@Autowired
	private OrderedServiceRepo repo;
	
	@Autowired
	private Workflow workflow;
	
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
