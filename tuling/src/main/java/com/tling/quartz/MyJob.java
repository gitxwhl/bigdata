package com.tling.quartz;

import org.quartz.*;

import java.util.Date;

/**
 * 注解是前一个任务执行完了，后一个任务才执行，不会并发执行
 */
@DisallowConcurrentExecution
/**
 * 将jobDataMap持久化
 */
@PersistJobDataAfterExecution
public class MyJob implements Job {

    private String name;

    public void setName(String name){
        this.name=name;
    }

    /**
     * 可以看成job的一个大的容器，所有的定时任务都是放在这个容器里面去运行的
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        /*JobDataMap jobDataMap  = context.getJobDetail().getJobDataMap();
        String jobName = jobDataMap.getString("job");
        JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();
        String  triggerName = triggerDataMap.getString("trigger");
        JobDataMap margeraMap = context.getMergedJobDataMap();
        String  margeName = margeraMap.getString("job");

        System.out.println("jobDataMap=======" + jobName);
        System.out.println("triggerDataMap=======" + triggerName);
        System.out.println("margeraMap==========" + margeName);
        System.out.println("name==========" + name);*/
        /**
         *Scheduler 每次执行，都会根据jobDetail 创建一个新的job实例，这样就可以规避并发访问问题（jobDetail的实例也是新的）
         *
         */
        /*System.out.println("jobDetail:"+System.identityHashCode(context.getJobDetail()));
        System.out.println("job:"+System.identityHashCode(context.getJobInstance()));*/

        /**
         * DisallowConcurrentExecution :
         * 注解是前一个任务执行完了，后一个任务才执行，不会并发执行
         */
       /* System.out.println("execute"+new Date());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /**
         *   DisallowConcurrentExecution
         *   将JobDataMap进行持久化
         *   统计定时任务执行了多少次对trigger中的datamap无效
         */
        JobDataMap triggerMap = context.getTrigger().getJobDataMap();
        triggerMap.put("count",triggerMap.getInt("count")+1);
        JobDataMap jobDataMap  = context.getJobDetail().getJobDataMap();
        jobDataMap.put("count",jobDataMap.getInt("count")+1);
        System.out.println("triggerMap count:" + triggerMap.getInt("count"));
        System.out.println("jobDataMap count:" + jobDataMap.getInt("count"));


//        System.out.println("MyJob execute" + new Date() + "张盼盼");
    }
}
