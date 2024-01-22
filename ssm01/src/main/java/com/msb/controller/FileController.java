package com.msb.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jndi.toolkit.url.UrlUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private final static String FILESERVER="http://192.168.56.1:8080/upload/";


    /*@ResponseBody
    @RequestMapping("fileUpload.do")
    public Map<String,String> fileUpload(MultipartFile headPhoto, HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<String,String>();
        // 获取文件名
        String originalFilename = headPhoto.getOriginalFilename();
        // 避免文件名冲突,使用UUID替换文件名
        String uuid = UUID.randomUUID().toString();
        // 获取拓展名
        String extendsName = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新的文件名
        String newFileName=uuid.concat(extendsName);
        // 创建 sun公司提供的jersey包中的client对象
        Client client=Client.create();
        WebResource resource = client.resource(FILESERVER + newFileName);
        //  文件保存到另一个服务器上去了
        resource.put(String.class, headPhoto.getBytes());
        // 上传成功之后,把文件的名字和文件的类型返回给浏览器
        map.put("message", "上传成功");
        map.put("newFileName", newFileName);
        map.put("filetype", headPhoto.getContentType());
        return map;
    }*/

    /**
     * 项目相对路径：单文件上传
     * @param headPhoto
     * @param req
     * @return
     * @throws IOException
     */
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


    /**
     * 跨服务器的单文件文件上传
     * @param headPhoto
     * @param req
     * @return
     * @throws IOException
     */
    @ResponseBody
//    @RequestMapping(value = "/fileUpload02",method = {RequestMethod.POST})
    @PostMapping("/fileUpload02")
    public Map<String,String> fileUpload02(MultipartFile headPhoto,HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<String,String>();
        //文件大小不能超过5MB
        if(headPhoto.getSize()>1024*1024*2){
            map.put("message", "文件内容不能超过2MB");
            return map;
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

        //创建sun公司提供的jersey包中的client对象
        Client client = Client.create();
        WebResource resource =  client.resource(FILESERVER+ newFileName);
//        保存文件将文件保存到另一个服务器
        try {
            resource.put(String.class,headPhoto.getBytes());
        }catch (Exception e){
            System.out.println(e.getMessage());

        }


        //上传成功返回文件名和文件的类型返回给浏览器
        map.put("message","文件上传成功");
        map.put("newFileName",FILESERVER+newFileName);
        map.put("sufix",sufix);
        return map;
    }







}
