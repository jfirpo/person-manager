package hu.furedikrisztian.personmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Person Manager API")
                        .version("1.0")
                        .description("Felvételi backend feladat – személyek, címek és kapcsolatok kezelése")
                        .contact(new Contact()
                                .name("Füredi Krisztián")
                                .email("furedi.k@gmail.com"))
                        .license(new License()
                                .name("MIT License")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
