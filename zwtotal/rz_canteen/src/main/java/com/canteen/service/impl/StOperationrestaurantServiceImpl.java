package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StOperationrestaurant;
import com.canteen.entity.StstrategyMetering;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StOperationrestaurantService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StOperationrestaurantServiceImpl implements StOperationrestaurantService {

    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;

    //获取运维餐厅级别菜单
    @Override
    public List getStOperationList(String deFlag) {
        List operationList = new ArrayList();
        List<StOperationrestaurant> stOperationList = stOperationrestaurantMapper.getStOperationList(deFlag);
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

    /*
    *获取策略列表
    * {"pageNum":"","pageSize":"","param":{"restaurantName":"","settlementamount":""}}
    * */
    @Override
    public PageBean getStrategyList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");

        //运营餐厅的id
        String string = param.getString("restaurantName");
        String restaurantName = getIds(string);
        String settlementamount = param.getString("settlementamount");
        int totalSize = stOperationrestaurantMapper.getStrategyCnt(restaurantName, settlementamount);
        PageBean pageBean = new PageBean();
        if( totalSize == 0){
            pageBean.setPageNum(pageNum);
            pageBean.setPageSize(pageSize);
            pageBean.setTotalRecord(totalSize);
            pageBean.setList(new ArrayList());
        }else {
            pageBean.setPageSize(pageSize);
            pageBean.setPageNum(pageNum);
            pageBean.setTotalRecord(totalSize);
            Integer startIndex = pageBean.getStartIndex();
            List<StstrategyMetering> strategyList = stOperationrestaurantMapper.getStrategyList(startIndex, pageSize, restaurantName, settlementamount);
            pageBean.setList(strategyList);
        }
        return pageBean;
    }

    /*
    *删除策略
    * {"id":""}
    * */
    @Override
    public String deleteStrategy(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        int i = stOperationrestaurantMapper.deleteStrategy(id);
        if( i > 0){
            return "删除成功";
        }
        return "删除失败";
    }

    /*//查询可关联设备和餐厅
    @Override
    public Map<String,List> getEquipment() {
        List equipment = stOperationrestaurantMapper.getEquipment();
        List restaurant = stOperationrestaurantMapper.getRestaurant();
        Map map = new LinkedHashMap();
        map.put("equipment",equipment);
        map.put("restaurant",restaurant);
        return map;
    }*/

    /*
     *修改策略
     * {"param":{"id":"","policyName":"","policyCode":"","restaurant":"","meteringequipment":"","applicabletime":"","settlementamount":"","remarks":""}}
     * */
    @Override
    public String updateStrategy(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        Integer id = Integer.parseInt(param.getString("id"));
        String policyName = param.getString("policyName");
        String policyCode = param.getString("policyCode");
        String restaurant = param.getString("restaurant");
        String meteringequipment = param.getString("meteringequipment");
        String applicabletime = param.getString("applicabletime");
        String settlementamount = param.getString("settlementamount");
        String remarks = param.getString("remarks");
        StstrategyMetering ststrategyMetering = new StstrategyMetering(id,policyName,policyCode,restaurant,meteringequipment,applicabletime,settlementamount,remarks,"0");
        int i = stOperationrestaurantMapper.updateStrategy(ststrategyMetering);
        if( i > 0 ){
            return "修改成功";
        }
        return "修改失败";
    }

    /*
     *新增策略
     * {"param":{"policyName":"","policyCode":"","restaurant":"","meteringequipment":"","applicabletime":"","settlementamount":"","remarks":""}}
     * */
    @Override
    public String insertStrategy(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String policyName = param.getString("policyName");
        String policyCode = param.getString("policyCode");
        String restaurant = param.getString("restaurant");
        String meteringequipment = param.getString("meteringequipment");
        String applicabletime = param.getString("applicabletime");
        String settlementamount = param.getString("settlementamount");
        String remarks = param.getString("remarks");
        StstrategyMetering ststrategyMetering = new StstrategyMetering(0,policyName,policyCode,restaurant,meteringequipment,applicabletime,settlementamount,remarks,"0");
        stOperationrestaurantMapper.insertStrategy(ststrategyMetering);
        return "新增成功";
    }

    /*
     * 获取字典表定义
     * */
    @Override
    public Map dictionaries() {
        Map<String,List> resultMap = new LinkedHashMap<>();

        //运营餐厅
        //List<JSONObject> restaurant = new ArrayList<>();

        //结算金额
        List<JSONObject> settlementamount = new ArrayList<>();
        settlementamount.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        //适用时间
        List<JSONObject> applicabletime = new ArrayList<>();

        List<Map> dictionaries = stOperationrestaurantMapper.dictionaries();

        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            /*if("RestaurantName".equals(map.get("paraname"))){
                restaurant.add(jsonObject);
            }*/
            if("settlementamount".equals(map.get("paraname"))){
                settlementamount.add(jsonObject);
            }
            if("applicabletime".equals(map.get("paraname"))){
                applicabletime.add(jsonObject);
            }
        });

        resultMap.put("settlementamount",settlementamount);
        resultMap.put("applicabletime",applicabletime);

        List equipment = stOperationrestaurantMapper.getEquipment();
        List restaurant = stOperationrestaurantMapper.getRestaurant();

        resultMap.put("equipment",equipment);
        resultMap.put("restaurant",restaurant);
        return resultMap;
    }

    public String getIds(String string){
        String restaurantName = null;
        StringBuilder sb = new StringBuilder();
        if(string != null && !string.equals("")  ){
            Integer restaurantId = Integer.parseInt(string);
            //通过id查询parend_ids，decide
            List<Map> decide = stOperationrestaurantMapper.getDecide(restaurantId);
            int parentIds = (Integer) decide.get(0).get("parentIds");
            //判断是否为一级菜单
            if(parentIds == 0){ //为一级菜单
                /*List<StOperationrestaurant> opById = stOperationrestaurantMapper.getOpById(restaurantId);
                for (StOperationrestaurant stOperationrestaurant : opById) {  //二级菜单
                    Integer id = stOperationrestaurant.getId();
                    List<StOperationrestaurant> opById1 = stOperationrestaurantMapper.getOpById(id);
                    if(opById1 != null && opById1.size() != 0){
                        for (StOperationrestaurant operationrestaurant : opById1) {
                            sb.append(operationrestaurant.getId()).append(",");
                        }
                    }
                }
                restaurantName = sb.substring(0, sb.length() - 1);*/
            }else {
                List<Map> decide1 = stOperationrestaurantMapper.getDecide(restaurantId);
                int decide2 = (Integer) decide.get(0).get("decide");
                //判断是否为二级菜单
                if( decide2 == 2 ){ //二级菜单
                    List<StOperationrestaurant> opById = stOperationrestaurantMapper.getOpById(restaurantId);
                    if( opById != null && opById.size() != 0){
                        for (StOperationrestaurant stOperationrestaurant : opById) {
                            sb.append(stOperationrestaurant.getId()).append(",");
                        }
                        restaurantName = sb.substring(0,sb.length()-1);
                    }
                }else { //三级菜单
                    restaurantName = restaurantId.toString();
                }
            }
        }
        return restaurantName;
    }
}
