package com.tling.bootquartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

@Configuration
public class SchedulerConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public Scheduler scheduler() throws IOException{
        return schedulerFactoryBean().getScheduler();
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        /**
         * 设置调度器的名字
         */
        factory.setSchedulerName("cluster_scheduler");
        //注入数据源
        factory.setDataSource(dataSource);
        factory.setApplicationContextSchedulerContextKey("application");
        //读配置文件
        factory.setQuartzProperties(quartzProperties());
        //读线程池
        factory.setTaskExecutor(schedulerThreadPool());
        //设置调度器 立马执行
        factory.setStartupDelay(0);
        return factory;

    }

    /**
     * 读取配置文件
     * @return
     */
    public Properties quartzProperties() throws IOException {
        //读取配置文件
        PropertiesFactoryBean propertiesFactoryBean =new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/spring-quartz.properties"));
        //真正去读配置文件
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 设置线程池
     * Runtime.getRuntime().availableProcessors()   cpu处理器的核心数
     */
    @Bean
    public Executor schedulerThreadPool(){
        //创建线程池
        ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
        //设置线程数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //设置最大链接线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        //设置队列容量
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
        return executor;

    }








}
