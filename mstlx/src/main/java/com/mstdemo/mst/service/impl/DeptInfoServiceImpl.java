package com.mstdemo.mst.service.impl;

import com.jcraft.jsch.*;
import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.bean.userInfo;
import com.mstdemo.mst.mapper.DeptInfoMapper;
import com.mstdemo.mst.service.DeptInfoService;
import com.mstdemo.mst.util.SFTPUtil;
import com.mstdemo.mst.util.StreamZipUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.*;


@Service
public class DeptInfoServiceImpl implements DeptInfoService {
    @Autowired
    private DeptInfoMapper deptInfoMapper;

    /**
     * 返回添加返回id
     *
     * @param deptInfo
     * @return
     */
    @Override
    public int addDept(DeptInfo deptInfo) {
        int fla = deptInfoMapper.addDept(deptInfo);
        Integer id = deptInfo.getDeptId();
        return fla;
    }


    /**
     * 递归调用
     *
     * @return
     */
    @Override
    public List<Permission> findPermission() {
        Permission parent = new Permission();
        parent.setId("0");
        queryChildPermissions(parent);
        return parent.getChildren();
    }

    /**
     * 递归
     * 自己调用自己
     * 方法一定要存在跳出逻辑
     * 方法调用时，参数之间应该有规律
     *
     * @param parent
     * @return
     */
    public void queryChildPermissions(Permission parent) {
        List<Permission> childPermissions = deptInfoMapper.findPermission(parent.getId());
        for (Permission permission : childPermissions) {
            queryChildPermissions(permission);
        }
        parent.setChildren(childPermissions);
    }

    /**
     * 处理时间循环遍历出现数组越界
     *
     * @param
     * @param
     */
    public static void getTime(String starttime, String endtime) {
        int starttimey = Integer.valueOf(starttime.substring(0, 4));
        int starttimem = Integer.valueOf(starttime.substring(5));
        int endtimey = Integer.valueOf(endtime.substring(0, 4));
        int endtimem = Integer.valueOf(endtime.substring(5));
        int l = 0;
        String xAxis[] = new String[10000];
        for (int i = 0; starttimey <= endtimey; i++) {
            if (starttimey == endtimey && starttimem <= endtimem) {
                if (starttimem < 10) {
                    xAxis[l] = starttimey + "-0" + starttimem;
                    starttimem = starttimem + 1;
                    System.out.println(xAxis[l]);
                    l = l + 1;
                } else if (starttimem < 13 && starttimem >= 10) {

                    xAxis[l] = starttimey + "-" + starttimem;
                    starttimem = starttimem + 1;
                    System.out.println(xAxis[l]);
                    l = l + 1;
                }
            }

            if (starttimey < endtimey) {
                if (starttimem < 10) {
                    xAxis[l] = starttimey + "-0" + starttimem;
                    starttimem = starttimem + 1;
                    System.out.println(xAxis[l]);
                    l = l + 1;
                } else if (starttimem < 13 && starttimem >= 10) {
                    xAxis[l] = starttimey + "-" + starttimem;
                    starttimem = starttimem + 1;
                    System.out.println(xAxis[l]);
                    l = l + 1;
                } else {
                    starttimey = starttimey + 1;
                    starttimem = starttimem - 12;
                }
            }
        }
    }

