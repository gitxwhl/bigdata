package com.msb.clint;

import com.msb.util.FastDFSClient;
import com.msb.util.FastDFSClientSave;
import org.csource.common.NameValuePair;

import java.io.*;
import java.util.Arrays;


public class StartApp {

    public static void main(String[] args) {
        upload();
//        download();
//        getFileInfo();
    }
    /**
     * 文件上传
     */
    public static void upload(){
        //读取文件
/*        File file = new File("D:\\cssc\\you.jpg");
        //上传
        String[] strings = FastDFSClient.uploadFile(file, file.getName());

        FastDFSClient.uploadFile(file, file.getName());

        System.out.println("文件名上传成功" + Arrays.asList(strings));*/
        File file = new File("D:\\cssc\\bb.jpg");
        try {
            InputStream    inputStream = new FileInputStream(file);
            String[] strings = FastDFSClientSave.uploadFile(inputStream,file.getName());
            System.out.println("文件上传成功" + Arrays.asList(strings));
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 文件下载
     */
    public static void download(){
        try {
            //获取图片信息
            InputStream in = FastDFSClientSave.downloadFile("group1","M00/00/00/wKg4CmWkvBeAAKgzAAItKNXfLDk963.jpg");
            //输出流写入磁盘
            OutputStream os = new FileOutputStream("D:\\download\\you.jpg");
            int index=0;
            while ((index=in.read()) !=-1){
                os.write(index);
            }
            os.flush();
            os.close();
            in.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取源数据信息
     */
    public static void getFileInfo(){
        NameValuePair[]  nameValuePairs =    FastDFSClient.getMetaDate("group1","M00/00/00/wKg4CmWk4AyAWdo3AAItKNXfLDk441.jpg");
            if(nameValuePairs !=null && nameValuePairs.length>0){
                for (NameValuePair nameValuePair :nameValuePairs){
                    System.out.println(nameValuePair);
                    System.out.println(nameValuePair.getName() +"=" + nameValuePair.getValue());
                }
            }

    }
}
