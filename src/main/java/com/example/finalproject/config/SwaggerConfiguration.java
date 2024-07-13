package com.example.finalproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@OpenAPIDefinition( info = @Info(title = "Home-and-Garden-Shop REST API", description = "This is the REST API for final project of the student of the group 171023", version = "1.0.0", contact = @Contact( name = "Project on GitHub", url = "https://github.com/YV-FinalProject/Final-Project")))
@SecurityScheme(name = "JWT", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfiguration {

}
