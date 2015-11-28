package sddc.services.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import javax.persistence.JoinColumn;

@Entity
@Table(name = "service")  
public class Service {
		
	@Id @GeneratedValue
	private long id;
	
	@Column(name="name",unique=true)
	private String serviceName;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "service_modules", joinColumns = { 
			@JoinColumn(name = "service_id", nullable = true, updatable = true) }, 
			inverseJoinColumns = { @JoinColumn(name = "servicemodule_id", 
					nullable = true, updatable = true) })
	private Set<ServiceModule> modules = new HashSet<ServiceModule>(0);
	
	public Service () {}
	
	public Service(String serviceName, Set<ServiceModule> modules) {
	    this.serviceName = serviceName;
	    this.modules = modules;
	}
	
	public Service(String serviceName) {
	    this.serviceName = serviceName;
	}

	public long getId() {
		return id;
	}

	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String name) {
		this.serviceName = name;;
	}
	
	public Set<ServiceModule> getServiceModules() {
		return this.modules;
	}
	
	public Set<ServiceModule> getServiceModules(Category category) {
		Set<ServiceModule> m = new HashSet<>();
		for(ServiceModule serviceModule : modules) {
			if(serviceModule.getCategory() == category)
				m.add(serviceModule);
		}
		return m;
	}
	
	public void setServiceModules(Set<ServiceModule> modules) {
		this.modules = modules;
	}
}
