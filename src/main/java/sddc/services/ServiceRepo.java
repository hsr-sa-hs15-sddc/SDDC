package sddc.services;


import org.springframework.data.jpa.repository.JpaRepository;

import sddc.services.domain.Service;

public interface ServiceRepo extends JpaRepository<Service, Long> {
	Service findByServiceName(String serviceName);
}
