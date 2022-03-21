package com.canteen.utils;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUpload {
    public static final String directory = "/home/zwtotal/";
    public static UUID newFileName = null;

    public static List UploadFile(List list){
        List list1 = new ArrayList();
        try {
            for (Object li : list) {
//取得返回json中的Content数据
                //String content = JSONPath.read((String) file, "$.Content").toString();
//去掉前面的“data:image/jpeg;base64,”的字样
                //String imgdata = content.replace("data:image/jpeg;base64,","");
                String imgdata = li.toString();
                StringBuilder sb = new StringBuilder(imgdata);
                sb.delete(0, 23);
                String image = sb.toString();

                //解码base64
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] data = decoder.decodeBuffer(image);
                for (int i = 22; i < data.length; i++) {
                    if (data[i] < 0) {
                        data[i] += 256;
                    }
                }
//写入保存成jpeg文件
                newFileName = UUID.randomUUID();
                File target = new File(directory, String.valueOf(newFileName) + ".jpg");
                FileOutputStream fos = new FileOutputStream(target);
                fos.write(data);
                fos.flush();
                fos.close();
                list1.add(directory + String.valueOf(newFileName) + ".jpg");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list1;
    }
}
