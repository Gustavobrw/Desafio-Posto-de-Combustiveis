package com.gustavo.posto_combustivel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi(){
        Info info = new Info();
        info.title("API Posto de Combustivel");
        info.version("1.0");
        info.description("API para gerenciamento de combustíveis, bombas e abastecimentos.");
        return new OpenAPI().info(info);
    }
}