    /**
     * 解压缩
     *
     * @param tagetFileName 压缩包所在的路径
     *                      parent 指定解压的目录
     */
    public static void decompression(String tagetFileName, String parent) {
        try {
            //构造解压的输入流
            ZipInputStream zIn = new ZipInputStream(new FileInputStream(tagetFileName));
            InputStream ffff = StreamZipUtil.unzipStream(zIn);


            //目录取出来并且是一个文件处理
//            ZipEntry entry =null;
//            File file = null;
//            while ((entry= zIn.getNextEntry()) != null && !entry.isDirectory()){
//                file = new File(parent,entry.getName());
//                if(!file.exists()){
//                    new File(file.getParent()).mkdirs();//创建文件的上级目录
//                }
//                //读文件
//                OutputStream out =new FileOutputStream(file);
//                BufferedOutputStream bos= new BufferedOutputStream(out);
//                 byte[] bytes= new byte[1024];
//                 int len= -1;
//                 while ((zIn.read(bytes)) !=-1){
//                     bos.write(bytes,0,len);
//                 }
//                 //关闭流
//                bos.close();
//                System.out.println(file.getAbsolutePath() + "解压成功");
//
//
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 压缩
     *
     * @param
     */
    /*public static void compress(String tagetFileName,String parent){
        try {
            //构造解压的输入流
            ZipInputStream zIn =  new ZipInputStream(new FileInputStream(tagetFileName));

            InputStream ffff= StreamZipUtil.zipStream();

        }catch (FileNotFoundException e){
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
    public static byte[] getBytes(InputStream is) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            for (int len = 0; (len = is.read(buffer)) != -1; ) {
                os.write(buffer, 0, len);
            }
            os.flush();
            return os.toByteArray();
        }
    }


    /**
     * 将数据导成excle并放到服务器
     *
     * @param
     */
    @Override
    public void exl() {
        // 创建Excel的工作书册 Workbook,对应到一个excel文档
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建Excel的工作sheet,对应到一个excel文档的tab
        HSSFSheet sheet = wb.createSheet();
        // 设置excel每列宽度
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 4000);
        // 创建字体样式
        // 创建字体样式
        HSSFFont font = wb.createFont();
        font.setFontName("Verdana");
        font.setFontHeight((short) 300);
//        font.setColor(HSSFColor.BLUE.index);
        // 创建单元格样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置边框
//        style.setBottomBorderColor(HSSFColor.RED.index);
        style.setFont(font);// 设置字体
        // 设置单元格内容格式时间
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
        style1.setWrapText(true);// 自动换行
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);// 设定行的高度
        HSSFCell cell = row.createCell(0);
        ;
        cell.setCellStyle(style);
        cell.setCellStyle(style2);

        cell.setCellValue("No");
        cell = row.createCell(1);
        cell.setCellStyle(style2);

        cell.setCellValue("ID of items");
        cell = row.createCell(2);
        cell.setCellStyle(style2);

        List<userInfo> userList = deptInfoMapper.findUserInfo();

        for (int j = 0; j <= userList.size() - 1; j++) {
            userInfo userInfo = userList.get(j);
//            OmsTradeOrder omstradeorder = JSONObject.parseObject(innerOrder.getSendManifestData(), OmsTradeOrder.class);
            int i = 0;
            row = sheet.createRow(j + 1);
            cell = row.createCell(i);
            cell.setCellStyle(style2);
            cell.setCellValue(j + 1);

            cell = row.createCell(++i);
            cell.setCellStyle(style2);
            cell.setCellValue(userInfo.getUserId());

            cell = row.createCell(++i);
            cell.setCellStyle(style2);
            cell.setCellValue(userInfo.getUserName());

            cell = row.createCell(++i);
            cell.setCellStyle(style2);
            cell.setCellValue(userInfo.getUserPassword());

            cell = row.createCell(++i);
            cell.setCellStyle(style2);
            cell.setCellValue(userInfo.getUserRole());

            cell = row.createCell(++i);
            cell.setCellStyle(style2);
            cell.setCellValue(userInfo.getDeptId());

        }
        ChannelSftp sftp = connect("192.168.232.128", 22, "root", "rz123456");
        ByteArrayOutputStream os = new ByteArrayOutputStream();


        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] b = os.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(b);


        // 创建临时文件

