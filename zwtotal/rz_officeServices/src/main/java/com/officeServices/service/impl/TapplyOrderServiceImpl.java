package com.officeServices.service.impl;

import com.officeServices.entity.ApplyOrder;
import com.officeServices.entity.ApplyOrderApproval;
import com.officeServices.entity.ApplyOrderGood;
import com.officeServices.entity.Tstock;
import com.officeServices.mapper.TapplyOrderMapper;
import com.officeServices.mapper.TdeptBudgetMapper;
import com.officeServices.service.DepartmentService;
import com.officeServices.service.DocumentnumberService;
import com.officeServices.service.TapplyOrderService;
import com.officeServices.utils.MoneyUtil;
import com.officeServices.utils.PageBean;
import com.officeServices.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TapplyOrderServiceImpl implements TapplyOrderService {

    @Autowired
    private TapplyOrderMapper tapplyOrderMapper;

    @Autowired
    private DocumentnumberService documentnumberService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TdeptBudgetMapper tdeptBudgetMapper;

    /**
     * 提交办公服务用品申请
     *
     * @param map
     * {"beginpersonid":"","beginpersonname":"","demanddep":"","demanddepid":"","receiptdate":"","applyphone":"","remark":"",
     * "typeid":"","meeting_name":"","meeting_person_number":"","amountPrice":"",
     *  "goods":[{"goodid":"","goodno":"","goodname":"","goodamount":"","specs":"","brand":"","storeno":"","unitprice":"","unit":"","pictureurl":"","location":""}]}
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object submitApplyOrder(Map<String, Object> map) {
        try {
            String orderid = documentnumberService.getOrderNo("BG");//办公服务物资申请号
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String operateDate = df.format(new Date());//操作时间
            String demanddep = map.get("demanddep") == null ? "" : map.get("demanddep").toString();//需求部门
            String demanddepId = map.get("demanddepid") == null ? "" : map.get("demanddepid").toString();//需求部门id
            String receiptdate = (map.get("receiptdate") == null || map.get("receiptdate").equals(""))? "0000-00-00" : map.get("receiptdate").toString();//交货日期
            String remark =  map.get("remark") == null?"":map.get("remark").toString();//申请原因
            String typeid = map.get("typeid") == null ? "001" : map.get("typeid").toString();//类型
            String applyphone = map.get("applyphone") == null ? "" : map.get("applyphone").toString();//联系方式
            //String step = map.get("step") == null ? "0" : map.get("step").toString();//联系方式
            //根据前台选择的审批人角色插入
            //String dealUserId = map.get("dealUserId") == null ? "" : map.get("dealUserId").toString(); //审批人id
            //String dealUserName = map.get("dealUserName") == null ? "" : map.get("dealUserName").toString();  //审批人
            //String ROLE_TYPE = map.get("ROLE_TYPE") == null ? "" : map.get("ROLE_TYPE").toString();//类型
            String meeting_name = map.get("meeting_name") == null ? "" : map.get("meeting_name").toString();//会议名称
            String meeting_person_number = (map.get("meeting_person_number") == null || map.get("meeting_person_number").equals("")) ? "0" : map.get("meeting_person_number").toString();//会议人数

            //订单总价
            BigDecimal amountPrice = new BigDecimal((map.get("amountPrice") == null || map.get("amountPrice").equals("")) ? "0" : map.get("amountPrice").toString());//订单总金额

            //这是办公用品申请中的物品条目具体信息
            List<Map<String, String>> goods = (ArrayList<Map<String, String>>) map.get("goods");

            //查询当前预算以及剩余预算
            if (typeid.equals("003") || typeid.equals("005")) {     //003:打印纸  005:办公文具
                Map<String, Object> checkMoneyMap = new HashMap<>();
                checkMoneyMap.put("deptId", demanddepId);
                checkMoneyMap.put("IS_PAPER", typeid.equals("005") ? "1" : "0");//0：办公用品   1：打印纸
                String message = getRemainDepMoney(checkMoneyMap).getMessage();
                if (message != null && !message.equals("")){
                    return message;
                }
                Map<String, Object> nowMoney = (Map) getRemainDepMoney(checkMoneyMap).getData();
                double normalleftmoney = Double.valueOf(nowMoney.get("normalleftmoney").toString());//剩余预算金额
                if (Double.valueOf(amountPrice.toString()) > normalleftmoney) {
                    return "预算不足，请申请额外预算！";
                }
            }

            //生成的主键
            String tableId = UUID.randomUUID().toString().replace("-", "");

            //插入申请表t_apply_order
            ApplyOrder applyOrder = new ApplyOrder();
            applyOrder.setId(tableId);
            applyOrder.setOrderId(orderid);
            applyOrder.setBeginPersonId(map.get("beginpersonid") == null ? "" : map.get("beginpersonid").toString());
            applyOrder.setBeginPersonName(map.get("beginpersonname") == null ? "" : map.get("beginpersonname").toString());
            applyOrder.setBeginDate(operateDate);//提交日期
            applyOrder.setStatus("0"); //单据状态：0：提交  1：审批  2:二级审核  3:已完成
            applyOrder.setDemandDep(demanddep);
            applyOrder.setDemandDepId(demanddepId);
            applyOrder.setReceiptDate(receiptdate);
            applyOrder.setRemark(remark);
            applyOrder.setTypeId(typeid);
            applyOrder.setAmountPrice(amountPrice);
            applyOrder.setAttr2(applyphone);
            applyOrder.setMeetingName(meeting_name);
            applyOrder.setMeetingPersonNumber(meeting_person_number);
            tapplyOrderMapper.addApplyOrder(applyOrder);

            //先获取审批人
            //获取下一节点审批人
            map.put("orderid", orderid);
            map.put("status",applyOrder.getStatus());
            Map<String,Object> next_deal_user_map = getNextApprovePerson(map);
            //2、要向t_apply_order_approvel表里插入审批记录
            ApplyOrderApproval applyOrderApproval = new ApplyOrderApproval();
            applyOrderApproval.setId(UUID.randomUUID().toString().replace("-", ""));
            applyOrderApproval.setOrderId(orderid);
            applyOrderApproval.setStatus("0");
            applyOrderApproval.setCreateTime(operateDate);
            applyOrderApproval.setDealUserId(next_deal_user_map.get("next_deal_user_id").toString());
            applyOrderApproval.setDealUserName(next_deal_user_map.get("next_deal_user_name").toString());
            applyOrderApproval.setStep("0");
            tapplyOrderMapper.addApproval(applyOrderApproval);

            //向物品条目表里插入多条物品
            for (Map<String, String> good : goods) {
                ApplyOrderGood applyOrderGood = new ApplyOrderGood();
                applyOrderGood.setRelateOrderId(orderid);
                applyOrderGood.setGoodId(good.get("goodid"));
                applyOrderGood.setGoodNo(good.get("goodno"));
                applyOrderGood.setGoodName(good.get("goodname"));
                applyOrderGood.setGoodAmount(Integer.parseInt(good.get("goodamount")));
                applyOrderGood.setSpecs(good.get("specs"));
                applyOrderGood.setBrand(good.get("brand"));
                applyOrderGood.setStoreNo(good.get("storeno"));
                applyOrderGood.setUnitPrice(good.get("unitprice"));
                applyOrderGood.setUnit(good.get("unit"));
                applyOrderGood.setPictureUrl(good.get("pictureurl"));
                applyOrderGood.setLocation(good.get("location"));
                tapplyOrderMapper.addGood(applyOrderGood);

                String goodId = good.get("goodid") == null ? "" : good.get("goodid");
                Integer goodamount = good.get("goodamount") == null ? 0 : Integer.parseInt(good.get("goodamount")) ;
                //获取可用库存
                Map store = tapplyOrderMapper.getNowStore(goodId);
                Integer usableamount = (Integer) store.get("usableamount");
                usableamount = usableamount - goodamount;
                if (usableamount < 0) {
                    throw new Exception();
                }
            }
            return "操作成功！";
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
            return "申请失败！";
        }
    }

    /**
     * 审批人查询审批列表
     * @param map
     * {"pageNum":"","pageSize":"","dealUserId":"","typeid":"","depId":""}
     * @return
     */
    @Override
    public Object getApprovalList(Map<String, Object> map) {
        Map resultMap = new LinkedHashMap();
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String dealUserId = map.get("dealUserId").toString();
        String typeid = map.get("typeid").toString();
        String depId = map.get("depId") == null ? "" : map.get("depId").toString();
        String ids = departmentService.getLowerIds(depId);
        Integer totalSize = tapplyOrderMapper.getApprovalListCnt(dealUserId, typeid,ids);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map<String, Object>> approvalList = tapplyOrderMapper.getApprovalList(dealUserId, typeid, ids, startIndex, pageSize);
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
                        Map<String, Object> nowMoney = (Map) getRemainDepMoney(checkMoneyMap).getData();
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
        Integer data = tapplyOrderMapper.getData(dealUserId, typeid, "0", depId); //待审批数量
        resultMap.put("page",pageBean);
        resultMap.put("dsp",data);
        return resultMap;
    }

    /**
     * 审批人审批
     * @param map
     * {"orderid":"","typeid":"","status":"","step":"","index":"","demanddepid":"","remark":""}
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object approveApply(Map<String,Object> map) {
        String orderid = map.get("orderid") == null ? "" : map.get("orderid").toString();
        String typeid = map.get("typeid") == null ? "" : map.get("typeid").toString();
        String status = map.get("status") == null ? "" : map.get("status").toString();
        String step = map.get("step") == null ? "" : map.get("step").toString();
        String demanddepid = map.get("demanddepid") == null ? "" : map.get("demanddepid").toString();
        String remark = map.get("remark") == null ? "" : map.get("remark").toString();
        String index = map.get("index") == null ? "" : map.get("index").toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String operateDate = df.format(new Date());//操作时间
        if(index.equals("0")){  //拒绝
            status = "-1";
            tapplyOrderMapper.approveApply(status,remark,orderid,step);
        }
        if (index.equals("1")){ //通过
            Map nextApprovePerson = getNextApprovePerson(map);
            status = nextApprovePerson.get("index") == null ? "" : nextApprovePerson.get("index").toString();
            //更新主表信息
            tapplyOrderMapper.approveApply(status,remark,orderid,step);

            if(!status.equals("11")){       //当status为11的时候，没有下个节点
                map.put("status",status);
                Map nextApprovePerson1 = getNextApprovePerson(map);
                //添加新节点
                ApplyOrderApproval applyOrderApproval = new ApplyOrderApproval();
                applyOrderApproval.setId(UUID.randomUUID().toString().replace("-", ""));
                applyOrderApproval.setOrderId(orderid);
                applyOrderApproval.setStatus("0");
                applyOrderApproval.setCreateTime(operateDate);
                applyOrderApproval.setDealUserId(nextApprovePerson1.get("next_deal_user_id").toString());
                applyOrderApproval.setDealUserName(nextApprovePerson1.get("next_deal_user_name").toString());
                applyOrderApproval.setStep(String.valueOf(Integer.valueOf(step)+1));
                tapplyOrderMapper.addApproval(applyOrderApproval);

                if (status.equals("10")){       //审批通过，修改存库信息
                    //减去库存数量
                    List<Map<String, Object>> goodById = tapplyOrderMapper.getGoodById(orderid);
                    for (Map<String, Object> stringObjectMap : goodById) {
                        String goodId = stringObjectMap.get("goodid") == null ? "" : stringObjectMap.get("goodid").toString();
                        Integer goodamount = stringObjectMap.get("goodamount") == null ? 0 : Integer.parseInt(stringObjectMap.get("goodamount").toString()) ;
                        Map store = tapplyOrderMapper.getNowStore(goodId);
                        Integer usableamount = store.get("usableamount") == null ? 0 : (Integer) store.get("usableamount"); //可用库存
                        Integer outstore = store.get("outstore") == null ? 0 : (Integer) store.get("outstore"); //待出库数量

                        usableamount = usableamount - goodamount;   //可用库存减少
                        outstore = outstore + goodamount;   //待出库相加
                        Tstock tstock = new Tstock();
                        tstock.setId(goodId);
                        tstock.setUsableaMount(usableamount.toString());
                        tstock.setOutStore(outstore.toString());
                        tapplyOrderMapper.updatePass(tstock);
                    }
                }
            }else {
                if( typeid.equals("006")){      //预算申请，审批结束后，追加预算加到部门预算中
                    Map amount = tdeptBudgetMapper.getAmount(orderid);
                    BigDecimal amountprice = new BigDecimal((amount.get("amountprice") == null || amount.get("amountprice").equals("")) ? "0" : amount.get("amountprice").toString());
                    String isPaper = amount.get("isPaper") == null ? "" : amount.get("isPaper").toString();
                    String begindate = (amount.get("begindate") == null || amount.get("begindate").equals(""))? "0000-00-00" : amount.get("begindate").toString();
                    tdeptBudgetMapper.updateAmount(demanddepid,begindate,amountprice,isPaper);
                }
            }
        }
        return "操作成功";
    }


    /**
     * 发放库存的列表
     * @param map
     * {"pageNum":"","pageSize":"","typeid":"","depId":""}
     * @return
     */
    /*@Override
    public Object getPassList(Map<String, Object> map) {
        Map resultMap = new LinkedHashMap();
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String typeid = map.get("typeid").toString();
        String depId = map.get("depId") == null ? "" : map.get("depId").toString();
        Integer totalSize = tapplyOrderMapper.getPassListCnt(typeid, depId);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> passList = tapplyOrderMapper.getPassList(typeid, depId, startIndex, pageSize);
            for (Map<String, Object> stringObjectMap : passList) {
                String orderId = stringObjectMap.get("orderid") == null ? "" : stringObjectMap.get("orderid").toString();
                List<Map<String, Object>> goods = tapplyOrderMapper.getGoodById(orderId);
                stringObjectMap.put("goods",goods);
                if(typeid.equals("003") || typeid.equals("005")){   //003:办公文具  005:打印纸
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
                    Map<String, Object> nowMoney = (Map) getRemainDepMoney(checkMoneyMap).getData();
                    //double MAX_MONEY = Double.valueOf(nowMoney.get("MAX_MONEY").toString());//剩余预算金额
                    if(nowMoney != null && nowMoney.size() != 0){
                        stringObjectMap.put("MAX_MONEY",nowMoney.get("MAX_MONEY"));

                    }else {
                        stringObjectMap.put("MAX_MONEY","0");
                    }
                    stringObjectMap.put("totalPrice",totalPrice);
                }
            }
            pageBean.setList(passList);
        }
        Integer data = tapplyOrderMapper.getData(null, typeid, "1", depId); //待审批数量
        resultMap.put("page",pageBean);
        resultMap.put("dff",data);
        return resultMap;
    }*/

    /**
     * 发放库存
     * @param map
     * {"orderId":"","deal_user_id":"","deal_user_name":""}
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object grantGoods(Map<String, Object> map) {
        String orderid = map.get("orderid") == null ? "" : map.get("orderid").toString();
        String dealUserId = map.get("deal_user_id") == null ? "" : map.get("deal_user_id").toString();
        String dealUserName = map.get("deal_user_name") == null ? "" : map.get("deal_user_name").toString();
        //修改主表状态
        tapplyOrderMapper.updateState(orderid);

        //获取发放记录id
        String id = tapplyOrderMapper.getRecordId(orderid);
        //更新操作人员信息和操作时间
        ApplyOrderApproval applyOrderApproval = new ApplyOrderApproval();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        applyOrderApproval.setDealTime(sdf.format(new Date()));
        applyOrderApproval.setDealUserId(dealUserId);
        applyOrderApproval.setDealUserName(dealUserName);
        applyOrderApproval.setId(id);
        tapplyOrderMapper.updatePersonInfo(applyOrderApproval);

        //减去库存数量
        List<Map<String, Object>> goodById = tapplyOrderMapper.getGoodById(orderid);
        for (Map<String, Object> stringObjectMap : goodById) {
            String goodId = stringObjectMap.get("goodid") == null ? "" : stringObjectMap.get("goodid").toString();
            Integer goodamount = stringObjectMap.get("goodamount") == null ? 0 : Integer.parseInt(stringObjectMap.get("goodamount").toString()) ;
            Map store = tapplyOrderMapper.getNowStore(goodId);
            //Integer usableamount = (Integer) store.get("usableamount");
            Integer outstore = store.get("outstore") == null ? 0 : (Integer) store.get("outstore");
            Integer nowstore = store.get("nowstore") == null ? 0 : (Integer) store.get("nowstore");
            Double unitprice = store.get("unitprice") == null ? 0.0 : Double.parseDouble(store.get("unitprice").toString());

            //usableamount = usableamount - goodamount;
            outstore = outstore - goodamount;
            nowstore = nowstore - goodamount;
            String allMoney = String.valueOf(unitprice * nowstore);
            Tstock tstock = new Tstock();
            //tstock.setUsableaMount(usableamount.toString());
            tstock.setId(goodId);
            tstock.setOutStore(outstore.toString());
            tstock.setNowStore(nowstore.toString());
            tstock.setAllmoney(allMoney);
            tapplyOrderMapper.updateNowStore(tstock);
        }
        return "发放成功";
    }

    /**
     * 下拉框
     * @param map
     * {"typeid":""}
     * @return
     */
    @Override
    public Object dropDownBox(Map<String, Object> map) {
        String typeid = map.get("typeid").toString();
        Map resultMap = new LinkedHashMap();
        //物品
        List goodFromStock = tapplyOrderMapper.getGoodFromStock(typeid);
        resultMap.put("goods",goodFromStock);
        return resultMap;
    }

    /**
     * 通过id查询物品详情
     * @param map
     * {"id":""}
     * @return
     */
    @Override
    public Object getGoodInfo(Map<String, Object> map) {
        String id = map.get("id").toString();
        Map goodInfo = tapplyOrderMapper.getGoodInfo(id);
        goodInfo.put("unitprice",goodInfo.get("unitprice").toString());
        return goodInfo;
    }


    //获取当前部门已审批的金额和最高金额
    public ResultMessage getRemainDepMoney(Map<String, Object> map) {
        ResultMessage resultMessage = new ResultMessage();
        try {
            //当前部门
            String deptId = map.get("deptId") == null ? "" : map.get("deptId").toString();
            //0：办公用品   1：打印纸
            String IS_PAPER = map.get("IS_PAPER") == null ? "0" : map.get("IS_PAPER").toString();

            List<Map<String, Object>> nowYearMoneyData = tapplyOrderMapper.nowYearMoneyData(deptId, IS_PAPER);
            if (nowYearMoneyData.size() <= 0) {
                resultMessage.setMessage("未找到当前年度预算，请联系相关人员设置预算！");
                return resultMessage;
            }
            /*管理员获取可用余额*/
            List<Map<String, Object>> adminUsedData = tapplyOrderMapper.adminUsedData(deptId, IS_PAPER.equals("0") ? "003" : "005", nowYearMoneyData.get(0).get("BEGIN_DATE").toString(), nowYearMoneyData.get(0).get("END_DATE").toString());
            Map<String, Object> result = new HashMap<>();
            String MAX_MONEY = MoneyUtil.moneyAdd(nowYearMoneyData.get(0).get("MAX_MONEY").toString(), nowYearMoneyData.get(0).get("ADDED_MONEY").toString());
            String adminleftmoney = MoneyUtil.moneySub(MAX_MONEY, adminUsedData.get(0).get("PRICE") == null ? "0" : adminUsedData.get(0).get("PRICE").toString());

            /*普通用户获取可用余额*/
            List<Map<String, Object>> normalUsedData = tapplyOrderMapper.normalUsedData(deptId, IS_PAPER.equals("0") ? "003" : "005", nowYearMoneyData.get(0).get("BEGIN_DATE").toString(), nowYearMoneyData.get(0).get("END_DATE").toString());

            String normalleftmoney = MoneyUtil.moneySub(MAX_MONEY, normalUsedData.get(0).get("PRICE") == null ? "0" : normalUsedData.get(0).get("PRICE").toString());

            result.put("allmoney", adminUsedData.get(0).get("PRICE"));
            result.put("MAX_MONEY", MAX_MONEY);
            result.put("adminleftmoney", adminleftmoney);
            result.put("normalleftmoney", normalleftmoney);
            result.put("THEYEAR", nowYearMoneyData.get(0).get("THEYEAR"));
            result.put("BEGIN_DATE", nowYearMoneyData.get(0).get("BEGIN_DATE"));
            result.put("END_DATE", nowYearMoneyData.get(0).get("END_DATE"));
            resultMessage.setData(result);
            return resultMessage;
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage.setMessage("设置失败！");
            return resultMessage;
        }
    }


    /*获取办公物资申请下一节点审批人*/
    public Map getNextApprovePerson(Map<String, Object> map) {
        try {
            String status = map.get("status") == null ? "" : map.get("status").toString();//状态
            String typeid = map.get("typeid") == null ? "" : map.get("typeid").toString();//类型
            String demanddepid = map.get("demanddepid") == null ? "" : map.get("demanddepid").toString();//需求部门id
            String demanddep = map.get("demanddep") == null ? "" : map.get("demanddep").toString();//需求部门
            String beginpersonid = map.get("beginpersonid") == null ? "" : map.get("beginpersonid").toString();//单据提交人ID
            String beginpersonname = map.get("beginpersonname") == null ? "" : map.get("beginpersonname").toString();//单据提交人Name
            //String nowUserId = map.get("userId") == null ? "" : map.get("userId").toString();//处理人ID
            //String nowUserName = map.get("userName") == null ? "" : map.get("userName").toString();//处理人name
            String roleType = "";
            String index = "";  //状态
            if (typeid.equals("002")) {//办公设备、办公家具
                if (status.equals("0")) {//部门负责人  状态改为1   step++   && nowRoleType.contains("bgsadmin")
                    roleType = "bmzr";
                    index = "1";
                }
                if (status.equals("1")) {//后勤部审核   状态改为2   step++    && nowRoleType.contains("hqadmin")
                    roleType = "hqadmin";
                    index = "10";
                }
                if (status.equals("10")) { //库存管理员  状态改为10   && userId.contains(beginpersonid)
                    roleType = "kcadmin";
                    index = "11";
                }
            } else if (typeid.equals("003") || typeid.equals("005")) {//办公用品 || 打印纸
                /*if (status.equals("-1")) { //被驳回的单据  状态改为0  处理人是自己   step++
                    roleType = "";
                }*/
                if (status.equals("0")) { //部门负责人审核 状态改为1   step++
                    roleType = "bmzr";
                    index = "10";
                }
                if (status.equals("10")) {//库存管理员审核 通过状态改为10  预算超支状态改为2   step++
                    roleType = "kcadmin";
                    index = "11";
                }
            } else if (typeid.equals("004")) {//会议用品
                if (status.equals("0")) {//部门负责人审核  状态改为1   step++   && nowRoleType.contains("bgsadmin")
                    roleType = "bmzr";
                    index = "1";
                }
                if (status.equals("1")) {//办公室审核  状态改为2  step++   && nowRoleType.contains("bgsadmin")
                    roleType = "bgsadmin";
                    index = "2";
                }
                if (status.equals("2")) {//后勤部审核   状态改为3   step++    && nowRoleType.contains("hqadmin")
                    roleType = "hqadmin";
                    index = "10";
                }
                /*if (status.equals("2")) { //单据提交人填写办公用品领用单  状态改为3   && userId.contains(beginpersonid)
                    roleType = "";
                }*/
                if (status.equals("10")) {//库存管理员审核   状态改为10    结束   step++    && nowRoleType.contains("kcadmin")
                    roleType = "kcadmin";
                    index = "11";
                }
            } else if (typeid.equals("006")) {//额外预算
                //驳回后重新提交
                // ---部门负责人审核
                //---财务部负责人审核
                // ---后勤部负责人审核
                // ---公司分管领导审核
                if (status.equals("0")) {//部门负责人审核  状态改为1   step++   && nowRoleType.contains("bgsadmin")
                    roleType = "bmzr";
                    index = "1";
                }
                if (status.equals("1")) {//财务部负责人审核  状态改为2   step++   && nowRoleType.contains("bgsadmin")
                    roleType = "cwadmin";
                    index = "2";
                }
                if (status.equals("2")) {//后勤部负责人审核   状态改为3   step++    && nowRoleType.contains("hqadmin")
                    roleType = "hqadmin";
                    index = "3";
                }
                if (status.equals("3")) {//公司分管领导审核   状态改为10    结束   step++    && nowRoleType.contains("kcadmin")
                    roleType = "fgadmin";
                    index = "11";
                }
            }
            Map<String, Object> personInfo = new HashMap<>();
            if (roleType.equals("")) {
                personInfo.put("next_deal_user_id", beginpersonid);
                personInfo.put("next_deal_user_name", beginpersonname);
                personInfo.put("next_deal_dept_id", demanddepid);
                personInfo.put("next_deal_dept_name", demanddep);
                personInfo.put("index",index);
                return personInfo;
            } else {
                String[] roles = roleType.split(",");
                StringBuffer sql = new StringBuffer("");
                //sql.append("SELECT USER_ID,USER_NAME,DEPT_ID,DEPT_NAME FROM t_user WHERE ");
                if (roles.length == 1) {
                    //sql.append("roleid LIKE :roleType ");
                    if (roles[0].equals("bmzr")) {
                        sql.append("(roleid LIKE '%" + roleType + "%' AND DEPT_ID = '" + demanddepid + "' )");
                    } else {
                        sql.append("roleid LIKE '%" + roleType + "%'");
                    }
                } else {
                    String orCondition = "";
                    for (int i = 0; i < roles.length; i++) {
                        if (roles[i].equals("bmzr")) {
                            orCondition = orCondition + " (roleid LIKE '%" + roleType + "%' AND DEPT_ID = '" + demanddepid +"') OR ";
                        } else {
                            orCondition = orCondition + " roleid LIKE '%" + roleType + "%' OR ";
                        }
                    }
                    orCondition = orCondition.substring(0, orCondition.length() - 3);
                    sql.append(orCondition);
                }

                List<Map> data = tapplyOrderMapper.getNextPerson(sql.toString());
                List<String> userId = new ArrayList<>();
                List<String> userName = new ArrayList<>();
                List<String> deptId = new ArrayList<>();
                List<String> deptName = new ArrayList<>();
                for (Map<String, Object> item : data) {
                    String USER_ID = item.get("USER_ID")== null?"":item.get("USER_ID").toString();
                    String USER_NAME = item.get("USER_NAME")== null?"":item.get("USER_NAME").toString();
                    String DEPT_ID = item.get("DEPT_ID")== null?"":item.get("DEPT_ID").toString();
                    String DEPT_NAME = item.get("DEPT_NAME")== null?"":item.get("DEPT_NAME").toString();
                    userId.add(USER_ID);
                    userName.add(USER_NAME);
                    deptId.add(DEPT_ID);
                    deptName.add(DEPT_NAME);

                    //最后要在消息表插入一条消息   status==1表示提交
                    /*StringBuffer newsSql = new StringBuffer("");
                    newsSql.append("INSERT INTO t_newsinfo (OBJ_ID, NEWS_ID, NEWS_TITLE, NEWS_CONTENT, NEWS_TYPE, CREATE_USER_ID, CREAT_PERSON, CREAT_TIME, REMARK, MSG_TYPE, MSG_READ, RECEPT_USER_ID, RECEPT_USER_NAME, DEPT_ID, DEPT_NAME,MSG_ISORDINARY) ");
                    newsSql.append("VALUES (:OBJ_ID, :NEWS_ID, :NEWS_TITLE, :NEWS_CONTENT, :NEWS_TYPE, :CREATE_USER_ID, :CREAT_PERSON, :CREAT_TIME, :REMARK, :MSG_TYPE, :MSG_READ, :RECEPT_USER_ID, :RECEPT_USER_NAME, :DEPT_ID, :DEPT_NAME,:MSG_ISORDINARY) ");
                    Query newsInsert = entityManager.createNativeQuery(newsSql.toString());
                    newsInsert.setParameter("OBJ_ID" , UUID.randomUUID().toString().replace("-",""));
                    newsInsert.setParameter("NEWS_ID", map.get("orderid"));
                    String applyType = "";
                    String menuType = "";
                    if (typeid.equals("002")){
                        applyType = "办公设备申请";
                        menuType = "C002";
                    }else if(typeid.equals("003") || typeid.equals("005")) {
                        applyType = "办公用品申请";
                        menuType = "C003";
                    } else if(typeid.equals("004")) {
                        applyType = "会议用品申请";
                        menuType = "C004";
                    } else if(typeid.equals("006")) {
                        applyType = "预算申请";
                        menuType = "C006";
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowOperateTime = sdf.format(new Date());
                    newsInsert.setParameter("NEWS_TITLE", "您有一条待处理"+applyType+"申请，单号："+map.get("orderid"));
                    newsInsert.setParameter("NEWS_CONTENT", nowUserName+"于"+nowOperateTime+"提交给您的"+applyType+"的单据需要处理！");
                    newsInsert.setParameter("NEWS_TYPE", menuType);//需要根据typeid判断
                    newsInsert.setParameter("CREATE_USER_ID",beginpersonid);
                    newsInsert.setParameter("CREAT_PERSON", beginpersonname);
                    newsInsert.setParameter("CREAT_TIME", nowOperateTime);
                    newsInsert.setParameter("REMARK", map.get("remark")==null?"":map.get("remark").toString());
                    //0:物资申请   1：物资出库   2：计划申请   3：计划入库
                    newsInsert.setParameter("MSG_TYPE", "0");
                    newsInsert.setParameter("MSG_READ", 0);
                    newsInsert.setParameter("RECEPT_USER_ID", USER_ID);
                    newsInsert.setParameter("RECEPT_USER_NAME", USER_NAME);
                    newsInsert.setParameter("DEPT_ID", DEPT_ID);
                    newsInsert.setParameter("DEPT_NAME", DEPT_NAME);
                    newsInsert.setParameter("MSG_ISORDINARY", "1");
                    newsInsert.executeUpdate();*/
                }
                personInfo.put("next_deal_user_id", StringUtils.join(userId,","));
                personInfo.put("next_deal_user_name", StringUtils.join(userName,","));
                personInfo.put("next_deal_dept_id", deptId);
                personInfo.put("next_deal_dept_name", deptName);
                personInfo.put("index",index);
            }
            return personInfo;
        } catch (Exception e) {
            Map failMap = new HashMap();
            failMap.put("next_deal_user_id", "false");
            failMap.put("next_deal_user_name", "false");
            return failMap;
        }
    }



}




