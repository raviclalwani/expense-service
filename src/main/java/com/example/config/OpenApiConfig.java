package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI expenseServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Service API")
                        .description("API documentation for Expense management microservice")
                        .version("1.0.0"));
    }
}

