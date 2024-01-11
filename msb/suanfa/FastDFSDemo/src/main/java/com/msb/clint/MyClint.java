package com.msb.clint;

import com.msb.util.FastDFSClient;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;

public class MyClint {

    public static void main(String[] args) {

        /**
         * 上传
         */
        try {
            //读取文件
            File file = new File("D:\\cssc\\cc.jpg");
            InputStream    inputStream = new FileInputStream(file);
            //生成文件
            String fileName = UUID.randomUUID().toString() + ".jpg";
            //上传
             String[] result =  FastDFSClient.uploadFile(inputStream,fileName);
            System.out.println(Arrays.toString(result));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * 下载
         */
        //读取文件
         //设置文件存储位置
        /*try {
            InputStream is = FastDFSClient.downloadFile("group1","M00/00/00/wKjogGWf8oaAJc0EAAItKNXfLDk448.jpg");
            OutputStream os = new FileOutputStream("D:\\download\\you.jpg");
            int index=0;
            while ((index=is.read()) !=-1){
                os.write(index);
            }
            os.flush();
            os.close();
            is.close();
        }catch (IOException e) {
            e.printStackTrace();
        }*/



    }





}
