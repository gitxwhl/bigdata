//package com.msb.utils;
//
//import io.minio.MinioClient;
//import org.apache.commons.io.IOUtils;
//
//import java.io.InputStream;
//import java.util.List;
//
//public class minioDemo {
//    private static String endpoint = "http://192.168.56.10:9000";
//    //用户名
//    private static String accessKey = "zpp";
//    //密码
//    private static String secretKey = "12345678";
//    private static String bucketName="test";
//
//    public static void main(String[] args) throws Exception{
//
//        // 1.使用MinIo服务的URL，端口 账号和密码 创建一个 MinIoClient对象
//        MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
//        //判断桶是否存在，如果不存在就创建
//        boolean bucketExists = minioClient.bucketExists(bucketName);
//        if (bucketExists){
//            System.out.println("对应的桶存在");
//        }else {
//            minioClient.makeBucket(bucketName);
//        }
//        String objectName="/group1/pan.jpg";
//        //        文件上传
//        minioClient.putObject(bucketName, objectName, "D:/cssc/pan.jpg", null);
//
//        //        文件下载
//        InputStream in = minioClient.getObject(bucketName, objectName);
//        //将字节输入流读出来
//        List<String> lines = IOUtils.readLines(in, "UTF-8");
//        //读出来
//        lines.stream().forEach(line-> System.out.println(line));
//
//    }
//
//
//
//}
