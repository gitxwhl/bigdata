package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StEquipmentmanagement;
import com.canteen.mapper.StEquipmentmanagementMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StEquipmentmanagementService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StEquipmentmanagementServiceImpl implements StEquipmentmanagementService {

    @Autowired
    private StEquipmentmanagementMapper stEquipmentmanagementMapper;

    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    /*
    * 获取设备列表
    * {"pageNum":"","pageSize":"","param":{"restaurantName":"","equipmentName":"","equipmentCode":""}}
    * */
    @Override
    public PageBean getEquipmentList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        String string = param.getString("restaurantName");
        String restaurantName = stOperationrestaurantService.getIds(string);

        String equipmentName = param.getString("equipmentName");
        String equipmentCode = param.getString("equipmentCode");
        int totalSize = stEquipmentmanagementMapper.getEquipmentCnt(restaurantName, equipmentName, equipmentCode);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List equipmentList = stEquipmentmanagementMapper.getEquipmentList(startIndex, pageSize, restaurantName, equipmentName, equipmentCode);
            pageBean.setList(equipmentList);
        }
        return pageBean;
    }

    /*
     * 删除设备
     * {"id":""}
     * */
    @Override
    public String deleteEquipment(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String id = rowData.getString("id");
        Integer i = stEquipmentmanagementMapper.deleteEquipment(id);
        if( i > 0 ){
            return "删除成功";
        }
        return "删除失败";
    }

    /*
     * 新增设备
     * {"equipmentName":"","equipmentCode":"","restaurant":"","purchaseBatch":"","totalDevices":"","remarks":""}
     * */
    @Override
    public String insertEquipment(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String equipmentName = rowData.getString("equipmentName");
        String equipmentCode = rowData.getString("equipmentCode");
        String restaurant = rowData.getString("restaurant");
        Integer purchaseBatch = Integer.parseInt(rowData.getString("purchaseBatch"));
        Integer totalDevices = Integer.parseInt(rowData.getString("totalDevices"));
        String remarks = rowData.getString("remarks");
        StEquipmentmanagement stEquipmentmanagement = new StEquipmentmanagement(0,equipmentName,equipmentCode,restaurant,purchaseBatch,
                totalDevices,0,0,0,remarks,"0");
        Integer i = stEquipmentmanagementMapper.insertEquipment(stEquipmentmanagement);
        if( i > 0 ){
            return "新增成功";
        }
        return "新增失败";
    }

    /*
     * 修改设备
     * {"id":"","equipmentName":"","equipmentCode":"","restaurant":"","equipmentState":"","equipmentNum":"","remarks":""}
     * */
    @Override
    public String updateEquipment(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        String equipmentName = rowData.getString("equipmentName");
        String equipmentCode = rowData.getString("equipmentCode");
        String restaurant = rowData.getString("restaurant");
        String equipmentState = rowData.getString("equipmentState");
        int equipmentNum = Integer.parseInt(rowData.getString("equipmentNum"));
        String remarks = rowData.getString("remarks");
        List<StEquipmentmanagement> list = stEquipmentmanagementMapper.selectEquipmentById(id);
        Integer totalDevices = list.get(0).getTotalDevices();
        Integer useQuantity = list.get(0).getUseQuantity();
        Integer examineQuantity = list.get(0).getExamineQuantity();
        Integer scrapQuantity = list.get(0).getScrapQuantity();

       // int useQuantity = 0, examineQuantity = 0, scrapQuantity = 0;
        if ( equipmentState.equals("1")){
            useQuantity = equipmentNum;
            if( useQuantity + examineQuantity + scrapQuantity > totalDevices){
                examineQuantity = totalDevices - useQuantity - scrapQuantity;
            }
        }
        if ( equipmentState.equals("2")){
            examineQuantity = equipmentNum;
            if( useQuantity + examineQuantity + scrapQuantity > totalDevices){
                useQuantity = totalDevices - examineQuantity - scrapQuantity;
            }
        }
        if ( equipmentState.equals("3")){
            scrapQuantity = equipmentNum;
            if( useQuantity + examineQuantity + scrapQuantity > totalDevices){
                examineQuantity = totalDevices - useQuantity - scrapQuantity;
            }
        }
        StEquipmentmanagement stEquipmentmanagement = new StEquipmentmanagement(id,equipmentName,equipmentCode,restaurant,
                1,totalDevices,useQuantity,examineQuantity,scrapQuantity,remarks,"0");
        Integer i = stEquipmentmanagementMapper.updateEquipment(stEquipmentmanagement);
        if( i > 0 ){
            return "修改成功";
        }
        return "修改失败";
    }

    //查询关联属性(运维餐厅，设备状态)
    @Override
    public Map getRelevance() {
        Map<String,List> resultMap = new LinkedHashMap<>();

        //设备状态
        List<JSONObject> equipmentState = new ArrayList<>();

        List<Map> dictionaries = stOperationrestaurantMapper.dictionaries();

        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("equipmentState".equals(map.get("paraname"))){
                equipmentState.add(jsonObject);
            }
        });

        resultMap.put("equipmentState",equipmentState);

        List restaurant = stOperationrestaurantMapper.getRestaurant();
        resultMap.put("restaurant",restaurant);
        return resultMap;
    }
}
