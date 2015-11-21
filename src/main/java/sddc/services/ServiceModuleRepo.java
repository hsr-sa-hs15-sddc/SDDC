package sddc.services;



import org.springframework.data.jpa.repository.JpaRepository;

import sddc.services.domain.ServiceModule;

public interface ServiceModuleRepo extends JpaRepository<ServiceModule, Long> {
	ServiceModule findByName(String name);
}
