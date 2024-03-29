package com.msb.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    /**
     * 跨服务器 多文件同步上传，即和表单数据一块上传
     * 文件和前端绑定不一致使用@RequestPart注解
     */
    @RequestMapping("/uploads")
    @ResponseBody
    public Map<String,String> uploads(String name,
                                     String password,
                                     String nickname,
                                     @RequestPart("photo") MultipartFile photo,
                                     @RequestPart("photos") MultipartFile[] photos, HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<>();
        //遍历photos接收图片的数组
        if(photos !=null && photos.length>0){
            for (int i=0;i < photos.length;i++){
                MultipartFile  multipartFile = photos[i];
                // 获取文件名
                String originalFilename = multipartFile.getOriginalFilename();
                // 避免文件名冲突,使用UUID替换文件名
                String uuid = UUID.randomUUID().toString();
                // 获取拓展名
                String extendsName = originalFilename.substring(originalFilename.lastIndexOf("."));
                //文件类型校验
                if(!extendsName.equals(".jpg")){
                    map.put("message","文件类型错误");
                }
                // 新的文件名
                String newFileName=uuid.concat(extendsName);
                // 创建 sun公司提供的jersey包中的client对象
                Client client=Client.create();
                WebResource resource = client.resource(FILESERVER + newFileName);
                //  文件保存到另一个服务器上去了
                resource.put(String.class, multipartFile.getBytes());
                // 上传成功之后,把文件的名字和文件的类型返回给浏览器
                map.put("message", "上传成功");
                map.put("newFileName",FILESERVER+newFileName);
                map.put("filetype", multipartFile.getContentType());
            }
        }
        return map;
    }

    /**
     * 相对路径即项目所在服务器多文件同步上传，即和表单数据和文件一块上传
     * 文件和前端绑定不一致使用@RequestPart注解
     */
    @RequestMapping("/uploadsXdlj")
    @ResponseBody
    public Map<String,String> uploadsXdlj(String name,
                                      String password,
                                      String nickname,
                                      @RequestPart("photo") MultipartFile photo,
                                      @RequestPart("photos") MultipartFile[] photos, HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<>();
        //遍历photos接收图片的数组
        if(photos !=null && photos.length>0){
            for (int i=0;i < photos.length;i++){
                MultipartFile  multipartFile = photos[i];
                //获取当前项目的相对路径
                //指定文件存储目录
                //        File dir=new File("d:/images");
                //        指定文件存储目录为我们项目部署环境下upload目录
                String realPath = req.getServletContext().getRealPath("/upload");
                File dir = new File(realPath);

                //如果目录不存在创建目录
                if(!dir.exists()){
                    dir.mkdir();
                }
                // 获取文件名
                String originalFilename = multipartFile.getOriginalFilename();
                // 避免文件名冲突,使用UUID替换文件名
                String uuid = UUID.randomUUID().toString();
                // 获取文件类型
                String extendsName = originalFilename.substring(originalFilename.lastIndexOf("."));
                System.out.println(extendsName);
               //校验文件类型
                if(!extendsName.equals(".jpg")){
                    map.put("message","文件类型错误");
                    return map;
                }
                // 新的文件名
                String newFileName=uuid.concat(extendsName);
                //指定文件存储位置
                System.out.println(dir);
                File file = new File(dir,newFileName);
                //保存文件
                multipartFile.transferTo(file);
                // 上传成功之后,把文件的名字和文件的类型返回给浏览器
                map.put("message", "上传成功");
                map.put("newFileName",realPath + newFileName);
                map.put("filetype", multipartFile.getContentType());
            }
        }
        return map;
    }





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
        System.out.println(realPath);
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
    /**
     * 跨服务器文件下载
     */
    @RequestMapping("fileDownload.do")
    public void fileDownload(String id, HttpServletResponse response) throws Exception {
        //根据id获取文件名，文件类型  可以从数据库获得
        String photo ="9fe2fe1d-9b93-4d2f-9b03-bc2278710cff.jpg";
        String filetype="image/jpeg";
        //设置响应头
        //告诉浏览器将数据保存到磁盘上，不在浏览器上直接解析
        response.setHeader("Content-Disposition","attachment;filename=" + photo);
        //告诉浏览器下载文件类型
        response.setContentType(filetype);
        //获取一个文件输入流
        InputStream inputStream = new URL(FILESERVER+photo).openStream();
        //获取一个指向浏览器的输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //向浏览器响应文件即可
        IOUtils.copy(inputStream, outputStream);
    }

    /**
     * 所在部署服务器文件下载
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("fileDownloadLocal.do")
    public void fileDownloadLocal(String id,String photo, HttpServletResponse response,HttpServletRequest req) throws Exception {
        //根据id获取文件名，文件类型  可以从数据库获得
        String filetype="image/jpeg";
        //设置响应头
        //告诉浏览器将数据保存到磁盘上，不在浏览器上直接解析
        response.setHeader("Content-Disposition","attachment;filename=" + photo);
        //告诉浏览器下载文件类型
        response.setContentType(filetype);
        //获取一个文件输入流
        InputStream inputStream = new URL("http://localhost:8090/upload/" + photo).openStream();
        //获取一个指向浏览器的输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //向浏览器响应文件即可
        IOUtils.copy(inputStream, outputStream);
    }



}
