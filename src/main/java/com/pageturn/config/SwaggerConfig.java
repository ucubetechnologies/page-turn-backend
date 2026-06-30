package com.pageturn.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pageTurnOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Page Turn API")
                        .description("Backend REST API for the Page Turn book platform — books, reviews, Q&A, users, and admin management.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Page Turn Team")
                                .email("admin@pageturn.com")));
    }
}
