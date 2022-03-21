package com.canteen.service;

import com.canteen.entity.vo.RemoteDiningListVO;
import com.canteen.entity.vo.StApplyCanteenVO;
import com.canteen.utils.PageBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IStApplyCanteenService {

    List<Map<String,String>> getApprovelPerson(String userId);


    void applyRemoteDining(StApplyCanteenVO appTApplyCanteen);

    PageBean getRemoteDiningList(RemoteDiningListVO remoteDiningListVO);

    List<Map<String, Object>> getRemoteDiningDetail(String flowId);

    PageBean getRemoteDiningApprovelList(RemoteDiningListVO remoteDiningListVO);

    void approvelRemoteDining(String flowId, String status, String userId, String remark, LocalDateTime now, String id, String reason);

    List<Map<String, String>> getCompanyList();

    List<Map<String, String>> getRestaurantList(Map<String,Object> map);

    List<Map<String, String>> getNextDeptInfo(Map map);

    List<Map<String, String>> getPersonList(Map map);

    List<Map<String, Object>> getFlowDetail(String flowId);

    List<Map<String, String>> getApplyResList(Map map);
}
