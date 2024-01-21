package com.msb.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jndi.toolkit.url.UrlUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class FileController {
    // 文件存储位置



    @ResponseBody
    @RequestMapping("/fileUpload01")
    public Map<String,String> fileUpload01(MultipartFile headPhoto,HttpServletRequest req) throws IOException {
       Map<String,String> map=new HashMap<String,String>();
       //文件大小不能超过5MB
        if(headPhoto.getSize()>1024*1024*2){
            map.put("message", "文件内容不能超过2MB");
            return map;
        }
        //指定文件存储目录
//        File dir=new File("d:/images");
//        指定文件存储目录为我们项目部署环境下upload目录
        String realPath = req.getServletContext().getRealPath("/upload");
        File dir = new File(realPath);
        System.out.println(realPath);
        //如果不存在创建目录
        if (!dir.exists()){
            dir.mkdir();
        }
        //获取文件名
        String filename = headPhoto.getOriginalFilename();
        //避免文件名冲突，使用uuid
        String nameUid= UUID.randomUUID().toString();
        //截取文件名后缀
       String sufix = filename.substring(filename.lastIndexOf("."));
        System.out.println(sufix);
       if(!sufix.equals(".jpg")){
           map.put("message","文件类型错误");
           return map;
       }
        //组合新的文件名
        String newFileName = nameUid+sufix;
        //指定文件存储位置
        File file = new File(dir,newFileName);
        //文件保存
        headPhoto.transferTo(file);
        //上传成功返回文件名和文件的类型返回给浏览器
        map.put("message","文件上传成功");
        map.put("newFileName",newFileName);
        map.put("sufix",sufix);
        return map;
    }







}
