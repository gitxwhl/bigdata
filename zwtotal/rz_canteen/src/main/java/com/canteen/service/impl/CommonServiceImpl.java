package com.canteen.service.impl;

import com.canteen.entity.StOperationrestaurant;
import com.canteen.mapper.StCommonMapper;
import com.canteen.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lilong
 * @Date: 2020/11/24 09:49
 * @Description: 公共查询类的service
 */
@Transactional(rollbackFor = { Exception.class })
@Service
public class CommonServiceImpl implements ICommonService {


    @Autowired
    private StCommonMapper mapper;
    /**
     *  查询公司部门信息
    **/
    @Override
    public List<Map<String, String>> getCompanyOrDeptList(String type, String id) {
        id = (id==null || "".equals(id) )? "101" : id;
        List<Map<String, String>> list = mapper.getCompanyOrDeptList(type,id);
        return list;
    }
    /**
     *  查询公司的餐厅信息
     **/
    @Override
    public List<Map<String,String>> getRestaurantList(String id) {
        return mapper.getRestaurantList(id);
    }
    /**
     * 查询公司的餐厅信息
     **/
    //    @Override
    //    public List<Map<String, String>> getRestaurantList(String id) {
    //        //list封装
    //        List<Map<String, String>> dpartmentCt = new ArrayList<>();
    //        //获取部门信息
    //        List<Map<String,String>> dpartment  = mapper.finddeparmentIds(id);
    //        for (int i=0;i < dpartment.size();i++){
    //            dpartmentCt.add(dpartment.get(i));
    //        }
    //        //获取餐厅
    //        List<Map<String, String>> stlist = mapper.getRestaurantList( id);
    //        for (int i=0;i < stlist.size();i++){
    //            dpartmentCt.add(stlist.get(i));
    //        }
    //        return dpartmentCt;
    //    }

    @Override
    public List<Map<String, String>> getPersonList(String id) {
        List<Map<String, String>> list = mapper.getPersonList( id);
        return list;
    }

    @Override
    public List getStOperationList(String type) {
        List operationList = new ArrayList();
        List<StOperationrestaurant> stOperationList = mapper.getStOperationList(type);
        Map<String,StOperationrestaurant> map = new LinkedHashMap<>();
        for (StOperationrestaurant stOperationrestaurant : stOperationList) {
            map.put(stOperationrestaurant.getId(),stOperationrestaurant);
        }
        for (StOperationrestaurant st : stOperationList) {
            if(st.getParentIds().equals("1")){
                operationList.add(st);
            }else {
                StOperationrestaurant parent = map.get(st.getParentIds());
                parent.getSubclass().add(st);
            }
        }
        return operationList;
    }

    @Override
    public List getGoodType() {
        return mapper.getGoodType();
    }


    @Override
    public List<Map<String, String>> findCompanyId(Map<String,Object> map) {
        String account= map.get("account")==null? "" :map.get("account").toString();
        return mapper.findCompanyId(account);
    }

    @Override
    public List<Map<String, String>> findDeparmentId(Map<String,Object> map) {
       String  account = map.get("account") == null ? "" : map.get("account").toString();
        return mapper.findDeparmentId(account);
    }

    @Override
    public List<Map<String, String>> findCompany() {
        List<Map<String,String>> alllist = new ArrayList<>();
        List<Map<String,String>> xjgslist = mapper.findCompany();
        for (int i=0;i< xjgslist.size();i++){
            alllist.add(xjgslist.get(i));
        }
        List<Map<String,String>> shigslist= mapper.findShiCompany();
        for (int i=0;i< shigslist.size();i++){
            alllist.add(shigslist.get(i));
        }
        return alllist;
    }

    @Override
    public List<Map<String, String>> finddeparmentIds(String bid) {
        return mapper.finddeparmentIds(bid);
    }
}
