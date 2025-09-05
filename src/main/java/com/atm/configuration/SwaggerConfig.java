package com.atm.configuration;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("ATM Simulation API")
                        .description("Simple ATM backend with authentication, balance, withdrawal")
                        .version("v1.0")
                        .contact(new Contact().name("Your Name").email("you@example.com")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("atm-api")
                .pathsToMatch("/api/atm/**")
                .build();
    }
}

