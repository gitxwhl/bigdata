package com.mashibing.easypoi_boot.controller.common;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.mashibing.easypoi_boot.pojo.Course;
import com.mashibing.easypoi_boot.service.CourseService;
import com.mashibing.easypoi_boot.utils.ExcelUtils;
import com.mashibing.easypoi_boot.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/ExportExcel")
public class ExportExcel {
    @Autowired
    private CourseService courseService;
    /**
     *
     * @param courses
     * @param response
     * @return
     */
    @GetMapping("/exportExcelEmp")
    public ResponseResult exportExcelEmp(Course courses, HttpServletResponse response){
        List<Course> list= courseService.findAllCourse();
        List<Course> courseList = list.stream().map(course->{
            Course course1=new Course();
            course1.setCname(course.getCname());
            course1.setCid(course.getCid());
            course1.setOrderno(course.getOrderno());
            course1.setBrief(course.getBrief());
            course1.setPrice(course.getPrice());
            return course1;
        }).collect(Collectors.toList());
        ExcelUtils.exportExcel(courseList,Course.class,"员工信息列表.xlsx",response,new ExportParams("员工列表","员工信息"));
        return ResponseResult.success("导出员工信息到excle成功！");
    }
}
