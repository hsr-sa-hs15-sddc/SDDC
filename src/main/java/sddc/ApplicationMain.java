package sddc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.google.common.base.Predicate;

import sddc.services.OrderedServiceRepo;
import sddc.services.ServiceModuleRepo;
import sddc.services.ServiceRepo;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.*;
import static com.google.common.base.Predicates.*;

@ComponentScan(basePackageClasses = ApplicationMain.class)
@EnableJpaRepositories(basePackageClasses = {ServiceRepo.class,OrderedServiceRepo.class,ServiceModuleRepo.class})
@SpringBootApplication
@EnableSwagger2
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
    
    @Bean
    public Docket serviceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("full-service-api")
                .apiInfo(apiInfo())
                .select()
                .paths(servicePaths())
                .build();
    }

   
    
    @SuppressWarnings("unchecked")
	private Predicate<String> servicePaths() {
        return or(
                regex("/api/services.*"),
                regex("/api/orderedservices.*"),
                regex("/api/servicemodules.*")
        );
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SDDC Service API")
                .description("Service API to order/create/edit/terminate Services/OrderedServices/ServiceModules")
                .build();
    }
}
