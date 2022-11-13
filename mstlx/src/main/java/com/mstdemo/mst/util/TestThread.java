package com.mstdemo.mst.util;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public  class TestThread {


    /**
     * 多线程实现方法一：
     */
    static class MyThread extends Thread{

            @Override
            public void run(){
                System.out.println("多线程实现方法一： Hello Thread");
            }
    }

    /**
     * 实现runable接口
     */
    static  class myRunable implements Runnable{
        @Override
        public void run(){
            System.out.println("多线程实现方法二： Hello Runable");
        }

    }

    /**
     * 实现方式三:实现Callable 接口
     * @param
     */
    static class Mycall implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("hello Mycall");
            return "Sucess";
        }
    }



    @SneakyThrows
    public static void main(String[] args) {
        //方法一：启动方式一：继承Thread类
        new  MyThread().start();
        //启动方式二：实现runable接口
        Thread thread = new Thread(new myRunable(){});
        thread.start();
        //启动方式三：lamdad表达式
        Runnable ladam = ()-> System.out.println("多线程实现方法三： Hello lamda表达式");
        ladam.run();
        //三实现callable接口启动方式一：
        FutureTask<String> task= new FutureTask<>(new Mycall());
        Thread tc=new Thread(task);
        tc.start();
        System.out.println("启动方式一：callable接口" + task.get());
        //实现方式4：线程池
        ExecutorService   service = Executors.newCachedThreadPool();
        //用线程池运行callable接口  带返回值的
        Future<String> f = service.submit(new Mycall());
        String s = null;
        try {
            s = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println( "启动方式二线程池：callable接口"+ s);

        service.execute(
                ()-> {
            System.out.println("hello ThreadPool");
                });
        service.shutdown();

    }







}
