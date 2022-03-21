package com.canteen.service;
import com.canteen.entity.AppTFlowdetail;
import com.canteen.entity.vo.AppTApplyCanteenVO;
import com.canteen.entity.vo.RemoteDiningListVO;
import com.canteen.utils.PageBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
public interface IAppTApplyCanteenService {

    List<Map<String,String>> getApprovelPerson(String userId);

    /**
     * 异地就餐申请
     * @param
     */
    void applyRemoteDining(AppTApplyCanteenVO appTApplyCanteen);

    /**
     * mybatispuls分页修改
     * 异地就餐列表
     * @param
     * @return
     */
//    IPage<Map<String, Object>> getRemoteDiningList(RemoteDiningListVO remoteDiningListVO);
    List<Map<String, Object>> getRemoteDiningDetail(String flowId);
    /**
     * 异地就餐审批表
     * @param
     * @return
     */
    PageBean<AppTFlowdetail> getAppTFlowdetailPage(PageBean<AppTFlowdetail> pageBean, RemoteDiningListVO remoteDiningListVO);
    /**
     * 异地就餐审批
     * @param
     */
    void approvelRemoteDining(String status, String userId, String remark, LocalDateTime now, String id, String reason);


    List<Map<String, String>> getCompanyList();

    List<Map<String, String>> getRestaurantList(Map<String, Object> map);

    List<Map<String, String>> getNextDeptInfo(Map map);

    List<Map<String, String>> getPersonList(Map map);

    List<Map<String, Object>> getFlowDetail(String flowId);

    List<Map<String, String>> getApplyResList(Map map);

    PageBean<Map<String, String>> getAllInfo(Map map);
}
