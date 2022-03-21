package com.officeServices.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.lang.reflect.Proxy;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

public class MySFTP {
    private static final String host = "10.40.70.95";
    private static final int port = 22;
    private static final String username = "root";
    private static final String password = "Sun55kong";
    public static final String directory = "/home/officeServices/file";
    public static String newFileName = null;
    public static List newFileNameList = null;

    public static ChannelSftp connect() {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {

        }
        return sftp;
    }

    /**
     * 上传文件
     * @param uploadFile 要上传的文件
     */
    public static boolean upload(String uploadFile) {
        boolean success = false;
        try {
            ChannelSftp sftp = connect();
            sftp.cd(directory);
            File file=new File(uploadFile);
            String fileName = file.getName();
            int i = fileName.lastIndexOf(".");
            fileName = fileName.substring(i);
            newFileName = UUID.randomUUID() + fileName;
            System.out.println(newFileName);
            sftp.put(new FileInputStream(file), newFileName);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 上传文件
     */
    public static boolean upload(MultipartFile file) {
        boolean success = false;
        try {
            ChannelSftp sftp = connect();
            sftp.cd(directory);
            String fileName = file.getOriginalFilename();
            int i = fileName.lastIndexOf(".");
            fileName = fileName.substring(i);
            newFileName = UUID.randomUUID() + fileName;
            System.out.println(newFileName);
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            sftp.put(inputStream, newFileName);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 上传文件
     */
    public static boolean upload(byte[] files) {
        boolean success = false;
        try {
            ChannelSftp sftp = connect();
            sftp.cd(directory);
            newFileName = UUID.randomUUID() + ".png";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(files);
            sftp.put(inputStream, newFileName);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 删除文件
     * //@param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     * //@param sftp
     */
    public static void delete(String deleteFile) {
        try {
            ChannelSftp sftp = connect();
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *多附件上传
     * @param files     文件base64码的集合
     * @return          返回拼接后的文件名
     */
    public static String getDecode(List files){
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (Object o : files) {
            String s = o.toString();
            String s1 = uploadFile(s);
            list.add(s1);
        }
        for (Object o : list) {
            sb.append(o).append(",");
        }
        newFileNameList = list;
        System.out.println(newFileNameList);
        return sb.toString();
    }

    /**
     * 上传附件
     * @param dataFile 上传附件的base64码
     * @return fileName 文件重命名
     */
    public static String uploadFile(String dataFile){
        String fileName = "";
        try{
            ChannelSftp sftp = connect();
            sftp.cd(directory);
            //获取附件类型并重命名
            String fileTypeBase = dataFile.split(",")[0];
            if(fileTypeBase.contains("image")){
                fileName = UUID.randomUUID() + ".png";
            } else if (fileTypeBase.equals("data:text/plain;base64")){
                fileName = UUID.randomUUID() + ".txt";
            } else if (fileTypeBase.equals("data:application/msword;base64")){
                fileName = UUID.randomUUID() + ".doc";
            } else if (fileTypeBase.equals("data:application/vnd.ms-excel;base64")){
                fileName = UUID.randomUUID() + ".xls";
            } else if (fileTypeBase.equals("data:application/pdf;base64")){
                fileName = UUID.randomUUID() + ".pdf";
            }else if (fileTypeBase.equals("data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64")){
                fileName = UUID.randomUUID() + ".docx";
            }else if (fileTypeBase.equals("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64")){
                fileName = UUID.randomUUID() + ".xlsx";
            }else if (fileTypeBase.equals("data:application/x-zip-compressed;base64")){
                fileName = UUID.randomUUID() + ".zip";
            }else if (fileTypeBase.equals("data:;base64")){
                fileName = UUID.randomUUID() + ".rar";
            }
            //base64解码
            String base64 = dataFile.split(",")[1];
            Base64.Decoder decoder = Base64.getMimeDecoder();
            byte[] bytes = decoder.decode(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            sftp.put(inputStream, fileName);
        }catch (Exception e){
            e.printStackTrace();
            for (Object o : newFileNameList) {
                delete(o.toString());
            }
        }
        return fileName;
    }

    /**
     * base64解码
     * @param dataFile  文件的base64码
     * @return          输入流
     */
    public static byte[] getInputStream(String dataFile){
        //base64解码
        String base64 = dataFile.split(",")[1];
        Base64.Decoder decoder = Base64.getMimeDecoder();
        byte[] bytes = decoder.decode(base64);
        return bytes;
    }

    /**
     * 获取图片的base64
     * @param dataFile
     * @return
     */
    public static String getPicture(Object dataFile) {
        String pic = "";
        if (dataFile != null && !dataFile.equals("")){
            byte[] inStream = (byte[]) dataFile;
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = encoder.encode(inStream);
            encode = encode.replaceAll("[\\s*\t\n\r]", "");
            pic = "data:image/png;base64," + encode;
        }
        return pic;
    }

    /**
     * 查看附件
     * @param enclosure  要查看的文件名称
     * @return dataFile  返回文件base64码
     */
    public static String getPath(String enclosure) {
        String dataFile = "";
        try {
            ChannelSftp sftp = connect();
            sftp.cd(directory);
            //获取附件类型
            int i = enclosure.lastIndexOf(".");
            String fileType = enclosure.substring(i);
            String fileTypeBase = "";
            if(fileType.contains("png")){
                fileTypeBase = "data:image/png;base64,";
            } else if (fileType.contains("txt")){
                fileTypeBase = "data:text/plain;base64,";
            } else if (fileType.contains("doc")){
                fileTypeBase = "data:application/msword;base64,";
            } else if (fileType.contains("xls")){
                fileTypeBase = "data:application/vnd.ms-excel;base64,";
            } else if (fileType.contains("pdf")){
                fileTypeBase = "data:application/pdf;base64,";
            }else if (fileType.contains("docx")){
                fileTypeBase = "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,";
            }else if (fileType.contains("xlsx")){
                fileTypeBase = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,";
            }else if (fileType.contains("zip")){
                fileTypeBase = "data:application/x-zip-compressed;base64,";
            }else if (fileType.contains("rar")){
                fileTypeBase = "data:;base64,";
            }
            //将附件进行base64编码
            InputStream inputStream = sftp.get(enclosure);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = inputStream.read(buf)) != -1){
                outputStream.write(buf, 0, numBytesRead);
            }
            byte[] data = outputStream.toByteArray();
            dataFile = fileTypeBase + new BASE64Encoder().encode(data);
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataFile;
    }

}
