package sddc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import sddc.services.OrderedServiceRepo;
import sddc.services.ServiceModuleRepo;
import sddc.services.ServiceRepo;
@ComponentScan(basePackageClasses = ApplicationMain.class)
@EnableJpaRepositories(basePackageClasses = {ServiceRepo.class,OrderedServiceRepo.class,ServiceModuleRepo.class})
@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}
