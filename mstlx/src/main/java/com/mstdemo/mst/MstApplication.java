package com.mstdemo.mst;

import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.service.DeptInfoService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import sun.tools.jar.CommandLine;

import java.sql.SQLOutput;
import java.util.List;


//@ServletComponentScan(basePackages = "com.mstdemo.mst.config.*")

/**
 * @author dell
 */
@SpringBootApplication
@MapperScan(value = "com.mstdemo.mst.mapper")
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MstApplication implements ApplicationRunner ,CommandLineRunner {
    @Autowired
    private DeptInfoService deptInfoService;

    public static void main(String[] args) {
        SpringApplication.run(MstApplication.class, args);
    }

    /**
     * 实现   ApplicationRunner 之后，在springboot启动之后会自动调用run方法执行里面的业务逻辑
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Permission>  list = deptInfoService.findPermission();
        for( Permission ps:list){
            System.out.println("spirng boot项目启动之后实现ApplicationRunner   执行run方法"+ps.getName());
        }

    }
    /**
     * 实现   CommandLineRunner 之后，在springboot启动之后会自动调用run方法执行里面的业务逻辑
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("spirng boot项目启动之后实现 CommandLineRunner  执行run方法");
    }
}
