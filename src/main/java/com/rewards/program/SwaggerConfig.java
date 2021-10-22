package com.rewards.program;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Alexis Ganga.
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Constructor por defecto.
     */
    public SwaggerConfig() {
        super();
    }

    /**
     * Bean que se generar para documetar la api.
     *
     * @return documentador.
     */
    @Bean
    public Docket productApi() {
        // Tipo de documentacion
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            // Paquete donde se encuentra los controladores.
            .apis(RequestHandlerSelectors.basePackage(
                "com.rewards.program.controller"))
            // No se utiliza los Response por defecto.
            .build().useDefaultResponseMessages(false)
            // Tags
            .tags(new Tag("RewardsController", "Main controller of the application"));
    }
}
