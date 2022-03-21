package com.canteen.mapper;



import com.canteen.entity.bo.StApplyCanteen;
import com.canteen.entity.vo.RemoteDiningListVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StApplyCanteenMapper   {
    //mybatis-plus 改mybatis增加的基础类的mapper方法  --开始
    void insert(  StApplyCanteen entity);
    //增加查询总数的接口
    int getRemoteDiningListCount( @Param("remoteDiningListVO") RemoteDiningListVO remoteDiningListVO);
    //单个查询
    StApplyCanteen selectById(@Param("id") String id);
    //
    Integer getRemoteDiningApprovelCount(RemoteDiningListVO remoteDiningListVO);
    //-----------结束----------------
    List<Map<String, String>> getApprovelPerson(@Param("userId")String userId);

    List<Map<String, Object>> getRemoteDiningList(Page<Map<String, Object>> page, @Param("remoteDiningListVO")RemoteDiningListVO remoteDiningListVO);

    /**
     *
     * @param page
     * @param remoteDiningListVO
     * @return
     */
    List<Map<String, Object>> getRemoteDiningApprovelList(Page<Map<String, Object>> page,@Param("remoteDiningListVO")RemoteDiningListVO remoteDiningListVO);

    void approvelRemoteDining(@Param("flowId")String flowId, @Param("status")String status, @Param("userId")String userId, @Param("remark")String remark,@Param("now") String now,@Param("reason")String reason);

    List<Map<String, String>> getCompanyList();

    List<Map<String, String>> getRestaurantList(Map<String,Object> map);

    List<Map<String, String>> getNextDeptInfo(Page<Map<String, Object>> page,@Param("deptId")String deptId);

    List<Map<String, String>> getPersonList(Page<Map<String, Object>> page, @Param("deptId")String deptId);

    List<Map<String, Object>> getRemoteDiningDetail(@Param("flowId")String flowId);

    List<Map<String, Object>> createRepairId();

    List<Map<String, Object>> getFlowDetail(@Param("flowId")String flowId);

    List<Map<String, Object>> getRemoteDiningApprovelTGList(Page<Map<String, Object>> page, @Param("remoteDiningListVO")RemoteDiningListVO remoteDiningListVO);


    List<Map<String, String>> getApplyResList(String org_id);

    void updateStatus(@Param("id")String id,@Param("staus")String staus );



}
