package com.officeServices.bootstrap;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;

@EnableAutoConfiguration
@MapperScan(value = "com.officeServices.mapper")
@EnableTransactionManagement
@ComponentScan({"com.officeServices.controller", "com.officeServices.service"})
public class OfficeServicesApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(OfficeServicesApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OfficeServicesApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(OfficeServicesApplication.class, args);
    }
}
