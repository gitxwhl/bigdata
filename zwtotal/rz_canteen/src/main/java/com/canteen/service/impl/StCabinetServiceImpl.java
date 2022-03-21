package com.canteen.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StCabinet;
import com.canteen.mapper.StCabinetMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.mapper.StOrderManagementMapper;
import com.canteen.service.StCabinetService;
import com.canteen.service.StOrderManagementService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StCabinetServiceImpl implements StCabinetService {

    @Autowired
    private StCabinetMapper stCabinetMapper;

    @Autowired
    private StOperationrestaurantServiceImpl serviceImpl;

    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;

    @Autowired
    private StOrderManagementService stOrderManagementService;

    /*
     * 餐柜管理列表
     * {"pageNum":"","pageSize":"","number":"","name":"","state":"","restaurant":""}
     * */
    @Override
    public Object getCabinet(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String number = rowData.getString("number");
        String name = rowData.getString("name");
        String state = rowData.getString("state");
        String str = rowData.getString("restaurant");
        String restaurant = serviceImpl.getIds(str);
        Integer totalSize = stCabinetMapper.getCabinetCnt(number, name, state, restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List cabinet = stCabinetMapper.getCabinet(number, name, state, restaurant, startIndex, pageSize);
            pageBean.setList(cabinet);
        }
        return pageBean;
    }

    /*
     * 查询订单详情
     * {"orderNumber":""}
     * */
    @Override
    public Object getOrderInfo(String paramJson) {
        StringBuilder param = new StringBuilder();
        Map map = (Map) stOrderManagementService.getOrderByNo(paramJson);
        JSONObject rowData = new JSONObject(map);
        String reservepersonnel = rowData.getString("reservepersonnel");
        String scheduled = rowData.getString("scheduled");
        String[] split = scheduled.split("-");
        String date = split[0] + "年" + split[1] + "月" + split[2] + "日";
        String dictionary = rowData.getString("dictionary");
        String deduction = rowData.getString("deduction");
        param.append(reservepersonnel).append(date).append(dictionary).append(" ").append(deduction).append(" ");
        String details = param.toString();
        List dishs = rowData.getJSONArray("dishs");
        if (dishs != null && dishs.size() != 0) {
            for (Object dish : dishs) {
                JSONObject jsonObject = (JSONObject) dish;
                String name = jsonObject.getString("name");
                String dishNum = jsonObject.getString("dishNum");
                param.append(name).append(dishNum).append("份,");
            }
            details = param.substring(0, param.length() - 1);
        }
        Map resultMap = new LinkedHashMap();
        resultMap.put("details", details);
        return resultMap;
    }


    /*
     *餐柜上货
     * {"launchTime":"","shelf":"","param":[{"id":"","orderNo":""}]}
     * */
    @Transactional
    @Override
    public Object addOrder(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String launchTime = rowData.getString("launchTime");
        String shelf = rowData.getString("shelf");
        List param = rowData.getJSONArray("param");
        for (Object o : param) {
            StCabinet stCabinet = new StCabinet();
            JSONObject jsonObject = (JSONObject)o;
            StringBuilder sb = new StringBuilder();
            String orderNo = jsonObject.getString("orderNo");
            String id = jsonObject.getString("id");
            if(id != null && !id.equals("")){
                if (orderNo != null && !orderNo.equals("")) {
                    Map map = (Map) stOrderManagementService.getOrderByNo("{'orderNumber':'" + orderNo + "'}");
                    JSONObject rowData1 = new JSONObject(map);
                    List dishs = rowData1.getJSONArray("dishs");
                    String details = "";
                    if (dishs != null && dishs.size() != 0) {
                        for (Object dish : dishs) {
                            JSONObject jsonObject1 = (JSONObject) dish;
                            String name = jsonObject1.getString("name");
                            String dishNum = jsonObject1.getString("dishNum");
                            sb.append(name).append(dishNum).append("份,");
                        }
                        details = sb.substring(0, sb.length() - 1);
                    }
                    stCabinet.setId(Integer.parseInt(id));
                    stCabinet.setOrderNo(orderNo);
                    stCabinet.setLaunchTime(launchTime);
                    stCabinet.setShelf(shelf);
                    stCabinet.setDetails(details);
                    stCabinetMapper.addOrder(stCabinet);
                }
            }
        }
        return "操作成功";
    }

    /*
     *更换订单
     * {"launchTime":"","shelf":"","param":[{"id":"","orderNo":""}]}
     * */
    @Transactional
    @Override
    public Object updateOrder(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String launchTime = rowData.getString("launchTime");
        String shelf = rowData.getString("shelf");
        List param = rowData.getJSONArray("param");
        for (Object o : param) {
            StCabinet stCabinet = new StCabinet();
            JSONObject jsonObject = (JSONObject)o;
            StringBuilder sb = new StringBuilder();
            int id = Integer.parseInt(jsonObject.getString("id"));
            String orderNo = jsonObject.getString("orderNo");
            String orderNumById = stCabinetMapper.getOrderNumById(id);
            if (!orderNo.equals(orderNumById) || (orderNo != null && !orderNo.equals(""))) {
                Map map = (Map) stOrderManagementService.getOrderByNo("{'orderNumber':'" + orderNo + "'}");
                JSONObject rowData1 = new JSONObject(map);
                List dishs = rowData1.getJSONArray("dishs");
                String details = "";
                if (dishs != null && dishs.size() != 0) {
                    for (Object dish : dishs) {
                        JSONObject jsonObject1 = (JSONObject) dish;
                        String name = jsonObject1.getString("name");
                        String dishNum = jsonObject1.getString("dishNum");
                        sb.append(name).append(dishNum).append("份,");
                    }
                    details = sb.substring(0, sb.length() - 1);
                }
                stCabinet.setId(id);
                stCabinet.setOrderNo(orderNo);
                stCabinet.setLaunchTime(launchTime);
                stCabinet.setShelf(shelf);
                stCabinet.setDetails(details);
                stCabinetMapper.addOrder(stCabinet);
            }
        }
        return "操作成功";
    }

    /*
     * 清空餐柜
     * */
    @Override
    public Object emptyCabinet() {
        stCabinetMapper.emptyCabinet();
        return "清空餐柜成功";
    }

    /*
     * 获取下拉框数据
     * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();

        //餐柜状态
        List sideboardState = new ArrayList();
        sideboardState.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        List<Map> dictionaries = stOperationrestaurantMapper.dictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'" + map.get("parakey") + "','text':'" + map.get("paravalue") + "'}");
            if ("sideboardState".equals(map.get("paraname"))) {
                sideboardState.add(jsonObject);
            }
        });

        //上柜人员
        List person = stCabinetMapper.getPerson();

        //空餐柜
        List emptyCabinet = stCabinetMapper.getEmptyCabinet();

        //非空餐柜
        //List noEmptyCabinet = stCabinetMapper.getNoEmptyCabinet();

        //餐柜订单
        List cabinOrder = stCabinetMapper.getCabinOrder();

        resultMap.put("sideboardState", sideboardState);
        resultMap.put("person", person);
        resultMap.put("emptyCabinet", emptyCabinet);
        //resultMap.put("noEmptyCabinet", noEmptyCabinet);
        resultMap.put("cabinOrder", cabinOrder);
        return resultMap;
    }
}
