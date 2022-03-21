package com.canteen.mapper;

import com.canteen.entity.StLocaleat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StLocaleatMapper {

    //申请人查询保存的申请
    List getSave(@Param("applicant") String applicant,@Param("msg") String msg,@Param("restaurant") String restaurant,
                 @Param("index")int index,@Param("pageSize")int pageSize);

    //申请人查询保存的申请的数量
    Integer getSaveCnt(@Param("applicant") String applicant,@Param("msg") String msg,@Param("restaurant") String restaurant);

    //添加申请
    Integer addApplication(StLocaleat stLocaleat);

    //修改保存的申请
    Integer updateSave(StLocaleat stLocaleat);

    //删除保存的申请
    Integer deleteSave(StLocaleat stLocaleat);

    //更改申请状态为未审批
    //Integer updateState(@Param("approvedBy")String approvedBy);

    //审批人查看已提交的申请
    List getSubmit(@Param("approvedBy") String approvedBy,@Param("msg") String msg,@Param("restaurant") String restaurant,
                   @Param("index")int index,@Param("pageSize")int pageSize);

    //审批人查看已提交的申请
    Integer getSubmitCnt(@Param("approvedBy") String approvedBy,@Param("msg") String msg,@Param("restaurant") String restaurant);

    //审批人进行审批
    Integer approval(StLocaleat stLocaleat);

    //查询审批人
    List getApprovalBy();

    //申请人查询待办数量
    Integer getBacklogCnt(@Param("applicant") String applicant,@Param("msg") String msg,
                          @Param("restaurant") String restaurant,@Param("state") String state);
    //申请人查询待办
    List getBacklog(@Param("applicant") String applicant,@Param("msg") String msg,
                    @Param("restaurant") String restaurant,@Param("state") String state,
                    @Param("index")int index,@Param("pageSize")int pageSize);
}
