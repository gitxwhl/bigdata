package com.mashibing.easypoi_boot.service;

import com.mashibing.easypoi_boot.pojo.Course;

import java.util.List;

public interface CourseService {
    //查询
    List<Course> findAllCourse();
    //    添加
    void saveCourse(List<Course> courses);
}
