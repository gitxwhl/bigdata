package com.canteen.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StCabinet;
import com.canteen.entity.StOrderManagement;
import com.canteen.entity.StOrdering;
import com.canteen.mapper.StCabinetMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.mapper.StOrderingMapper;
import com.canteen.service.DocumentnumberService;
import com.canteen.service.StOrderManagementService;
import com.canteen.service.StOrderingService;
import com.canteen.service.StfoodManagementService;
import com.canteen.utils.MySFTP;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StOrderingServiceImpl implements StOrderingService {

    @Autowired
    private StOrderingMapper stOrderingMapper;

    @Autowired
    private StCabinetMapper stCabinetMapper;

    @Autowired
    private StfoodManagementService managementService;

    @Autowired
    private StOperationrestaurantMapper operationrestaurantMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    @Autowired
    private DocumentnumberService documentnumberService;

    @Autowired
    private StOrderManagementService stOrderManagementService;


    /*
    * 线上订餐列表查询
    * {"pageNum":"","pageSize":"","restaurant":"","orderNum":"","scheduled":"","mealType":"","deduction":""}
    * */
    @Override
    public Object getOrderList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String str = rowData.getString("restaurant");
        String restaurant = stOperationrestaurantService.getIds(str);
        String orderNum = rowData.getString("orderNum");
        String scheduled = rowData.getString("scheduled");
        String mealType = rowData.getString("mealType");
        String deduction = rowData.getString("deduction");
        Integer orderListCnt = stOrderingMapper.getOrderListCnt(restaurant, orderNum, scheduled, mealType, deduction);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(orderListCnt);
        if(orderListCnt == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> orderList = stOrderingMapper.getOrderList(restaurant, orderNum, scheduled, mealType, deduction, startIndex, pageSize);
            for (Map map : orderList) {
                Object personnel = map.get("personnel");
                Object orderNumber = map.get("ordernumber");
                Map info = getInfo(personnel, orderNumber);
                map.put("personCnt",info.get("personCnt"));
                map.put("zsCnt",info.get("zsCnt"));
                map.put("tlCnt",info.get("tlCnt"));
                map.put("sgCnt",info.get("sgCnt"));
                map.put("lcCnt",info.get("lcCnt"));
                map.put("rcCnt",info.get("rcCnt"));
            }
            pageBean.setList(orderList);
        }
        return pageBean;
    }

    /*
    *预订
    * {"reservePersonnel":"","restaurant":"","scheduled":"","telephone":"","mealType":"","person":[],
    * "dishes":[{"dishId":"","dishNum":""}],"addr":"","pickTime":"","address":""}
    * */
    @Transactional
    @Override
    public Object reservation(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String reservePersonnel = rowData.getString("reservePersonnel");
        String restaurant = rowData.getString("restaurant");
        String scheduled = rowData.getString("scheduled");
        String telephone = rowData.getString("telephone");
        String mealType = rowData.getString("mealType");
        String addr = rowData.getString("addr");
        String address = rowData.getString("address");
        String pickTime = rowData.getString("pickTime");
        JSONArray array = rowData.getJSONArray("person");
        String personnel = managementService.sort(array);
        JSONArray dishes = rowData.getJSONArray("dishes");
        //生成订单编号
        String orderNo = documentnumberService.getOrderNo("DD");
        StOrdering stOrdering = new StOrdering();
        stOrdering.setOrderNum(orderNo);
        stOrdering.setAddr(addr);
        stOrdering.setPersonnel(personnel);
        stOrdering.setAddress(address);
        stOrderingMapper.addOrdering(stOrdering);
        Double costTotal = 0.0;
        for (Object dish : dishes) {
            JSONObject jsonObject = (JSONObject) dish;
            int dishId = Integer.parseInt(jsonObject.getString("dishId"));
            Double price = stOrderingMapper.getPrice(dishId);
            String dishNum = jsonObject.getString("dishNum");
            costTotal += price * Integer.parseInt(dishNum);
            stOrderingMapper.orderDishes(orderNo,dishId,dishNum);
        }

        StOrderManagement stOrderManagement = new StOrderManagement();
        stOrderManagement.setOrderNumber(orderNo);
        stOrderManagement.setReservePersonnel(reservePersonnel);
        stOrderManagement.setTelephone(telephone);
        stOrderManagement.setRestaurant(restaurant);
        stOrderManagement.setScheduled(scheduled);
        stOrderManagement.setDictionary(mealType);
        stOrderManagement.setPickTime(pickTime);
        stOrderManagement.setCostTotal(costTotal.toString());
        stOrderManagement.setOrdertype("0");
        stOrderingMapper.addOrderManagement(stOrderManagement);
        return "预订成功";
    }

    /*
    * 预订菜品详情
    * {"orderNumber":""}
    * */
    @Override
    public Object getOrderInfo(String paramJson) {
        JSONObject rowDara = JSONObject.parseObject(paramJson);
        String orderNumber = rowDara.getString("orderNumber");
        Map orderInfo = stOrderingMapper.getOrderInfo(orderNumber);
        Object personnel = orderInfo.get("personnel");
        Map info = getInfo(personnel, orderNumber);
        orderInfo.put("info",info);
        return orderInfo;
    }

    /*
    * 取消预订
    * {"id":""}
    * */
    @Transactional
    @Override
    public Object cancelOrder(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        stOrderingMapper.cancelOrder(id);
        String orderNum = stOrderingMapper.getOrderNum(id);
        stOrderingMapper.deleteOrder(orderNum);
        return "取消成功";
    }

    /*
    * 获取菜品信息
    * {"restaurant":"","date":"","mealType":""}
    * */
    @Override
    public Object getDishes(String paramJson) {
        Map map = new LinkedHashMap();
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String restaurant = rowData.getString("restaurant");
        String date = rowData.getString("date");
        String mealType = rowData.getString("mealType");
        List<Map> list1 = stOrderingMapper.getDishes(restaurant, date, mealType,"1");  //主食
        for (Map dish : list1) {
            String category = dish.get("category").toString();
            String dishCategory = managementService.getDishCategory(category);
            dish.put("category",dishCategory);

            Object foodpictures = dish.get("foodpictures");
            if(foodpictures != null && !foodpictures.equals("")){
                foodpictures =  MySFTP.getPath(foodpictures.toString());
            }
            dish.put("foodpictures",foodpictures);
        }
        map.put("zs",list1);

        List<Map> list2 = stOrderingMapper.getDishes(restaurant, date, mealType,"2");  //汤类
        for (Map dish : list2) {
            String category = dish.get("category").toString();
            String dishCategory = managementService.getDishCategory(category);
            dish.put("category",dishCategory);

            Object foodpictures = dish.get("foodpictures");
            if(foodpictures != null && !foodpictures.equals("")){
                foodpictures =  MySFTP.getPath(foodpictures.toString());
            }
            dish.put("foodpictures",foodpictures);
        }
        map.put("tl",list2);

        List<Map> list3 = stOrderingMapper.getDishes(restaurant, date, mealType,"3");  //水果
        for (Map dish : list3) {
            String category = dish.get("category").toString();
            String dishCategory = managementService.getDishCategory(category);
            dish.put("category",dishCategory);

            Object foodpictures = dish.get("foodpictures");
            if(foodpictures != null && !foodpictures.equals("")){
                foodpictures =  MySFTP.getPath(foodpictures.toString());
            }
            dish.put("foodpictures",foodpictures);
        }
        map.put("sg",list3);

        List<Map> list4 = stOrderingMapper.getDishes(restaurant, date, mealType,"4");  //凉菜
        for (Map dish : list4) {
            String category = dish.get("category").toString();
            String dishCategory = managementService.getDishCategory(category);
            dish.put("category",dishCategory);

            Object foodpictures = dish.get("foodpictures");
            if(foodpictures != null && !foodpictures.equals("")){
                foodpictures =  MySFTP.getPath(foodpictures.toString());
            }
            dish.put("foodpictures",foodpictures);
        }
        map.put("lc",list4);

        List<Map> list5 = stOrderingMapper.getDishes(restaurant, date, mealType,"5");  //热菜
        for (Map dish : list5) {
            String category = dish.get("category").toString();
            String dishCategory = managementService.getDishCategory(category);
            dish.put("category",dishCategory);

            Object foodpictures = dish.get("foodpictures");
            if(foodpictures != null && !foodpictures.equals("")){
                foodpictures =  MySFTP.getPath(foodpictures.toString());
            }
            dish.put("foodpictures",foodpictures);
        }
        map.put("rc",list5);

        return map;
    }

    /*
    * 获取下拉框数据
    * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();

        //餐别
        List mealType = new ArrayList();
        mealType.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        //扣费情况
        List deduction = new ArrayList();
        deduction.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        //取餐地址
        List addr = new ArrayList();

        List<Map> dictionaries = operationrestaurantMapper.dictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("mealtype".equals(map.get("paraname"))){
                mealType.add(jsonObject);
            }
            if("deduction".equals(map.get("paraname"))){
                deduction.add(jsonObject);
            }
            if("addr".equals(map.get("paraname"))){
                addr.add(jsonObject);
            }
        });

        List restaurant = operationrestaurantMapper.getRestaurant(); //下单餐厅
        List person = stCabinetMapper.getPerson();  //人员

        resultMap.put("mealType",mealType);
        resultMap.put("deduction",deduction);
        resultMap.put("restaurant",restaurant);
        resultMap.put("person",person);
        resultMap.put("addr",addr);
        return resultMap;
    }


    /*
    * 获取人员及菜品信息
    * */
    public Map getInfo(Object personnel,Object orderNumber){
        Map map = new LinkedHashMap();
        Integer personCnt = 0;
        String personName = "";
        if(personnel != null && !personnel.equals("")) {
            personCnt = stOrderingMapper.getPersonCnt(personnel.toString());
            List<Map> names = stOrderingMapper.getPersonName(personnel.toString());
            for (Map name : names) {
                Object name1 = name.get("name");
                if (name1 != null && !name1.equals("")) {
                    personName += name1.toString() + "、";
                }
            }
            personName = personName.substring(0,personName.length()-1);
        }
        map.put("personCnt",personCnt); //人员总数
        map.put("personName",personName); //人员名单

        if(orderNumber != null && !orderNumber.equals("")) {
            List<Map> dishInfo = stOrderingMapper.getDishInfo(orderNumber.toString(), "1");//主食
            String zs = "";
            Integer zsCnt = 0;
            for (Map map1 : dishInfo) {
                Object name = map1.get("NAME");
                Object dishNum = map1.get("dishNum");
                if (name != null && !name.equals("")){
                    zs += name;
                    if (dishNum != null && !dishNum.equals("")) {
                        zsCnt += Integer.parseInt(dishNum.toString());
                        zs += Integer.parseInt(dishNum.toString()) + "份,";
                    }
                }
            }
            if(zs != null && !zs.equals("")){
                zs = zs.substring(0,zs.length()-1);
            }
            map.put("zsCnt", zsCnt); //主食数量
            map.put("zs", zs); //主食

            List<Map> dishInfo1 = stOrderingMapper.getDishInfo(orderNumber.toString(), "2");//汤类
            String tl = "";
            Integer tlCnt = 0;
            for (Map map1 : dishInfo1) {
                Object name = map1.get("NAME");
                Object dishNum = map1.get("dishNum");
                if (name != null && !name.equals("")){
                    tl += name;
                    if (dishNum != null && !dishNum.equals("")) {
                        tlCnt += Integer.parseInt(dishNum.toString());
                        tl += Integer.parseInt(dishNum.toString()) + "份,";
                    }
                }
            }
            if(tl != null && !tl.equals("")){
                tl = tl.substring(0,tl.length()-1);
            }
            map.put("tlCnt", tlCnt); //汤类数量
            map.put("tl", tl); //汤类

            List<Map> dishInfo2 = stOrderingMapper.getDishInfo(orderNumber.toString(), "3");//水果
            String sg = "";
            Integer sgCnt = 0;
            for (Map map1 : dishInfo2) {
                Object name = map1.get("NAME");
                Object dishNum = map1.get("dishNum");
                if (name != null && !name.equals("")){
                    sg += name;
                    if (dishNum != null && !dishNum.equals("")) {
                        sgCnt += Integer.parseInt(dishNum.toString());
                        sg += Integer.parseInt(dishNum.toString()) + "份,";
                    }
                }
            }
            if(sg != null && !sg.equals("")){
                sg = sg.substring(0,sg.length()-1);
            }
            map.put("sgCnt", sgCnt); //水果数量
            map.put("sg", sg); //水果

            List<Map> dishInfo3 = stOrderingMapper.getDishInfo(orderNumber.toString(), "4");//凉菜
            String lc = "";
            Integer lcCnt = 0;
            for (Map map1 : dishInfo3) {
                Object name = map1.get("NAME");
                Object dishNum = map1.get("dishNum");
                if (name != null && !name.equals("")){
                    lc += name;
                    if (dishNum != null && !dishNum.equals("")) {
                        lcCnt += Integer.parseInt(dishNum.toString());
                        lc += Integer.parseInt(dishNum.toString()) + "份,";
                    }
                }
            }
            if(lc != null && !lc.equals("")){
                lc = lc.substring(0,lc.length()-1);
            }
            map.put("lcCnt", lcCnt); //凉菜数量
            map.put("lc", lc); //凉菜数量

            List<Map> dishInfo4 = stOrderingMapper.getDishInfo(orderNumber.toString(), "5");//热菜
            String rc = "";
            Integer rcCnt = 0;
            for (Map map1 : dishInfo4) {
                Object name = map1.get("NAME");
                Object dishNum = map1.get("dishNum");
                if (name != null && !name.equals("")){
                    rc += name;
                    if (dishNum != null && !dishNum.equals("")) {
                        rcCnt += Integer.parseInt(dishNum.toString());
                        rc += Integer.parseInt(dishNum.toString()) + "份,";
                    }
                }
            }
            if(rc != null && !rc.equals("")){
                rc = rc.substring(0,rc.length()-1);
            }
            map.put("rcCnt", rcCnt); //热菜数量
            map.put("rc", rc); //热菜

            Integer count = zsCnt + tlCnt + sgCnt + lcCnt + rcCnt;
            map.put("count",count);
        }

        return map;
    }


    //-------------------------------------打包服务------------------------------------------------------
    /*
    * 打包服务列表查询
    * {"pageNum":"","pageSize":"","restaurant":"","orderNum":"","person":"","scheduled":"","mealType":"","type":""}
    * */
    @Override
    public Object getPackageService(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String str = rowData.getString("restaurant");
        String restaurant = stOperationrestaurantService.getIds(str);
        String orderNum = rowData.getString("orderNum");
        String person = rowData.getString("person");
        String scheduled = rowData.getString("scheduled");
        String mealType = rowData.getString("mealType");
        String type = rowData.getString("type");
        Integer totalSize = stOrderingMapper.getPackageServiceCnt(restaurant, orderNum, person, scheduled, mealType, type);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> packageService = stOrderingMapper.getPackageService(restaurant, orderNum, person, scheduled, mealType, type, startIndex, pageSize);
            for (Map map : packageService) {
                Object personnel = map.get("personnel");
                Object ordernumber = map.get("ordernumber");
                Map info = getInfo(personnel, ordernumber);
                map.put("personCnt",info.get("personCnt"));
                map.put("zsCnt",info.get("zsCnt"));
                map.put("tlCnt",info.get("tlCnt"));
                map.put("sgCnt",info.get("sgCnt"));
                map.put("lcCnt",info.get("lcCnt"));
                map.put("rcCnt",info.get("rcCnt"));
            }
            pageBean.setList(packageService);
        }
        return pageBean;
    }

    /*
    * 打包上柜
    * {"orderNo":"","id":"","launchTime":"","shelf":"","tablewareNum":""}
    * */
    @Override
    public Object packaging(StCabinet stCabinet) {
        StringBuilder param = new StringBuilder();
        String orderNo = stCabinet.getOrderNo();
        Map map = (Map)stOrderManagementService.getOrderByNo("{'orderNumber':'" + orderNo + "'}");
        JSONObject rowData = new JSONObject(map);
        List dishs = rowData.getJSONArray("dishs");
        String details = "";
        if (dishs != null && dishs.size() != 0){
            for (Object dish : dishs) {
                JSONObject jsonObject = (JSONObject)dish;
                String name = jsonObject.getString("name");
                String dishNum = jsonObject.getString("dishNum");
                param.append(name).append(dishNum).append("份,");
            }
            details = param.substring(0,param.length()-1);
        }
        stCabinet.setDetails(details);
        stOrderingMapper.packaging(stCabinet);
        return "打包成功";
    }

    /*
    * 下拉框数据
    * */
    @Override
    public Object getDropDownBox() {
        Map resultMap = new LinkedHashMap();
        //餐别
        List mealType = new ArrayList();
        mealType.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        //订单类型
        List orderType = new ArrayList();
        orderType.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        List<Map> dictionaries = operationrestaurantMapper.dictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("mealtype".equals(map.get("paraname"))){
                mealType.add(jsonObject);
            }
            if("addr".equals(map.get("paraname"))){
                orderType.add(jsonObject);
            }
        });

        //上架人员
        List person = stCabinetMapper.getPerson();

        //空餐柜
        List emptyCabinet = stCabinetMapper.getEmptyCabinet();

        resultMap.put("mealType",mealType);
        resultMap.put("orderType",orderType);
        resultMap.put("person",person);
        resultMap.put("emptyCabinet",emptyCabinet);
        return resultMap;
    }


    //--------------------------------------------异地餐食配送------------------------------------------------------
    /*
    *异地配送列表查询
    * {"pageNum":"","pageSize":"","restaurant":"","orderNum":"","scheduled":"","mealType":""}
    * */
    @Override
    public Object distributionList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String str = rowData.getString("restaurant");
        String restaurant = stOperationrestaurantService.getIds(str);
        String orderNum = rowData.getString("orderNum");
        String scheduled = rowData.getString("scheduled");
        String mealType = rowData.getString("mealType");
        Integer totalSize = stOrderingMapper.distributionListCnt(restaurant, orderNum, scheduled, mealType);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> list = stOrderingMapper.distributionList(restaurant, orderNum, scheduled, mealType, startIndex, pageSize);
            for (Map map : list) {
                Object personnel = map.get("personnel");
                Object orderNumber = map.get("ordernumber");
                Map info = getInfo(personnel, orderNumber);
                map.put("personCnt",info.get("personCnt"));
                map.put("zsCnt",info.get("zsCnt"));
                map.put("tlCnt",info.get("tlCnt"));
                map.put("sgCnt",info.get("sgCnt"));
                map.put("lcCnt",info.get("lcCnt"));
                map.put("rcCnt",info.get("rcCnt"));
            }
            pageBean.setList(list);
        }
        return pageBean;
    }


}
