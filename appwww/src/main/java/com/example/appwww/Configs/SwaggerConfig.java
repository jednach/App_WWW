package com.example.appwww.Configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    private static final String[] WHITELIST = {
            "/api/**",
            "/auth/**",
    };

    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("public-api").pathsToMatch(WHITELIST).build();
    }

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                        new Info().title("APP")
                                .version("1.0.0").description("APP"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT")));
    }
}
