package no.idatt1002.group6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * 
 * Configuration class that sets up the OpenAPI documentation for the BudgetPlan
 * API.
 */
@Configuration

public class OpenApiConfig {

        /**
         * 
         * Sets up the custom OpenAPI object with title, description, and version
         * information.
         * 
         * Also configures the security scheme to use a Bearer token for authentication.
         * 
         * @return the custom OpenAPI object
         */
        @Bean
        public OpenAPI customOpenAPI() {
                OpenAPI openAPI = new OpenAPI();

                Info info = new Info()
                                .title("BudgetPlan API")
                                .description(
                                                "This is the backend for the BudgetPlan application. Remember to add a Bearer token when using endpoints")
                                .version("1.0.0");
                openAPI.setInfo(info);

                Components components = new Components();
                components.addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .description("Please enter a valid Bearer token to authenticate."));
                openAPI.setComponents(components);

                SecurityRequirement securityRequirement = new SecurityRequirement();
                securityRequirement.addList("bearerAuth");
                openAPI.addSecurityItem(securityRequirement);

                return openAPI;
        }
}