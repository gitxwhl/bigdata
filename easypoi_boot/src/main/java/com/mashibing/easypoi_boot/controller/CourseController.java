package com.mashibing.easypoi_boot.controller;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.mashibing.easypoi_boot.pojo.Course;
import com.mashibing.easypoi_boot.service.CourseService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<Course> courseList= courseService.findAllCourse();
        System.out.println(courseList);
        model.addAttribute("courses",courseList);
        return "index";
    }

    /**
     * 导入数据
     * @param excelFile
     * @return
     */
    @RequestMapping("/importExcel")
    public String importExcle(MultipartFile excelFile)throws Exception{
        //设置导入参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//标题占几行
        params.setHeadRows(1);//列名占几行
        List<Course> courseList= ExcelImportUtil.importExcel(excelFile.getInputStream(), Course.class, params);
        courseService.saveCourse(courseList);
        return "redirect:/course/findAll";
    }

    /**
     * 导出数据
     */
    @RequestMapping("/exportExcel")
   public void exportExcel(HttpServletResponse response) throws Exception {
        //查询课程数据
        List<Course> courseList = courseService.findAllCourse();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("课程列表信息", "课程信息"), Course.class,courseList);
//        设置下载方式，弹窗下载
        response.setHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode("课程信息列表.xlsx", "utf-8"));
       ServletOutputStream outputStream=  response.getOutputStream();
       //将流输出
       workbook.write(outputStream);
       outputStream.close();
       workbook.close();
    }





}
