package com.byebyechallan.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";

    return new OpenAPI()
        .info(new Info()
            .title("ByeByeChallan Backend API")
            .version("1.0.0")
            .description("This documentation covers all the core APIs for the backend service."))
        // 1. Add the global security requirement to all endpoints
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        // 2. Define the security scheme component (JWT Bearer Token)
        .components(new Components()
            .addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))); // Tells Swagger it's a JWT
  }
}