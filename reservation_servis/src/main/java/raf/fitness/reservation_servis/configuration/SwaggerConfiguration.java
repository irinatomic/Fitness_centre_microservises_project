package raf.fitness.reservation_servis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        // path to our controller:
        // 1. hardcoded: "raf.fitness.user_servis.controller"
        // 2. package name from some controller: AdminController.class.getPackage().getName()

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select().apis(RequestHandlerSelectors.basePackage("raf.fitness.reservation_servis.controller"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo("API", "API swagger definition", "1.0.0"
                , "Terms of service", new Contact("Katarina Vucicevic & Irina Tomic", "", "kvucicevic5721rn@raf.rs itomic7222rn@raf.rs")
                , "", "", Collections.emptyList());
    }

}
