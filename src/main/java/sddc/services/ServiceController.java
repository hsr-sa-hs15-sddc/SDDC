package sddc.services;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import sddc.services.domain.Service;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;
import sddc.services.domain.Workflow;
import sddc.util.FileUtil;
import sddc.services.domain.Category;


@RestController
public class ServiceController {
	
    @Autowired
    private ServiceRepo repo;
    
    @Autowired
    private ServiceModuleRepo moduleRepo;
    
    @Autowired
    private Workflow workflow;
    
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    
    private String networkConfig = FileUtil.getContentOfFile("./LibVirtNetworkConfig.xml",
    		Charset.defaultCharset(), false);
    		
    private String ubuntuConfig = FileUtil.getContentOfFile("./LibVirtComputeConfigUbuntu.xml",
    		Charset.defaultCharset(), false);
    
    private String ubuntuConfigNet = FileUtil.getContentOfFile("./LibVirtComputeConfigDebianNetwork.xml",
    		Charset.defaultCharset(), false);

    private String debianConfig = FileUtil.getContentOfFile("./LibVirtComputeConfigDebian.xml",
    		Charset.defaultCharset(), false);
    
    private String storageConfig = FileUtil.getContentOfFile("./LibVirtStorageConfig.xml",
    		Charset.defaultCharset(), false);
    
    @PostConstruct
    private void createInitialData() {
    	repo.deleteAll();
    	moduleRepo.deleteAll();
    	Set<ServiceModule> modules = new HashSet<ServiceModule>();
    	ServiceModule network = new ServiceModule("Network Bridge",Category.Network,networkConfig);
    	ServiceModule compute = new ServiceModule("Debian Squeez",Size.M, Category.Compute,ubuntuConfigNet);
    	modules.add(network);
    	modules.add(compute);
        Service service = new Service("Virtual Bridge + Debian",modules);
        repo.save(service);
        Set<ServiceModule> modules2 = new HashSet<ServiceModule>();
        ServiceModule compute2 = new ServiceModule("Ubuntu VM",Size.M,Category.Compute,ubuntuConfig);
        modules2.add(compute2);
        Service service2 = new Service("Ubuntu 14.04",modules2);
        repo.save(service2);
        Set<ServiceModule> modules3 = new HashSet<ServiceModule>();
        ServiceModule compute3 = new ServiceModule("Debian VM",Size.M,Category.Compute,debianConfig);
        modules3.add(compute3);
        Service service3 = new Service("Debian Squeeze",modules3);
        repo.save(service3);
        Set<ServiceModule> modules4 = new HashSet<ServiceModule>();
        ServiceModule storage = new ServiceModule("Dir Storage Pool",Size.M,Category.Storage,storageConfig);
        modules4.add(storage);
        Service service4 = new Service("Storage Pool",modules4);
        repo.save(service4);
    }
    
    @RequestMapping(value="/api/services/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Service findService(@PathVariable("id") long id){
    	logger.info("Get Service:" + repo.findOne(id).getServiceName());
        return repo.findOne(id);
    }
    
    @RequestMapping(value = "/api/services/new", method = RequestMethod.POST)
    public Service createService(@RequestBody Service service) {
    	logger.info("Create Service:" + service.getServiceName());
    	return repo.save(service);
    }

    @RequestMapping(value = "/api/services/{id}", method = RequestMethod.POST)
    public @ResponseBody String orderService(@RequestBody Service service){
    	workflow.orderService(service);
    	logger.info("Order Service:" + service.getServiceName());
    	return "ok";
    }
    
    @RequestMapping(value="/api/services/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteService(@PathVariable("id") long id) {
    	logger.info("Delete Service:" + repo.findOne(id).getServiceName());
    	repo.delete(id);
    }
    
    @RequestMapping(value="/api/services/{id}", method = RequestMethod.PUT)
    @Transactional
    public void updateService(@PathVariable("id") long id, @RequestBody Service service) {
    	logger.info("Update Service:" + repo.findOne(id).getServiceName());
    	Service s = repo.findOne(id);
    	s.setServiceName(service.getServiceName());
    	s.setModules(service.getModules());
    	repo.save(s);
    }

    @RequestMapping("/api/services")
    @ResponseBody
    public List<Service> findAllServices() {
    	logger.info("Get all Services");
        List<Service> result = repo.findAll();
        return result;
    }
    
    
   
  

}
