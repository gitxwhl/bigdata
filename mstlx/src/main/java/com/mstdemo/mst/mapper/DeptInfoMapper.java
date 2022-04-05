package com.mstdemo.mst.mapper;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptInfoMapper {
    /**
     * 测试返回主键id
     * @param deptInfo
     * @return
     */
    int addDept(DeptInfo deptInfo);
    /**
     * ztree
     * 根据id获取数据信息
     */
    @Select("SELECT  * FROM t_permission WHERE pid =#{pid}")
    List<Permission> findPermission(@Param("pid") String pid);


}
