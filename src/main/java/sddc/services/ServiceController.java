package sddc.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sddc.services.domain.Service;
import sddc.services.domain.Workflow;


@RestController
public class ServiceController {
	
    @Autowired
    private ServiceRepo repo;
    
    @Autowired
    private Workflow workflow;
    
    @RequestMapping(value="/api/services/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Service findService(@PathVariable("id") long id){
        return repo.findOne(id);
    }
    
    @RequestMapping(value = "/api/services/{id}", method = RequestMethod.POST)
    public @ResponseBody String orderService(@RequestBody Service service){
    	workflow.orderService(service);
    	return "ok";
    }
    
    @RequestMapping(value="/api/services/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteService(@PathVariable("id") long id) {
    	repo.delete(id);
    }
    
    @RequestMapping(value="/api/services/{id}", method = RequestMethod.PUT)
    @Transactional
    public void updateService(@PathVariable("id") long id, @RequestBody Service service) {
    	Service s = repo.findOne(id);
    	s.setServiceName(service.getServiceName());
    	repo.save(s);
    }

    @RequestMapping("/api/services")
    @ResponseBody
    public List<Service> findAllServices() {
        List<Service> result = repo.findAll();
        return result;
    }
    
    @RequestMapping(value = "/api/services", method = RequestMethod.PUT)
    public void createService(@RequestBody Service service) {
    	repo.save(service);
    }
    
  

}
