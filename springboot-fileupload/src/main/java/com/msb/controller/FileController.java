package com.msb.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
@RequestMapping("/file")
public class FileController {
    // 文件存储位置
    private final static String FILESERVER="http://127.0.0.1:8080/upload/";
    /*@RequestMapping("/upload")
    @ResponseBody
    public Map<String,String> upload(String name,
                                     String password,
                                     String nickname,
                                     @RequestPart("photo") MultipartFile photo,
                                     @RequestPart("photos") MultipartFile[] photos, HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<>();
        // 获取文件名
        String originalFilename = photo.getOriginalFilename();
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
        resource.put(String.class, photo.getBytes());
        // 上传成功之后,把文件的名字和文件的类型返回给浏览器
        map.put("message", "上传成功");
        map.put("newFileName",FILESERVER+newFileName);
        map.put("filetype", photo.getContentType());
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
    @RequestMapping("/uploadone")
    public Map<String,String> fileUpload01(MultipartFile headPhoto,HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<String,String>();
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
        map.put("sufix",headPhoto.getContentType());
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
    @PostMapping("/upload")
    public Map<String,String> upload(MultipartFile headPhoto,HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<String,String>();
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
        resource.put(String.class,headPhoto.getBytes());
        //上传成功返回文件名和文件的类型返回给浏览器
        map.put("message","文件上传成功");
        map.put("newFileName",FILESERVER + newFileName);
        map.put("filetype",headPhoto.getContentType());
        return map;
    }




}
