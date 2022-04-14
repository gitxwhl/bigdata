package com.tling.bootquartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * spring boot 启动之后监听器执行定时任务
 */
@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    //调度器
    @Autowired
   private Scheduler scheduler;

    /**
     * 0/10 * * * * ?
     * 每阁10s执行一次
     * 一个节点
     * @param event
     */
   /* @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        TriggerKey triggerKey  = TriggerKey.triggerKey("trigger","group1");
        try {
           //在调度器里面通过triggerKey去取触发器；触发器没有的时候需要新建有的时候不需要新建；确保触发器在调度器里面是唯一的，触发器只需要一个就可以了不需要多余的触发器
            Trigger trigger  =  scheduler.getTrigger(triggerKey);
            //判断触发器是否为空
            if(trigger ==null){
                //调度
               trigger = TriggerBuilder.newTrigger()
                       .withIdentity(triggerKey)
                       .withSchedule(CronScheduleBuilder
                       .cronSchedule("0/10 * * * * ?"))
                       .startNow()
                       .build();
             JobDetail jobDetail  = JobBuilder.newJob(QuartzJob.class).withIdentity("job1","grop1")
                       .build();
             //执行调度
                scheduler.scheduleJob(jobDetail,trigger);
                scheduler.start();

            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
*/


    /**
     * 0/10 * * * * ?
     * 每阁10s执行一次
     * 两个个节点
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        try {
            TriggerKey triggerKey1  = TriggerKey.triggerKey("trigger1","group1");
            //在调度器里面通过triggerKey去取触发器；触发器没有的时候需要新建有的时候不需要新建；确保触发器在调度器里面是唯一的，触发器只需要一个就可以了不需要多余的触发器
            Trigger trigger1  =  scheduler.getTrigger(triggerKey1);
            //判断触发器是否为空
            if(trigger1 ==null){
                //调度
                trigger1 = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey1)
                        .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/10 * * * * ?"))
                        .startNow()
                        .build();
                JobDetail jobDetail1  = JobBuilder.newJob(QuartzJob.class).withIdentity("job1","grop1")
                        .build();
                //执行调度
                scheduler.scheduleJob(jobDetail1,trigger1);
//                scheduler.start();

            }



            //在调度器里面通过triggerKey去取触发器；触发器没有的时候需要新建有的时候不需要新建；确保触发器在调度器里面是唯一的，触发器只需要一个就可以了不需要多余的触发器
            TriggerKey triggerKey2  = TriggerKey.triggerKey("trigger2","group2");
            Trigger trigger2  =  scheduler.getTrigger(triggerKey2);
            //判断触发器是否为空
            if(trigger2 ==null){
                //调度
                trigger2 = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey2)
                        .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/10 * * * * ?"))
                        .startNow()
                        .build();
                JobDetail jobDetail2  = JobBuilder.newJob(QuartzJob.class).withIdentity("job2","grop2")
                        .build();
                //执行调度
                scheduler.scheduleJob(jobDetail2,trigger2);
            }
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
