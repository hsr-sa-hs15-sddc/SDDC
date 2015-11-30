package sddc.services.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class ServiceModule {
	
	@Id @GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
    private Category category;
	
	@Enumerated(EnumType.STRING)
    private Size size;
	
	@Enumerated(EnumType.STRING)
	private Provider provider;
	
	@Column(length=10000)
	private String config;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "modules")
	private Set<Service> services = new HashSet<Service>(0);
	
	@Column(unique=true)
	private String name;
	
	public ServiceModule() {}
	
	public ServiceModule(String name, Size size, Provider provider, Category category, String config) {
		this.setName(name);
		this.setSize(size);
		this.setProvider(provider);
		this.setCategory(category);
		this.setConfig(config);
	}
	
	public ServiceModule(String name, Category category, String config) {
		this.setName(name);
		this.setCategory(category);
		this.setConfig(config);
	}
	
	
	public String getName() {
		return name;
	}
	
	public Size getSize() {
		return size;
	}
	
	public Provider getProvider() {
		return provider;
	}
	
	public Category getCategory() {
		return category;
	}
	
	@JsonIgnore
	public Set<Service> getServices() {
		return this.services;
	}
	
	public void setServices(Set<Service> service) {
		this.services = service;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
