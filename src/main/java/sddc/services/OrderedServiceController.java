package sddc.services;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(OrderedServiceController.class);
	
    @RequestMapping("/api/orderedservices")
    @ResponseBody
    public List<OrderedService> getOrderedServices() {
        List<OrderedService> result = repo.findAll();
        logger.info("Get all OrderedServices");
        return result;
    }
    
    @RequestMapping(value="/api/orderedservices/{id}",method = RequestMethod.GET)
    @ResponseBody
    public OrderedService getOrderedService(@PathVariable("id") long id){
    	logger.info("Get OrderedService:" + repo.findOne(id).getOrderedServiceName());
        return repo.findOne(id);
    }
    
    @RequestMapping(value = "/api/orderedservices/{id}", method = RequestMethod.DELETE)
    public void cancelOrderedService(@PathVariable("id") long id){
    logger.info("Cancel OrderedService:" + repo.findOne(id).getOrderedServiceName());
     workflow.cancelService(repo.findOne(id));
    }
}