        File zipFile = null;
        try {
            zipFile = File.createTempFile("评估数据上传", ".zip");
            FileOutputStream f = new FileOutputStream(zipFile);
            CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
            // 用于将数据压缩成Zip文件格式
            ZipOutputStream zos = new ZipOutputStream(csum);
            /**
             * 添加excel表格数据
             */
            zos.putNextEntry(new ZipEntry("file.xls"));
            int bytesRead = 0;
            while ((bytesRead = in.read()) != -1) {
                zos.write(bytesRead);
            }
            in.close();
            zos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //boolean res = ftp.uploadFile(excel_name, in);
        try {
            sftp.cd("/home/sftp/");//进入放置文件夹路径下
            sftp.put(in, "ceshi.xls");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将数据导成excle并放到服务器 xslx
     *
     * @param
     */
    @Override
    public void exlxlsx() {

        XSSFWorkbook wb = new XSSFWorkbook();


    }


    public static void fpexls() {
        int count = 100; //每次导入的数目
        int Lastindex = count;
        userInfo user = new userInfo();
        List<String> sharList = new ArrayList<>();
        sharList.add("AA");
        sharList.add("BB");
        sharList.add("CC");
        sharList.add("DD");
        sharList.add("EE");
        sharList.add("FF");
        sharList.add("GG");

        int insertLength = sharList.size();
        int i = 0;
        while (insertLength > 3) {
            System.out.println(sharList.subList(i, i + 3));
            System.out.println("FFFFFF");
            i = i + 3;
            insertLength = insertLength - 3;
        }
        if (insertLength > 0) {
            System.out.println(sharList.subList(i, i + insertLength));
        }
    }


    public static void main(String[] args) {
        //日期之间的调用
//        getTime("1993-08" ,"2022-11");
        //解压测试
//        decompression("D:\\sss.zip","D:\\xjzh\\");

        /*File f = new File("D:\\ys\\张盼盼.xlsx");
        try {
            InputStream in = new FileInputStream(f);
            byte[] ins= getBytes(in);
            ByteArrayInputStream inin =  new  ByteArrayInputStream(ins);
            StreamZipUtil.zipStream("www",inin);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //压缩测试
//        compress("张盼盼","");


        //批量导入
        fpexls();

    }


    public ChannelSftp connect(String host, int port, String username,
                               String password) {
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
     * 分批导入
     */
    @Override
    public void fpexl() {
        /*int count=100; //每次导入的数目
        int Lastindex=count;*/
//        userInfo user=new userInfo();
        List<String> sharList = new ArrayList<>();
        sharList.add("AA");
        sharList.add("BB");
        sharList.add("CC");
        sharList.add("DD");
        sharList.add("EE");
        sharList.add("FF");
        sharList.add("GG");
        sharList.add("HH");
        sharList.add("II");
        sharList.add("JJ");
        sharList.add("KK");
        sharList.add("LL");
        sharList.add("MM");
        sharList.add("NN");
        sharList.add("OO");
        int insertLength = sharList.size();
        int i = 0;
        while (insertLength > 600) {
            //List  结合分批导入
//            dao.insertList(sharList.subList(i, i + 600));
            i = i + 600;
            insertLength = insertLength - 600;
        }
        if (insertLength > 0) {
            //List  结合分批导入
//            dao.insertList(sharList.subList(i, i + insertLength));
        }
    }



    @Override
    public List<DeptInfo> getDeptInfoById(String deptName) {
        return deptInfoMapper.getDeptInfoById(deptName);
    }


/**
 * 分批导入
 */
   /* @Override
    public void fpexl() {
        int count=100; //每次导入的数目
        int Lastindex=count;
        userInfo user=new userInfo();
        List<String> sharList=new ArrayList<>();
        sharList.add("AA");
        sharList.add("BB");
        sharList.add("CC");
        sharList.add("DD");
        sharList.add("EE");
        sharList.add("FF");
        sharList.add("GG");
        sharList.add("HH");
        sharList.add("II");
        sharList.add("JJ");
        sharList.add("KK");
        sharList.add("LL");
        sharList.add("MM");
        sharList.add("NN");
        sharList.add("OO");
        int insertLength = sharList.size();
        int i = 0;
        while (insertLength > 3) {
            System.out.println(sharList.subList(i, i + 3));
            System.out.println("FFFFFF");
            i = i + 3;
            insertLength = insertLength - 3;
        }
        if (insertLength > 0) {
            System.out.println(sharList.subList(i, i + insertLength));
        }
    }*/


}
