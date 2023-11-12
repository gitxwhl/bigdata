package com.mstdemo.mst.mapper;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.bean.userInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptInfoMapper {
    /**
     * 测试返回主键id
     *
     * @param deptInfo
     * @return
     */
    int addDept(DeptInfo deptInfo);

    /**
     * 入参不是用对象绑定，验证非空
     */
    @Select("SELECT  * FROM dept_info WHERE dept_name =#{deptName}")
    List<DeptInfo> getDeptInfoById(String deptName);

    /**
     * ztree
     * 根据id获取数据信息
     */
    @Select("SELECT  * FROM t_permission WHERE pid =#{pid}")
    List<Permission> findPermission(@Param("pid") String pid);

    /**
     * 查询
     */
    @Select("SELECT user_id AS userId,user_name AS userName,user_password AS userPassword,user_role AS userRole,dept_id AS deptId  FROM user_info")
    List<userInfo> findUserInfo();



}
