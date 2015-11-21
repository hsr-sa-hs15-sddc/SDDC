package sddc.services;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sddc.services.domain.Category;
import sddc.services.domain.OrderedService;
import sddc.services.domain.Service;
import sddc.services.domain.ServiceModule;
import sddc.services.domain.Size;
import sddc.services.domain.Workflow;
import sddc.services.genericapi.IGenericAPIFacade;

@RestController
public class ServiceController {
    
	private String networkconfig = "<network><name>default6</name><bridge name=\"virbr0\" /><forward mode=\"nat\"/><ip address=\"192.168.122.1\" netmask=\"255.255.255.0\">"
			+ "<dhcp><range start=\"192.168.122.2\" end=\"192.168.122.254\" /></dhcp>"
		    + "</ip><ip family=\"ipv6\" address=\"2001:db8:ca2:2::1\" prefix=\"64\" >"
		    +  "<dhcp><range start=\"2001:db8:ca2:2:1::10\" end=\"2001:db8:ca2:2:1::ff\" /></dhcp></ip></network>";
	
    @Autowired
    private ServiceRepo repo;
    
    @Autowired
    private ServiceModuleRepo moduleRepo;
    
    @Autowired
    private Workflow workflow;
    
    @PostConstruct
    private void createInitialData() {
    	cleanDb();
    	Set<ServiceModule> modules = new HashSet<ServiceModule>();
    	modules.add(new ServiceModule("Network Bridge",Size.S,Category.Network,networkconfig));
        repo.save(new Service("Network Virtual Bridge",modules));
    }
    
    private void cleanDb() {
    	moduleRepo.deleteAll();
        repo.deleteAll();
    }
    
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
    public void updateService(@PathVariable("id") long id, @RequestBody Service service) {
    	Service s = repo.findOne(id);
    	s = service;
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
