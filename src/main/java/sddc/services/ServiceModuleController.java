package sddc.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sddc.services.domain.Category;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;

@RestController
public class ServiceModuleController {
	  	@Autowired
	   	private ServiceModuleRepo repo;
	  
	    @PostConstruct
	    private void createInitialData() {
	    	repo.save(new ServiceModule("Deletable ServiceModule",Size.S, Category.Storage,"Config"));
	    }
	  	
	  	
	  	@RequestMapping("/api/servicemodules")
	    @ResponseBody
	    public List<ServiceModule> findAllServiceModules() {
	        List<ServiceModule> result = repo.findAll();
	        return result;
	    }

	  	 @RequestMapping(value="/api/servicemodules/{id}",method = RequestMethod.GET)
	     @ResponseBody
	     public ServiceModule findServiceModule(@PathVariable("id") long id){
	         return repo.findOne(id);
	     }
	  	 
	  	 @RequestMapping(value="/api/servicemodules/{id}",method = RequestMethod.DELETE)
	     @ResponseBody
	     public String deleteServiceModule(@PathVariable("id") long id){
	  		 System.out.println("Delete");
	          repo.delete(id);
	          return "ok";
	     }
	  	 
	  	@RequestMapping(value = "/api/servicemodules/new", method = RequestMethod.POST)
	    public ServiceModule createServiceModule(@RequestBody ServiceModule module) {
	    	return repo.save(module);
	    }
	  	
	  	 @RequestMapping(value="/api/servicemodules/{id}", method = RequestMethod.PUT)
	     @Transactional
	     public void updateServiceModule(@PathVariable("id") long id, @RequestBody ServiceModule module) {
	     	ServiceModule m = repo.findOne(id);
	     	m.setConfig(module.getConfig());
	     	m.setName(module.getName());
	     	m.setSize(module.getSize());
	     	m.setServices(module.getServices());
	     	m.setCategory(module.getCategory());
	     	repo.save(m);
	     }	 
	  
}
