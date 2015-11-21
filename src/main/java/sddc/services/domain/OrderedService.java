package sddc.services.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class OrderedService {
	@Id @GeneratedValue
	private long id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "orderedservice_id")
	private Set<Identifier> identifiers = new HashSet<Identifier>();
	
	
	public OrderedService () {}
	
	public OrderedService (String name, Set<Identifier> ids) {
		this.name = name;
		this.identifiers = ids;
	}

	public Set<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(Set<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	public String getOrderedServiceName() {
		return name;
	}

	public void setOrderedServiceName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Set<Identifier> getIdentifiers(Category category) {
		Set<Identifier> m = new HashSet<>();
		for(Identifier identifier : identifiers) {
			if(identifier.getCategory() == category)
				m.add(identifier);
		}
		return m;
	}
	
}
