package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.TokenModel;
import com.canteen.mapper.StFinanceManagementMapper;
import com.canteen.service.StFinanceManagementService;
import com.canteen.service.StfoodManagementService;
import com.canteen.utils.PageBean;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class StFinanceManagementServiceImpl implements StFinanceManagementService {

    @Autowired
    private StFinanceManagementMapper stFinanceManagementMapper;

    @Autowired
    private StOperationrestaurantServiceImpl operationrestaurantServiceImpl;

    @Autowired
    private StfoodManagementService stfoodManagementService;

    /*
    * 就餐结算明细列表
    * {"pageNum":"","pageSize":"","restaurant":"","startTime":"","endTime":""}
    * */
    @Override
    public Object detailList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String str = rowData.getString("restaurant");
        String restaurant = operationrestaurantServiceImpl.getIds(str);
        String startTime = rowData.getString("startTime");
        String endTime = rowData.getString("endTime");
        Integer totalSize = stFinanceManagementMapper.detailListCnt(restaurant, startTime, endTime);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        Map onlineMap = new LinkedHashMap();
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> list = stFinanceManagementMapper.detailList(restaurant, startTime, endTime, startIndex, pageSize);
            for (Map map : list) {
                Object category = map.get("category");
                if(category != null && !category.equals("")){
                    String dishCategory = stfoodManagementService.getDishCategory(category.toString());
                    map.put("category",dishCategory);
                }
            }
            pageBean.setList(list);

            List onlineZs = stFinanceManagementMapper.onlineTopFive("1",restaurant,startTime,endTime);   //线上主食销量top5
            List onlineTl = stFinanceManagementMapper.onlineTopFive("2",restaurant,startTime,endTime);   //线上汤类销量top5
            List onlineSg = stFinanceManagementMapper.onlineTopFive("3",restaurant,startTime,endTime);   //线上水果销量top5
            List onlineLc = stFinanceManagementMapper.onlineTopFive("4",restaurant,startTime,endTime);   //线上凉菜销量top5
            List onlineRc = stFinanceManagementMapper.onlineTopFive("5",restaurant,startTime,endTime);   //线上热菜销量top5
            onlineMap.put("zs",dataProcessing(onlineZs));
            onlineMap.put("tl",dataProcessing(onlineTl));
            onlineMap.put("sg",dataProcessing(onlineSg));
            onlineMap.put("lc",dataProcessing(onlineLc));
            onlineMap.put("rc",dataProcessing(onlineRc));
        }

        Map map = new LinkedHashMap();
        map.put("pageBean",pageBean);
        map.put("online",onlineMap);    //线上销量top5
        map.put("all",onlineMap);       //总销量top5
        return map;
    }

    /*
    *食堂经营成本统计
    *{"pageNum":"","pageSize":"","restaurant":"","startTime":"","endTime":""}
    * */
    @Override
    public Object costStatistics(String paramJson) {
        //创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        Map resultMap = new LinkedHashMap();
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String str = rowData.getString("restaurant");
        String restaurant = operationrestaurantServiceImpl.getIds(str);
        String startTime = rowData.getString("startTime");
        String endTime = rowData.getString("endTime");
        Integer totalSize = stFinanceManagementMapper.costListCnt(restaurant, startTime, endTime);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        Double tCost = 0.0;  //经营成本
        Double tIncome = 0.0;  //经营收入
        Double tProfit = 0.0;  //经营利润
        Double tRetainedProfits = 0.0;  //净利润
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> list = stFinanceManagementMapper.costList(restaurant, startTime, endTime, startIndex, pageSize,"1");
            for (Map map : list) {
                Object scheduled = map.get("scheduled");
                Object mealtype = map.get("mealtype");
                StringBuilder sb = new StringBuilder();
                List<Map> saleInfo = new ArrayList();   //线上售出菜品信息
                Integer cnt = 0;    //售出菜品数量
                Double cost = 0.0;  //经营成本
                Double income = 0.0;  //经营收入
                Double profit = 0.0;  //经营利润
                Double retainedProfits = 0.0;  //净利润
                if(scheduled != null && !scheduled.equals("") && mealtype != null && !mealtype.equals("")){
                    List<Map> orderNo = stFinanceManagementMapper.getOrderNo(scheduled.toString(), mealtype.toString(),restaurant);
                    for (Map map1 : orderNo) {
                        Object ordernumber = map1.get("ordernumber");
                        if(ordernumber != null && !ordernumber.equals("")){
                            sb.append("'").append(ordernumber).append("',");
                        }
                    }

                    if(sb != null && !sb.equals("")){
                        String s = sb.substring(0,sb.length()-1);
                        saleInfo = dataProcessing(stFinanceManagementMapper.getSaleInfo(s));
                        for (Map map1 : saleInfo) {
                            Object dishNum = map1.get("dishNum");
                            if(dishNum != null && !dishNum.equals("")){
                                cnt += (Integer) dishNum;   //线上售出菜品数量
                                income += (Integer)dishNum * (Double)map1.get("referenceprice");
                            }

                            Object category = map1.get("category");
                            if(category != null && !category.equals("")){
                                String dishCategory = stfoodManagementService.getDishCategory(category.toString());
                                map1.put("category",dishCategory);
                            }
                        }
                    }
                }

                //线下数据-智盘获取
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String d = sdf.format(new Date());
//              TokenModel tokenModel = new TokenModel(3,d,"json",101,102,"8295",11,);

                profit = income - cost;
                retainedProfits = profit * ( 1 - 0.15);
                map.put("cnt",cnt);
                map.put("cost",cost);
                map.put("income",income);
                map.put("profit",profit);
                map.put("retainedProfits",numberFormat.format(retainedProfits));
                map.put("saleInfo",saleInfo);
            }
            pageBean.setList(list);

            List<Map> list1 = stFinanceManagementMapper.costList(restaurant, startTime, endTime, 0, 0, null);
            for (Map map : list1) {
                Object scheduled = map.get("scheduled");
                Object mealtype = map.get("mealtype");
                StringBuilder sb = new StringBuilder();
                if(scheduled != null && !scheduled.equals("") && mealtype != null && !mealtype.equals("")){
                    List<Map> orderNo = stFinanceManagementMapper.getOrderNo(scheduled.toString(), mealtype.toString(),restaurant);
                    for (Map map1 : orderNo) {
                        Object ordernumber = map1.get("ordernumber");
                        if(ordernumber != null && !ordernumber.equals("")){
                            sb.append("'").append(ordernumber).append("',");
                        }
                    }
                    if(sb != null && !sb.equals("")){
                        String s = sb.substring(0,sb.length()-1);
                        List<Map> saleInfo = dataProcessing(stFinanceManagementMapper.getSaleInfo(s));
                        for (Map map1 : saleInfo) {
                            Object dishNum = map1.get("dishNum");
                            if(dishNum != null && !dishNum.equals("")){
                                tIncome += (Integer)dishNum * (Double)map1.get("referenceprice");
                            }
                        }
                    }
                }
            }
            tProfit = tIncome - tCost;
            tRetainedProfits = tProfit * ( 1 - 0.15);
        }
        resultMap.put("pageBean",pageBean);
        resultMap.put("tCost",tCost);
        resultMap.put("tIncome",tIncome);
        resultMap.put("tProfit",tProfit);
        resultMap.put("tRetainedProfits",numberFormat.format(tRetainedProfits));
        return resultMap;
    }

    //sum求和的数据转为int类型
    public List  dataProcessing(List<Map> list){
        for (Map map : list) {
            Set set = map.keySet();
            if(set.contains("num")){
                Object num = map.get("num");
                if(num != null && !num.equals("")){
                    num = ((Double)num).intValue();
                }else {
                    num = 0;
                }
                map.put("num",num);
            }

            if(set.contains("dishNum")){
                Object dishNum = map.get("dishNum");
                if(dishNum != null && !dishNum.equals("")){
                    dishNum = ((Double)dishNum).intValue();
                }else {
                    dishNum = 0;
                }
                map.put("dishNum",dishNum);
            }
        }
        return list;
    }

}
