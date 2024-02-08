package com.mashibing.easypoi_boot.mapper;

import com.mashibing.easypoi_boot.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {


//查询
    List<Course> findAllCourse();

//    添加
    void saveCourse(Course courses);


}
