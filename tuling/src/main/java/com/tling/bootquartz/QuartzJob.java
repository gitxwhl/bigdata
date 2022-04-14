package com.tling.bootquartz;

import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
/**
 * 注解是前一个任务执行完了，后一个任务才执行，不会并发执行
 */
@DisallowConcurrentExecution
/**
 * 将jobDataMap持久化
 */
@PersistJobDataAfterExecution
public class QuartzJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Thread.sleep(2000);
            System.out.println(context.getScheduler().getSchedulerInstanceId());
            System.out.println("taskname=" +context.getJobDetail().getKey().getName());
            System.out.println("执行时间=" + new Date());
        } catch (InterruptedException | SchedulerException e) {
            e.printStackTrace();
        }
    }
}
