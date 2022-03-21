package com.officeServices.service.impl;

import com.officeServices.entity.Department;
import com.officeServices.mapper.DepartmentMapper;
import com.officeServices.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 部门导航栏
     * @return
     */
    @Override
    public Object getDepartmentList() {
        List departmentList = new ArrayList();
        List<Department> department = departmentMapper.getDepartment();
        Map<String,Department> map = new LinkedHashMap<>();
        for (Department dm : department) {
            map.put(dm.getId(),dm);
        }
        for (Department dm : department) {
            if(dm.getParentId().equals("04")){
                departmentList.add(dm);
            }else {
                Department parent = map.get(dm.getParentId());
                parent.getSubClass().add(dm);
            }
        }
        return departmentList;
    }

    @Override
    public String getLowerIds(String id) {
        String department = "";
        /*if (id != null && !id.equals("")){
            String level = departmentMapper.getLevel(id);
            if (level.equals("1")){ //一级部门

            }
            if (level.equals("2")){   //二级部门
                StringBuilder sb = new StringBuilder();
                List<String> lowerId = departmentMapper.getLowerId(id);     //查询三级部门
                if(lowerId != null && lowerId.size() != 0){
                    for (String s : lowerId) {
                        List<String> lowerId1 = departmentMapper.getLowerId(s); //查询四级部门
                        if (lowerId1 != null && lowerId1.size() != 0){
                            for (String s1 : lowerId1) {
                                sb.append("'").append(s1).append("',");
                            }
                        }else {
                            sb.append("'").append(s).append("',");
                        }
                    }
                    department = sb.substring(0,sb.length()-1);
                }else {
                    department = id;
                }
            }
            if (level.equals("3")){ //三级部门
                StringBuilder sb = new StringBuilder();
                List<String> lowerId = departmentMapper.getLowerId(id); //查询四级部门
                if(lowerId != null && lowerId.size() != 0){
                    for (String s : lowerId) {
                        sb.append("'").append(s).append("',");
                    }
                    department = sb.substring(0,sb.length()-1);
                }else {
                    department = id;
                }
            }
            if (level.equals("4")){ //四级部门
                department = id;
            }
        }*/
        return department;
    }
}
