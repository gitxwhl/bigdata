package com.canteen.service;


import java.util.List;
import java.util.Map;

public interface ICommonService {

    List<Map<String,String>> getCompanyOrDeptList(String type, String id);

    List<Map<String,String>> getRestaurantList(String id);

    List<Map<String,String>> getPersonList(String id);

    List getStOperationList(String type);

    List getGoodType();

    List<Map<String,String>> findCompanyId(Map<String,Object> map);

    List<Map<String,String>> findDeparmentId(Map<String,Object> map);

    List<Map<String,String>> findCompany();

    List<Map<String,String>> finddeparmentIds(String id);

}
