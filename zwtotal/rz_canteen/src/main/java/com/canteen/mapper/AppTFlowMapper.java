package com.canteen.mapper;


import com.canteen.entity.AppTFlow;
import com.canteen.entity.StFlow;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@Repository
public interface AppTFlowMapper{
    /**
     * 添加流程节点
     * @param appTFlow
     */
    @Insert("INSERT into app_t_flow (FlowNo,Title,BusType,AddUserNo,AddTime,ApproStatus ) values (#{FlowNo},#{Title},#{BusType},#{AddUserNo},#{AddTime},#{ApproStatus})")
    void saveAppTFlow(AppTFlow appTFlow);
    /**
     * 更改流程节点状态
     * @return void
    **/
    void updateStatus(Map<String,String> map );

}

