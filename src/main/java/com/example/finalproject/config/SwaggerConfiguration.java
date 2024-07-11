package com.example.finalproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;


@OpenAPIDefinition( info = @Info(title = "Home-and-Garden REST API", description = "Describes ...", version = "1.0.0", contact = @Contact( name = "171023 Group", url = "https://github.com/YV-FinalProject/Final-Project")))
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI () {
        return new OpenAPI ().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")).components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));


    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
    }


}
