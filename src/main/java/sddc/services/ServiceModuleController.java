package sddc.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

import sddc.services.domain.Category;
import sddc.services.domain.Provider;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;

@RestController
public class ServiceModuleController {
	  	@Autowired
	   	private ServiceModuleRepo repo;
	  	
	  	private static final Logger logger = LoggerFactory.getLogger(ServiceModuleController.class);
	  
	    @PostConstruct
	    private void createInitialData() {
	    	repo.save(new ServiceModule("Deletable ServiceModule",Size.S, Provider.LibVirt,Category.Storage,"Config"));
	    }
	  	
	    @ApiOperation(value = "Returns all ServiceModules", 
	    		notes = "Returns a list of all ServiceModules", 
	    		response = ServiceModule[].class)
	  	@RequestMapping(value="/api/servicemodules",method = RequestMethod.GET)
	    @ResponseBody
	    public List<ServiceModule> getServiceModules() {
	  		logger.info("Get all ServiceModules");
	        List<ServiceModule> result = repo.findAll();
	        return result;
	     }	

		@ApiOperation(value = "Returns a ServiceModule by it's id", 
	    		notes = "Returns a ServiceModule by it's id", 
	    		response = ServiceModule.class)
	  	 @RequestMapping(value="/api/servicemodules/{id}",method = RequestMethod.GET)
	     @ResponseBody
	     public ServiceModule getServiceModule(@PathVariable("id") long id){
	  		 logger.info("Get ServiceModule:" + repo.findOne(id).getName());
	         return repo.findOne(id);
	     }
	  	 
	    @ApiOperation(value = "Delets a existing ServiceModule", 
	    		notes = "Delets a existing ServiceModule")
	  	 @RequestMapping(value="/api/servicemodules/{id}",method = RequestMethod.DELETE)
	     @ResponseBody
	     public void deleteServiceModule(@PathVariable("id") long id){
	  		 logger.info("Delete ServiceModule:" + repo.findOne(id).getName());
	         repo.delete(id);
	     }
	  	 
	    @ApiOperation(value = "Creates a ServiceModule", 
	    		notes = "Creates a ServiceModule")
	  	@RequestMapping(value = "/api/servicemodules/new", method = RequestMethod.POST)
	    public ServiceModule createServiceModule(@RequestBody ServiceModule module) {
	  		logger.info("Create ServiceModule:" + module.getName());
	    	return repo.save(module);
	    }
	  	
	    @ApiOperation(value = "Updates a existing ServiceModule", 
	    		notes = "Updates a existing ServiceModule")
	  	 @RequestMapping(value="/api/servicemodules/{id}", method = RequestMethod.PUT)
	     @Transactional
	     public void updateServiceModule(@PathVariable("id") long id, @RequestBody ServiceModule module) {
	  		logger.info("Update ServiceModule:" + module.getName());
	     	ServiceModule m = repo.findOne(id);
	     	m.setConfig(module.getConfig());
	     	m.setName(module.getName());
	     	m.setSize(module.getSize());
	     	m.setServices(module.getServices());
	     	m.setCategory(module.getCategory());
	     	repo.save(m);
	     }
	    
	    @ApiOperation(value = "Returns all Providers", 
	    		notes = "Returns a list of all Providers", 
	    		response = Provider[].class)
	  	@RequestMapping(value="/api/servicemodules/providers",method = RequestMethod.GET)
	    @ResponseBody
	    public Provider[] getServiceModulesProviders() {
	  		logger.info("Get all Providers");
	        Provider[] result = Provider.values();
	        return result;
	    }
	  	
	    @ApiOperation(value = "Returns all Categories", 
	    		notes = "Returns a list of all Categories", 
	    		response = Category[].class)
		@RequestMapping(value="/api/servicemodules/categories",method = RequestMethod.GET)
	    @ResponseBody
	    public Category[] getServiceModulesCategories() {
	  		logger.info("Get all Categories");
	        Category[] result = Category.values();
	        return result;
	    }
		
	    @ApiOperation(value = "Returns all Sizes", 
	    		notes = "Returns a list of all Sizes", 
	    		response = Size[].class)
		@RequestMapping(value="/api/servicemodules/sizes",method = RequestMethod.GET)
	    @ResponseBody
	    public Size[] getServiceModulesSizes() {
	  		logger.info("Get all Categories");
	  		Size[] result = Size.values();
	        return result;
	    }
	  
}
