package mate.academy.springboot.swagger.config;

import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@PropertySource("classpath:application.properties")
public class SwaggerConfig {
    @Value("${api.content.type:application/json}")
    private String apiContentType;
    private final Environment environment;

    public SwaggerConfig(Environment environment) {
        this.environment = environment;
    }

    private Contact getContact() {
        return new Contact(environment.getProperty("contact.name"),
                environment.getProperty("contact.url"),
                environment.getProperty("contact.email"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                environment.getProperty("api.title"),
                environment.getProperty("api.description"),
                environment.getProperty("api.version"),
                "", getContact(),
                environment.getProperty("api.license"),
                environment.getProperty("api.license.url"),
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("mate.academy.springboot.swagger.controller"))
                .build()
                .apiInfo(apiInfo())
                .produces(Set.of(apiContentType))
                .consumes(Set.of(apiContentType));
    }
}
