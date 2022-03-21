package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.entity.Trepaircheck;
import com.property.entity.Ufrepair;
import com.property.entity.Wycorporate;
import com.property.mapper.UfrepairMapper;
import com.property.service.DocumentnumberService;
import com.property.service.UfrepairService;
import com.property.utils.MySFTP;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UfrepairServiceImpl implements UfrepairService {
    private static final String type = "WEB";
    private static final Integer thisMonth = 1; //本月
    private static final Integer thisQuarter = 2;   //本季度
    private static final Integer thisYear = 3;  //本年
    private static final Integer lastMonth = 4; //上月
    private static final Integer lastQuarter = 5;   //上季度
    private static final Integer lastYear = 6;  //上年

    @Autowired
    private UfrepairMapper ufrepairMapper;

    @Autowired
    private DocumentnumberService documentnumberService;

    //获取部门导航栏
    @Override
    public List getWycorporateList() {
        List corporateList = new ArrayList();
        List<Wycorporate> wycorporateList = ufrepairMapper.getWycorporateList(null,null);
        Map<Integer,Wycorporate> map = new LinkedHashMap<>();
        for (Wycorporate wycorporate : wycorporateList) {
            map.put(wycorporate.getId(),wycorporate);
        }
        for (Wycorporate st : wycorporateList) {
            if(st.getParentIds().equals("0")){
                corporateList.add(st);
            }else {
                Wycorporate parent = map.get(Integer.parseInt(st.getParentIds()));
                parent.getSubclass().add(st);
            }
        }
        return corporateList;
    }

    //获取导航栏id
    public String getIds(String string){
        String restaurantName = null;
        StringBuilder sb = new StringBuilder();
        if(string != null && !string.equals("")  ){
            Integer corporateId = Integer.parseInt(string);
            //通过id查询parend_ids，decide
            List<Wycorporate> decide = ufrepairMapper.getWycorporateList(corporateId,null);
            String parentIds = decide.get(0).getParentIds();
            //判断是否为一级菜单
            if(parentIds.equals("0")){ //为一级菜单

            }else {
                String decide2 =  decide.get(0).getParentId();
                //判断是否为二级菜单
                if( decide2.equals("0")){ //二级菜单
                    List<Wycorporate> opById = ufrepairMapper.getWycorporateList(null,corporateId.toString());
                    if( opById != null && opById.size() != 0){
                        for (Wycorporate wycorporate : opById) {
                            sb.append(wycorporate.getId()).append(",");
                        }
                        restaurantName = sb.substring(0,sb.length()-1);
                    }
                }else { //三级菜单
                    restaurantName = corporateId.toString();
                }
            }
        }
        return restaurantName;
    }


    /*
    * 报修申请
    * {"mBxr":"","mBxrgh":"","mBxdw":"","mLxdh":"","mWxdz":"","mBxnr":"","swid":""}
    * */
    @Override
    public Object addUfrepair(Ufrepair ufrepair) {
        //生成单号
        String orderNo = documentnumberService.getOrderNo(type);
        ufrepair.setMGh(orderNo);
        //报修时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(new Date());
        ufrepair.setMBxsj(s);
        ufrepairMapper.addUfrepair(ufrepair);
        return "申请成功";
    }

    /**
     * 报修人查询报修记录
     * @param paramJson
     * {"pageNum":"","pageSize":"","mBxrgh":"","state":"","num":"","time":""}
     * @return
     */
    @Override
    public Object selectRecord(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String mBxrgh = rowData.getString("mBxrgh");    //报修人工号
        String state = rowData.getString("state");    //状态 0：处理中 1：已处理
        String num = rowData.getString("num");    //编号
        String time = rowData.getString("time");    //提交时间
        Integer totalSize = ufrepairMapper.selectRecordCnt(mBxrgh, state, num, time);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> list = ufrepairMapper.selectRecord(mBxrgh, state, num, time, startIndex, pageSize);
            for (Map map : list) {
                Object mPjxj = map.get("M_PJXJ");
                if (mPjxj != null && !mPjxj.equals("")){
                    map.put("M_PJXJ",Integer.parseInt(mPjxj.toString()));
                }else {
                    map.put("M_PJXJ",0);
                }
            }
            pageBean.setList(list);
        }
        return pageBean;
    }

    /**
     * 管理员维修管理
     * @param paramJson
     * {"pageNum":"","pageSize":"","state":"","num":"","name":"","time":""}
     * @return
     */
    @Override
    public Object management(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String state = rowData.getString("state");    //状态 0：未派单 1：已派发 2：已处理
        String num = rowData.getString("num");    //编号
        String name = rowData.getString("name");    //报修人员
        String time = rowData.getString("time");    //提交时间
        Integer totalSize = ufrepairMapper.managementCnt(state, num, name, time);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> management = ufrepairMapper.management(state, num, name, time, startIndex, pageSize);
            for (Map map : management) {
                Object mPjxj = map.get("M_PJXJ");
                if (mPjxj != null && !mPjxj.equals("")){
                    map.put("M_PJXJ",Integer.parseInt(mPjxj.toString()));
                }else {
                    map.put("M_PJXJ",0);
                }
            }
            pageBean.setList(management);
        }
        return pageBean;
    }


    /**
     * 管理员派单
     * @param ufrepair
     * {"mGh":"","mWxr":"","mWxlxfs":""}
     * @return
     */
    @Override
    public Object sendOrders(Ufrepair ufrepair) {
        //维修时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(new Date());
        ufrepair.setMWxsj(s);
        ufrepairMapper.sendOrders(ufrepair);
        return "派单成功";
    }

    /**
     * 完成维修
     * @param ufrepair
     * {"mGh":"","mWxsj":""}
     * @return
     */
    @Override
    public Object finishWx(Ufrepair ufrepair) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String wcsj = df.format(date);
        long currentTime = date.getTime();
        //从对象中拿到时间
        long createTime = df.parse(ufrepair.getMWxsj()).getTime();
        double diff = ((double) (currentTime -  createTime))/1000/60/60;
        //创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        ufrepair.setMWxsc(numberFormat.format(diff));
        ufrepair.setMWcsj(wcsj);
        ufrepairMapper.finishWx(ufrepair);
        return "维修完成";
    }

    /**
     * 报修评价
     * @param ufrepair
     * {"mGh":"","mPjxj":"","mPjnr":""}
     * @return
     */
    @Override
    public Object repairEvaluate(Ufrepair ufrepair) {
        ufrepairMapper.updateRepair(ufrepair);
        return "评价成功";
    }


    /*
    * 获取页面数据
    * {"mBxrgh":""}
    * */
    @Override
    public Object getData() {
        Map map = new LinkedHashMap();

        //数量统计
        Map map1 = new LinkedHashMap();
        Integer count1 = ufrepairMapper.getCount("01", null);//报修总量
        Integer count2 = ufrepairMapper.getCount("02", null);//派单总量
        Integer count3 = ufrepairMapper.getCount("03", null);//维修总量
        Integer count4 = ufrepairMapper.getCount("04", null);//评价总量
        Integer count5 = ufrepairMapper.getCount("05", null);//好评总量
        map1.put("bx",count1);
        map1.put("pd",count2);
        map1.put("wx",count3);
        map1.put("pj",count4);
        map1.put("hp",count5);
        map.put("getCount",map1);

        //派单情况分析
        Map map3 = new LinkedHashMap();
        //创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);

        Integer thisMonthBx = ufrepairMapper.getCount("01", thisMonth);//本月报修
        Integer thisMonthPd = ufrepairMapper.getCount("02", thisMonth);//本月派单
        String thisMonthRate ;  //本月比率
        if( thisMonthBx == 0){
            thisMonthRate = "0";
        }else {
            thisMonthRate = numberFormat.format((float)thisMonthPd / (float)thisMonthBx * 100);
        }
        map3.put("thisMonthBx",thisMonthBx);
        map3.put("thisMonthPd",thisMonthPd);
        map3.put("thisMonthRate",thisMonthRate);

        Integer lastMonthBx = ufrepairMapper.getCount("01", lastMonth);//上月报修
        Integer lastMonthPd = ufrepairMapper.getCount("02", lastMonth);//上月派单
        String lastMonthRate ;  //上月比率
        if( lastMonthBx == 0){
            lastMonthRate = "0";
        }else {
            lastMonthRate = numberFormat.format((float)lastMonthPd / (float)lastMonthBx * 100);
        }
        map3.put("lastMonthBx",lastMonthBx);
        map3.put("lastMonthPd",lastMonthPd);
        map3.put("lastMonthRate",lastMonthRate);

        Integer thisQuarterBx = ufrepairMapper.getCount("01", thisQuarter);//本季度报修
        Integer thisQuarterPd = ufrepairMapper.getCount("02", thisQuarter);//本季度派单
        String thisQuarterRate ;  //本季度比率
        if( thisQuarterBx == 0){
            thisQuarterRate = "0";
        }else {
            thisQuarterRate = numberFormat.format((float)thisQuarterPd / (float)thisQuarterBx * 100);
        }
        map3.put("thisQuarterBx",thisQuarterBx);
        map3.put("thisQuarterPd",thisQuarterPd);
        map3.put("thisQuarterRate",thisQuarterRate);

        Integer lastQuarterBx = ufrepairMapper.getCount("01", lastQuarter);//上季度报修
        Integer lastQuarterPd = ufrepairMapper.getCount("02", lastQuarter);//上季度派单
        String lastQuarterRate ;  //上季度比率
        if( lastQuarterBx == 0){
            lastQuarterRate = "0";
        }else {
            lastQuarterRate = numberFormat.format((float)lastQuarterPd / (float)lastQuarterBx * 100);
        }
        map3.put("lastQuarterBx",lastQuarterBx);
        map3.put("lastQuarterPd",lastQuarterPd);
        map3.put("lastQuarterRate",lastQuarterRate);

        Integer thisYearBx = ufrepairMapper.getCount("01", thisYear);//本年报修
        Integer thisYearPd = ufrepairMapper.getCount("02", thisYear);//本年派单
        String thisYearRate ;  //本年比率
        if( thisYearBx == 0){
            thisYearRate = "0";
        }else {
            thisYearRate = numberFormat.format((float)thisYearPd / (float)thisYearBx * 100);
        }
        map3.put("thisYearBx",thisYearBx);
        map3.put("thisYearPd",thisYearPd);
        map3.put("thisYearRate",thisYearRate);

        Integer lastYearBx = ufrepairMapper.getCount("01", lastYear);//上年报修
        Integer lastYearPd = ufrepairMapper.getCount("02", lastYear);//上年派单
        String lastYearRate ;  //上年比率
        if( lastYearBx == 0){
            lastYearRate = "0";
        }else {
            lastYearRate = numberFormat.format((float)lastYearPd / (float)lastYearBx * 100);
        }
        map3.put("lastYearBx",lastYearBx);
        map3.put("lastYearPd",lastYearPd);
        map3.put("lastYearRate",lastYearRate);
        map.put("sendOrders",map3);

        //报修评价分析
        Map statistics = ufrepairMapper.getStatistics();
        map.put("evaluationAnalysis",statistics);

        //按部门统计次数
        List countByBm = ufrepairMapper.getCountByBm();
        map.put("countByBm",countByBm);
        return map;
    }

    /*
    * 获取下拉框数据
    * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();
        List equipment = ufrepairMapper.getEquipment(); //设备

        List state = new ArrayList();   //订单状态(维修人)
        state.add(JSONObject.parseObject("{'id':'0','text':'处理中'}"));
        state.add(JSONObject.parseObject("{'id':'1','text':'已处理'}"));

        List adminState = new ArrayList();   //订单状态(管理员)
        adminState.add(JSONObject.parseObject("{'id':'0','text':'未派单'}"));
        adminState.add(JSONObject.parseObject("{'id':'1','text':'已派单'}"));
        adminState.add(JSONObject.parseObject("{'id':'2','text':'已处理'}"));

        resultMap.put("equipment",equipment);
        resultMap.put("state",state);
        resultMap.put("adminState",adminState);
        return resultMap;
    }

    /**
     * 通过id查询设备信息
     * @param paramJson
     * {"id":""}
     * @return
     */
    @Override
    public Object getEquipmentById(String paramJson) {
        JSONObject jsonObject = JSONObject.parseObject(paramJson);
        String id = jsonObject.getString("id");
        Map equipmentById = ufrepairMapper.getEquipmentById(id);
        return equipmentById;
    }

}
