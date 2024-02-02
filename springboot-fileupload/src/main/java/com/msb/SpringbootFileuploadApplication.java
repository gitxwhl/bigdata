package com.msb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
@MapperScan(value = "com.msb.mapper")
@SpringBootApplication
public class SpringbootFileuploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFileuploadApplication.class, args);
//ConfigurableApplicationContext context = SpringApplication.run(SpringbootFileuploadApplication.class, args);
       /* String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }*/
    }

}
