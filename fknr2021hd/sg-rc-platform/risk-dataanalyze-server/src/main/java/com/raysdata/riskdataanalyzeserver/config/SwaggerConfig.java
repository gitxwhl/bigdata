package com.raysdata.riskdataanalyzeserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment env;

    public SwaggerConfig() {
    }

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.getApiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.raysdata.riskdataanalyzeserver")).paths(PathSelectors.any()).build();
    }

    private ApiInfo getApiInfo() {
        return (new ApiInfoBuilder()).title(this.env.getProperty("rmcp.swagger.title")).description(this.env.getProperty("rmcp.swagger.description")).version(this.env.getProperty("rmcp.version")).build();
    }
}
