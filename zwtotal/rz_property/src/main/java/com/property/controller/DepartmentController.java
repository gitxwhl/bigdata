package com.property.controller;

import com.property.entity.Department;
import com.property.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Department.do")
@Slf4j
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    /**
     * 获取部门菜单列表
     * @param
     * @return
     */
    @RequestMapping("/getDepartment")
    public Object getDepartments(){
        List<Department> DepartmentListList = new ArrayList<>();
        //读取树形图数据
        List<Department> departmentList= departmentService.findStDepartment();
        Map<String,Department> stCategorydishesMap = new HashMap<>();
        for (Department sc: departmentList){
            stCategorydishesMap.put(sc.getId(),sc);
        }
        for (Department st: departmentList){
            Department child=st;
            if (child.getParentId().equals("0")) {
                DepartmentListList.add(child);
            }else {
                Department parent = stCategorydishesMap.get(child.getParentId());
                parent.getSubclass().add(child);
            }
        }
        return DepartmentListList;
    }








}
