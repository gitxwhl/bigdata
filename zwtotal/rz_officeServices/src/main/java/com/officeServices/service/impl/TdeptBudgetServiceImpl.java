package com.officeServices.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.officeServices.entity.TdeptBudget;
import com.officeServices.entity.Tevaluate;
import com.officeServices.mapper.TapplyOrderMapper;
import com.officeServices.mapper.TdeptBudgetMapper;
import com.officeServices.service.DepartmentService;
import com.officeServices.service.TapplyOrderService;
import com.officeServices.service.TdeptBudgetService;
import com.officeServices.utils.ImportExcelUtil;
import com.officeServices.utils.MoneyUtil;
import com.officeServices.utils.PageBean;
import com.officeServices.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TdeptBudgetServiceImpl implements TdeptBudgetService {

    @Autowired
    private TdeptBudgetMapper tdeptBudgetMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TapplyOrderService tapplyOrderService;

    @Autowired
    private TapplyOrderMapper tapplyOrderMapper;

    /**
     * 查询预算列表
     * @param map
     * {"pageNum":"","pageSize":"","isPaper":"","depId":""}
     * @return
     */
    @Override
    public Object getBudget(Map<String, Object> map) {
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String isPaper = map.get("isPaper") == null ? "" : map.get("isPaper").toString();
        String depId = map.get("depId") == null ? "" : map.get("depId").toString();
        Integer totalSize = tdeptBudgetMapper.getBudgetCnt(isPaper,depId);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> budget = tdeptBudgetMapper.getBudget(isPaper, depId, startIndex, pageSize);
            for (Map itemMap : budget) {
                String beginDate = itemMap.get("BEGIN_DATE") == null ? "" : itemMap.get("BEGIN_DATE").toString();
                String endDate = itemMap.get("END_DATE") == null ? "" : itemMap.get("END_DATE").toString();
                String typeId = isPaper.equals("0")?"003":"005";
                List<Map> depUsedData = tdeptBudgetMapper.getAmountprice(typeId, beginDate, endDate, depId);
                if (depUsedData.size()>0){
                    itemMap.put("allmoney", depUsedData.get(0).get("PRICE"));//已使用金额
                    String MAX_MONEY = (itemMap.get("MAX_MONEY") == null || itemMap.get("MAX_MONEY").equals("")) ? "0" : itemMap.get("MAX_MONEY").toString();
                    String ADDED_MONEY = (itemMap.get("ADDED_MONEY") == null || itemMap.get("ADDED_MONEY").equals("")) ? "0" : itemMap.get("ADDED_MONEY").toString();
                    String moneyAmount = MoneyUtil.moneyAdd(MAX_MONEY,ADDED_MONEY); //计算额外申请金额以及部门预算累加
                    itemMap.put("depmoney",moneyAmount);
                    itemMap.put("leftmoney", MoneyUtil.moneySub(moneyAmount, depUsedData.get(0).get("PRICE").toString()));//计算剩余金额
                }/*else{
                    itemMap.put("allmoney", "0");//已使用金额
                    String MAX_MONEY = (itemMap.get("MAX_MONEY") == null || itemMap.get("MAX_MONEY").equals("")) ? "0" : itemMap.get("MAX_MONEY").toString();
                    String ADDED_MONEY = (itemMap.get("ADDED_MONEY") == null || itemMap.get("ADDED_MONEY").equals("")) ? "0" : itemMap.get("ADDED_MONEY").toString();
                    String moneyAmount = MoneyUtil.moneyAdd(MAX_MONEY,ADDED_MONEY); //计算额外申请金额以及部门预算累加
                    itemMap.put("depmoney",moneyAmount);
                    itemMap.put("leftmoney", MoneyUtil.moneySub(moneyAmount, "0"));//计算剩余金额
                }*/
            }
            pageBean.setList(budget);
        }
        return pageBean;
    }

    /**
     * 预算设置
     * @param tdeptBudget
     * {"orgid":"","orgname":"","maxmoney":"","theyear":"","beginDate":"","endDate":"","remark":"","isPaper":""}
     * @return
     */
    @Override
    public Object insertBudget(TdeptBudget tdeptBudget) {
        tdeptBudget.setId(UUID.randomUUID().toString().replace("-", ""));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tdeptBudget.setOperateTime(sdf.format(new Date()));
        tdeptBudget.setAddedMoney("0");
        tdeptBudgetMapper.insertBudget(tdeptBudget);
        return "设置成功";
    }

    /**
     * 导入年度预算
     * @param request
     * @return
     */
    @Override
    public Object importBudget(HttpServletRequest request) throws Exception {
        int j = 0;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        InputStream in = null;
        List<List<Object>> listob = null;
        MultipartFile file = multipartRequest.getFile("file");
        //获取文件名
        //String fileName = file.getOriginalFilename();
//        System.out.println("文件名-fileNames" + fileName);
        //存到到本地
        //stFileNameService.instName(fileName);
        try {
            if (file.isEmpty()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        in = file.getInputStream();
        listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
        in.close();
        for (int i = 0; i < listob.size(); i++) {
            TdeptBudget tdeptBudget = new TdeptBudget();
            List<Object> lo = listob.get(i);
            tdeptBudget.setId(String.valueOf(lo.get(0)));
            tdeptBudget.setOrgid(String.valueOf(lo.get(1)));
            tdeptBudget.setOrgname(String.valueOf(lo.get(2)));
            tdeptBudget.setMaxmoney((lo.get(3) == null || lo.get(3).equals("")) ? "0" : String.valueOf(lo.get(3)));
            tdeptBudget.setAddedMoney((lo.get(4) == null || lo.get(4).equals("")) ? "0" : String.valueOf(lo.get(4)));
            tdeptBudget.setTheyear(String.valueOf(lo.get(5)));
            tdeptBudget.setBeginDate(String.valueOf(lo.get(6)));
            tdeptBudget.setEndDate(String.valueOf(lo.get(7)));
            tdeptBudget.setRemark(String.valueOf(lo.get(8)));
            tdeptBudget.setIsPaper(String.valueOf(lo.get(9)));
            tdeptBudget.setOperateTime(lo.get(10).equals("") ? null : String.valueOf(lo.get(10)));
            int i1 = tdeptBudgetMapper.insertBudget(tdeptBudget);
            if( i1 > 0 ){
                j++;
            }
        }
        return "成功导入"+j+"条数据";
    }

    /**
     * 通过部门id查询部门年度预算
     * @param map
     * {"orgId":"","isPaper":""}
     * @return
     */
    @Override
    public Object getBudgetInfo(Map<String, Object> map) {
        String isPaper = map.get("isPaper") == null ? "" : map.get("isPaper").toString();
        String orgId = map.get("orgId") == null ? "" : map.get("orgId").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        List<Map> budgetInfo = tdeptBudgetMapper.getBudgetInfo(isPaper, orgId, date);
        for (Map itemMap : budgetInfo) {
            String beginDate = itemMap.get("BEGIN_DATE") == null ? "" : itemMap.get("BEGIN_DATE").toString();
            String endDate = itemMap.get("END_DATE") == null ? "" : itemMap.get("END_DATE").toString();
            String typeId = isPaper.equals("0")?"003":"005";
            List<Map> depUsedData = tdeptBudgetMapper.getAmountprice(typeId, beginDate, endDate, orgId);
            if (depUsedData.size()>0){
                itemMap.put("allmoney", depUsedData.get(0).get("PRICE"));//已使用金额
                String MAX_MONEY = (itemMap.get("MAX_MONEY") == null || itemMap.get("MAX_MONEY").equals("")) ? "0" : itemMap.get("MAX_MONEY").toString();
                String ADDED_MONEY = (itemMap.get("ADDED_MONEY") == null || itemMap.get("ADDED_MONEY").equals("")) ? "0" : itemMap.get("ADDED_MONEY").toString();
                String moneyAmount = MoneyUtil.moneyAdd(MAX_MONEY,ADDED_MONEY); //计算额外申请金额以及部门预算累加
                itemMap.put("depmoney",moneyAmount);
                itemMap.put("leftmoney", MoneyUtil.moneySub(moneyAmount, depUsedData.get(0).get("PRICE").toString()));//计算剩余金额
            }/*else{
                itemMap.put("allmoney", "0");//已使用金额
                String moneyAmount = MoneyUtil.moneyAdd(itemMap.get("MAX_MONEY").toString(), itemMap.get("ADDED_MONEY").toString()); //计算额外申请金额以及部门预算累加
                itemMap.put("depmoney",moneyAmount);
                itemMap.put("leftmoney", MoneyUtil.moneySub(moneyAmount, "0"));//计算剩余金额
            }*/
        }
        return budgetInfo;
    }


    /**
     * 下拉框
     * @return
     */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int i = Integer.parseInt(sdf.format(new Date()));
        //年度
        List dateList = new ArrayList();
        for (int j = 0; j < 5 ; j++) {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+ i + "','text':'" + i + "年度'}");
            dateList.add(jsonObject);
            i++;
        }
        resultMap.put("date",dateList);

        //预算类型
        List budgetTypes = new ArrayList();
        budgetTypes.add(JSONObject.parseObject("{'id':'0','text':'办公文具'}"));
        budgetTypes.add(JSONObject.parseObject("{'id':'1','text':'打印纸'}"));
        resultMap.put("budgetTypes",budgetTypes);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
        String nowDate = sd.format(new Date());
        //当前时间
        resultMap.put("nowDate",nowDate);
        resultMap.put("thisYear",nowDate.substring(0,4) + "年度");
        return resultMap;
    }

    /**
     *用户查询申请情况
     * @param map
     * {"pageNum":"","pageSize":"","beginPersonId":"","typeid":"","depId":"","status":""}
     * @return
     */
    @Override
    public Object getSituation(Map<String, Object> map) {
        Map resultMap = new LinkedHashMap();
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String beginPersonId = map.get("beginPersonId").toString();
        String typeid = map.get("typeid").toString();
        String depId = map.get("depId") == null ? "" : map.get("depId").toString();
        String ids = departmentService.getLowerIds(depId);
        String status = map.get("status") == null ? "" : map.get("status").toString();
        Integer totalSize = tdeptBudgetMapper.getSituationCnt(typeid,beginPersonId,ids,status);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> approvalList = tdeptBudgetMapper.getSituation(typeid, beginPersonId, ids, status, startIndex, pageSize);
            if(typeid.equals("006")){
                for (Map<String, Object> stringObjectMap : approvalList) {
                    String receiptDate = stringObjectMap.get("begindate") == null ? "" : stringObjectMap.get("begindate").toString() ;
                    String isPaper = stringObjectMap.get("isPaper") == null ? "0" : stringObjectMap.get("isPaper").toString();
                    String demanddepid = stringObjectMap.get("demanddepid") == null ? "" : stringObjectMap.get("demanddepid").toString();
                    List<Map> budgetInfo = tdeptBudgetMapper.getBudgetInfo(isPaper, demanddepid, receiptDate);
                    for (Map itemMap : budgetInfo) {
                        String beginDate = itemMap.get("BEGIN_DATE") == null ? "" : itemMap.get("BEGIN_DATE").toString();
                        String endDate = itemMap.get("END_DATE") == null ? "" : itemMap.get("END_DATE").toString();
                        String typeId = isPaper.equals("0")?"003":"005";
                        List<Map> depUsedData = tdeptBudgetMapper.getAmountprice(typeId, beginDate, endDate, depId);
                        if (depUsedData.size()>0){
                            itemMap.put("allmoney", depUsedData.get(0).get("PRICE"));//已使用金额
                            String MAX_MONEY = (itemMap.get("MAX_MONEY") == null || itemMap.get("MAX_MONEY").equals("")) ? "0" : itemMap.get("MAX_MONEY").toString();
                            String ADDED_MONEY = (itemMap.get("ADDED_MONEY") == null || itemMap.get("ADDED_MONEY").equals("")) ? "0" : itemMap.get("ADDED_MONEY").toString();
                            String moneyAmount = MoneyUtil.moneyAdd(MAX_MONEY,ADDED_MONEY); //计算额外申请金额以及部门预算累加
                            itemMap.put("depmoney",moneyAmount);
                            itemMap.put("leftmoney", MoneyUtil.moneySub(moneyAmount, depUsedData.get(0).get("PRICE").toString()));//计算剩余金额
                        }/*else{
                            itemMap.put("allmoney", "0");//已使用金额
                            String moneyAmount = MoneyUtil.moneyAdd(itemMap.get("MAX_MONEY").toString(), itemMap.get("ADDED_MONEY").toString()); //计算额外申请金额以及部门预算累加
                            itemMap.put("depmoney",moneyAmount);
                            itemMap.put("leftmoney", MoneyUtil.moneySub(moneyAmount, "0"));//计算剩余金额
                        }*/
                    }
                    stringObjectMap.put("budgetInfo",budgetInfo);
                }
            }else {
                for (Map<String, Object> stringObjectMap : approvalList) {
                    String orderId = stringObjectMap.get("orderid") == null ? "" : stringObjectMap.get("orderid").toString();
                    List<Map<String, Object>> goods = tapplyOrderMapper.getGoodById(orderId);
                    stringObjectMap.put("goods",goods);
                    if(typeid.equals("003") || typeid.equals("005")){   //003:打印纸  005:办公文具
                        Double totalPrice = 0.0;
                        for (Map<String, Object> good : goods) {
                            Integer num = good.get("goodamount") == null ? 0 : (Integer)good.get("goodamount");
                            Double price = good.get("unitprice") == null ? 0.0 : Double.parseDouble(good.get("unitprice").toString()) ;
                            totalPrice += num * price;
                        }

                        String demanddepId = stringObjectMap.get("demanddepid") == null ? "" : stringObjectMap.get("demanddepid").toString();//需求部门id
                        Map<String, Object> checkMoneyMap = new HashMap<>();
                        checkMoneyMap.put("deptId", demanddepId);
                        checkMoneyMap.put("IS_PAPER", typeid.equals("005") ? "1" : "0");//0：办公用品   1：打印纸
                        Map<String, Object> nowMoney = (Map) tapplyOrderService.getRemainDepMoney(checkMoneyMap).getData();
                        //double MAX_MONEY = Double.valueOf(nowMoney.get("MAX_MONEY").toString());//剩余预算金额
                        if(nowMoney != null && nowMoney.size() != 0){
                            stringObjectMap.put("MAX_MONEY",nowMoney.get("MAX_MONEY"));
                        }else {
                            stringObjectMap.put("MAX_MONEY","0");
                        }
                        stringObjectMap.put("totalPrice",totalPrice);
                    }
                }
            }
            pageBean.setList(approvalList);
        }
        Integer data = tdeptBudgetMapper.getData(typeid, beginPersonId, ids, status);
        resultMap.put("page",pageBean);
        resultMap.put("data",data);
        return resultMap;
    }

    /**
     * 用户查询评价列表
     * @param map
     * {"pageNum":"","pageSize":"","beginPersonId":"","typeid":""}
     * @return
     */
    @Override
    public Object getPjList(Map<String, Object> map) {
        Map resultMap = new LinkedHashMap();
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String beginPersonId = map.get("beginPersonId").toString();
        String typeid = map.get("typeid").toString();
        Integer totalSize = tdeptBudgetMapper.getPjListCnt(typeid, beginPersonId, null, null);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> pjList = tdeptBudgetMapper.getPjList(typeid, beginPersonId, null, startIndex, pageSize);
            for (Map map1 : pjList) {
                String orderid = map1.get("orderid").toString();
                List<Map> pjGoods = tdeptBudgetMapper.getPjGoods(orderid);
                for (Map pjGood : pjGoods) {
                    String goodno = pjGood.get("goodno").toString();
                    Map history = tdeptBudgetMapper.getHistory(goodno);
                    Integer sum = Integer.parseInt(history.get("sum").toString()) ;
                    Integer cnt = Integer.parseInt(history.get("cnt").toString());
                    Integer historyPj = 0;
                    if (cnt != 0){
                        historyPj = sum / cnt;
                    }
                    pjGood.put("history",historyPj);
                }
                map1.put("goods",pjGoods);
            }
            pageBean.setList(pjList);
        }
        //待评价数量
        Integer dpjCnt = tdeptBudgetMapper.getPjListCnt(typeid, beginPersonId, null, "11");
        //已评价数量
        Integer ypjCnt = tdeptBudgetMapper.getPjListCnt(typeid, beginPersonId, null, "12");
        resultMap.put("page",pageBean);
        resultMap.put("dpj",dpjCnt);
        resultMap.put("ypj",ypjCnt);
        return resultMap;
    }

    /**
     * 用户评价
     * @param list
     * [{"orderId":"","content":"","number":"","pictureUrl":"","goodNo":""}]
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object evaluate(List<Tevaluate> list) {
        for (Tevaluate tevaluate : list) {
            //用户评价
            tdeptBudgetMapper.evaluate(tevaluate);

            String orderId = tevaluate.getOrderId();
            //修改主表状态
            tdeptBudgetMapper.updatePjzt(orderId);
        }
        return "评价成功";
    }


}
