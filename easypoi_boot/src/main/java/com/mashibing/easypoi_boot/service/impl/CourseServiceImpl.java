package com.mashibing.easypoi_boot.service.impl;
import com.mashibing.easypoi_boot.mapper.CourseMapper;
import com.mashibing.easypoi_boot.pojo.Course;
import com.mashibing.easypoi_boot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
   private CourseMapper courseMapper;
    //查询
    @Override
    public List<Course> findAllCourse() {
        return courseMapper.findAllCourse();
    }

    @Override
    public void saveCourse(List<Course> courses) {
        courses.forEach(course->{
            course.setCid(null);//自动生成id，不需要excle中的编号
            courseMapper.saveCourse(course);
        });
    }


}
