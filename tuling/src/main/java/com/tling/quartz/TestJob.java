package com.tling.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public class TestJob {
    public static void main(String[] args) {
        /**
         * name指定时任务的名字和组其中名字不能重复
         */
        JobDetail jobDetail=
        JobBuilder.newJob(MyJob.class)
        .withIdentity("job1","group1")
        .usingJobData("job","jobDetail")
        .usingJobData("name","jobDetail")
        .usingJobData("count",0)
        .build();
        /**
         * withIdentity 设置一组触发器及名字
         * withIntervalInSeconds  执行几次
         * repeatForever   一直执行
         * startNow   设置启动时间马上启动
         * startAt    指定时间启动
         * withSchedule  制定触发策略
         *  1：SimpleScheduleBuilder  按照一定的时间间隔来触发
         *  withIntervalInSeconds   设置  1s执行一次
         *  repeatForever   一直执行
         *  usingJobData:可以通过定时任务的容器获取设置的参数
         */
        int count=0;
        Trigger  trigger= TriggerBuilder.newTrigger()
                .withIdentity("trigger1","trigger1")
                .usingJobData("trigger","trigger")
                .usingJobData("name","trigger")
                .usingJobData("count",count)
                .startNow()
                .withSchedule(SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(1)
                .repeatForever()).build();

        /**
         * 创建调度器
         */
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }
}
