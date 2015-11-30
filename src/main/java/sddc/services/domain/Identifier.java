package sddc.services.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Identifier {
	@Id @GeneratedValue
	private long id;
	
	private String uuid;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@Enumerated(EnumType.STRING)
	private Size size;
	
	@Enumerated(EnumType.STRING)
	private Provider provider;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="orderedservice_id")
	private OrderedService orderedService;
	
	public Identifier() {}
	
	public Identifier(String uuid, Category category, Size size, Provider provider) {
		this.uuid = uuid;
		this.category = category;
		this.setSize(size);
		this.setProvider(provider);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@JsonIgnore
	public OrderedService getOrderedService() {
		return orderedService;
	}

	public void setOrderedService(OrderedService orderedService) {
		this.orderedService = orderedService;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	public Provider getProvider() {
		return provider;
	}
	
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	
}
