package com.titus.socialnetworkingsite2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Social Networking Site",
                        email = "iakwasititus@gmail.com",
                        url = "iakwasititus@gmail.com"

                ) ,
                description = "Social Networking site app",
                title = "API DOCUMENTATION",
                version = "0.0.1",
                license = @License(
                        name = "BSD License",
                        url = "link to the license"
                ),
                termsOfService = "link to the terms of service page"
        ),

        servers = {
                @Server(
                        description = "server",
                        url = "http://localhost:5000"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerauth"
                )
        }
)
@SecurityScheme(
        name= "bearerauth",
        description = "bearerauthentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP, bearerFormat = "jwt", in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}