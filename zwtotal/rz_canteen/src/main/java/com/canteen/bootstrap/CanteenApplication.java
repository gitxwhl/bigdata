package com.canteen.bootstrap;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MapperScan(value = "com.canteen.mapper")
@EnableTransactionManagement
@ComponentScan({"com.canteen.controller", "com.canteen.service","com.canteen.bootstrap.config"})
public class CanteenApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(CanteenApplication.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CanteenApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(CanteenApplication.class,args);
    }
}
