package com.canteen.mapper;
import com.canteen.entity.AppTApplyCanteen;
import com.canteen.entity.AppTFlowdetail;
import com.canteen.entity.vo.RemoteDiningListVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@Mapper
public interface AppTApplyCanteenMapper {
    /**
     * 根据ACCOUNT获取用户信息
     * @param userId
     * @return
     */

    List<Map<String, String>> getApprovelPerson(@Param("userId") String userId);
    /**
     * 添加申请
     * @param
     * @return
     */
    void saveAppTApplyCanteen(AppTApplyCanteen appTApplyCanteen);
    /**
     * mybatispuls分页修改
     * 异地就餐列表
     * @param
     * @return
     */
    List<Map<String, Object>> getRemoteDiningList(Page<Map<String, Object>> page, @Param("remoteDiningListVO") RemoteDiningListVO remoteDiningListVO);

    /**
     * 列表获取总数
     * 异地就餐列表
     * @param
     * @return
     */
    Integer getRemoteDiningCount(@Param("remoteDiningListVO") RemoteDiningListVO remoteDiningListVO);





    /**
     * mybatispuls分页修改
     * @param
     * @param
     * @return
     */
//    IPage<Map<String, Object>> getRemoteDiningApprovelList(Page<Map<String, Object>> page, @Param("remoteDiningListVO") RemoteDiningListVO remoteDiningListVO);







    /**
     * 异地就餐审批
     * @param flowId
     * @param status
     * @param userId
     * @param remark
     * @param now
     * @param reason
     */
    void approvelRemoteDining(@Param("flowId")String flowId, @Param("status")String status, @Param("userId")String userId, @Param("remark")String remark,@Param("now") String now,@Param("reason")String reason);



    List<Map<String, String>> getCompanyList();

    List<Map<String, String>> getRestaurantList(Map<String, Object> map);

    List<Map<String, String>> getNextDeptInfo(Page<Map<String, Object>> page, @Param("deptId") String deptId);

    List<Map<String, String>> getPersonList(Page<Map<String, Object>> page, @Param("deptId") String deptId);


    List<Map<String, Object>> getRemoteDiningDetail(@Param("flowId") String flowId);

    List<Map<String, Object>> createRepairId();

    List<Map<String, Object>> getFlowDetail(@Param("flowId") String flowId);
    /**
     * 审批列表
     * @param
     * @param
     * @return
     */
    List<AppTFlowdetail> getRemoteDiningApprovelTGList(@Param("remoteDiningListVO") RemoteDiningListVO remoteDiningListVO);
    /**
     * 列表总数
     * @param
     * @param
     * @return
     */
    Integer getRemoteDiningApprovelTGAccount(@Param("remoteDiningListVO") RemoteDiningListVO remoteDiningListVO);

    List<Map<String, String>> getApplyResList(String org_id);
    /**
     * 根据id获取申请信息
     * @param
     * @param
     * @return
     */
     AppTApplyCanteen selectById(String id);
     /**
      更改状态
     **/
     void updateStatus(@Param("id")String id,@Param("staus")String staus );
    //代办
    List<Map<String,String>> getAllInfo(Map map);

    Integer getAllInfoCount(Map map);

}
