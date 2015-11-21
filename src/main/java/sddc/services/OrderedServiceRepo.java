package sddc.services;

import org.springframework.data.jpa.repository.JpaRepository;

import sddc.services.domain.OrderedService;

public interface OrderedServiceRepo extends JpaRepository<OrderedService, Long> {
	
	OrderedService findByName(String name);
}
