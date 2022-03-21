package com.officeServices.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.officeServices.entity.*;
import com.officeServices.mapper.TapplyOrderMapper;
import com.officeServices.mapper.TdeptBudgetMapper;
import com.officeServices.mapper.TstockMapper;
import com.officeServices.service.DocumentnumberService;
import com.officeServices.service.TstockService;
import com.officeServices.utils.MySFTP;
import com.officeServices.utils.PageBean;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TstockServiceImpl implements TstockService {

    @Autowired
    private TstockMapper tstockMapper;

    @Autowired
    private DocumentnumberService documentnumberService;

    @Autowired
    private TapplyOrderMapper tapplyOrderMapper;

    @Autowired
    private TdeptBudgetMapper tdeptBudgetMapper;

    //---------------------------------------------库存查询-------------------------------------------------------------

    /**
     * 库存查询
     * @param map
     * {"pageNum":"","pageSize":"","goodName":"","type":""}
     * @return
     */
    @Override
    public Object findTstock(Map<String, Object> map) {
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String goodName = map.get("goodName") == null ? "" : map.get("goodName").toString();
        String type = map.get("type") == null ? "" : map.get("type").toString();
        Integer goodCnt = tstockMapper.findGoodCnt(goodName,type);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(goodCnt);
        if (goodCnt == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> good = tstockMapper.findGood(goodName, type, startIndex, pageSize);
            for (Map map1 : good) {
                Object pictureUrl = map1.get("pictureurl");
                String picture = MySFTP.getPicture(pictureUrl);
                map1.put("pictureurl",picture);

                String goodno = map1.get("goodno") == null ? "" : map1.get("goodno").toString();
                Map history = tdeptBudgetMapper.getHistory(goodno);
                Integer sum = Integer.parseInt(history.get("sum").toString());
                Integer cnt = Integer.parseInt(history.get("cnt").toString());
                Integer historyPj = 0;
                if (cnt != 0) {
                    historyPj = sum / cnt;
                }
                map1.put("history",historyPj);
            }
            pageBean.setList(good);
        }
        return pageBean;
    }

    /**
     * 新建物品
     * @param tstock
     * {"goodName":"","specs":"","brand":"","storeNo":"","unitPrice":"","unit":"","pictureurl":"","expectcount":"","barCode":"","parenttypeId":""}
     * @return
     */
    @Override
    public Object addGood(Tstock tstock) {
        tstock.setId(UUID.randomUUID().toString().replace("-", ""));
        tstock.setGoodNo(documentnumberService.getOrderNo("WZ"));
        tstock.setIsDel("0");
        String pictureurl = tstock.getPictureurl();
        if (pictureurl != null && !pictureurl.equals("")){
            byte[] inputStream = MySFTP.getInputStream(pictureurl);
            tstock.setPicture(inputStream);
        }
        tstockMapper.addGood(tstock);
        return "新建成功";
    }

    /**
     * 修改物品
     * @param tstock
     * {"id":"","goodName":"","specs":"","brand":"","storeNo":"","unitPrice":"","unit":"","pictureurl":"","expectcount":"","barCode":"","parenttypeId":""}
     * @return
     */
    @Override
    public Object updateGood(Tstock tstock) {
        String pictureurl = tstock.getPictureurl();
        if (pictureurl != null && !pictureurl.equals("")){
            byte[] inputStream = MySFTP.getInputStream(pictureurl);
            tstock.setPicture(inputStream);
        }
        tstockMapper.updateGood(tstock);
        return "修改成功";
    }

    /**
     * 库存查询-下拉框
     *
     * @return
     */
    @Override
    public Object getTypeId() {
        Map resultMap = new LinkedHashMap();
        //物品类别
        List goodType = new ArrayList();
        goodType.add(JSONObject.parseObject("{'id':'002','text':'办公设备'}"));
        goodType.add(JSONObject.parseObject("{'id':'003','text':'办公文具'}"));
        goodType.add(JSONObject.parseObject("{'id':'005','text':'打印纸'}"));
        resultMap.put("goodType", goodType);
        return resultMap;
    }

    //---------------------------------------------采购计划-------------------------------------------------------------

    /**
     * 发起采购申请
     * @param map
     * {"beginPersonId":"","beginPersonName":"","beginPersonPhone":"","beginDepId":"","beginDepName":"","planTime":"",
     *  "endPersonId":"","endPersonName":"","remark":"","status":"","allMoney":"","allaMount":"","title":"",
     *  "goods":[{"goodId":"","goodNo":"","goodName":"","specs":"","brand":"","storeNo":"","realUnitprice":"","realAmount":"",
     *  "unitPrice":"","unit":"","pictureUrl":"","expectCount":"","location":""}]}
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object purchaseApply(Map<String, Object> map) {
        String applyNo = documentnumberService.getOrderNo("BG");//办公服务物资申请号
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String beginDate = df.format(new Date());//操作时间
        String beginPersonId = map.get("beginPersonId") == null ? "" : map.get("beginPersonId").toString();//申请人id
        String beginPersonName = map.get("beginPersonName") == null ? "" : map.get("beginPersonName").toString();//申请人名称
        String beginPersonPhone = map.get("beginPersonPhone") == null ? "" : map.get("beginPersonPhone").toString();//联系方式
        String beginDepId = map.get("beginDepId") == null ? "" : map.get("beginDepId").toString();//申请部门id
        String beginDepName = map.get("beginDepName") == null ? "" : map.get("beginDepName").toString();//申请部门名称
        String planTime = map.get("planTime") == null ? "" : map.get("planTime").toString();//采购日期
        String endPersonId = map.get("endPersonId") == null ? "" : map.get("endPersonId").toString();//审批人id
        String endPersonName = map.get("endPersonName") == null ? "" : map.get("endPersonName").toString();//审批人名称
        String remark = map.get("remark") == null ? "" : map.get("remark").toString();//采购理由
        String status = map.get("status") == null ? "" : map.get("status").toString();//审批状态 0：保存 1：提交 2：已审批 3：已驳回
        String allMoney = (map.get("allMoney") == null || map.get("allMoney").equals("") ) ? "0" : map.get("allMoney").toString();//总金额
        String allaMount = (map.get("allaMount") == null || map.get("allaMount").equals("")) ? "0" : map.get("allaMount").toString();//总数量
        String title = map.get("title") == null ? "" : map.get("title").toString();//采购计划名称

        //这是申请中的物品条目具体信息
        List<Map<String, String>> goods = (ArrayList<Map<String, String>>) map.get("goods");

        TstockPlan tstockPlan = new TstockPlan();
        tstockPlan.setId(UUID.randomUUID().toString().replace("-", ""));
        tstockPlan.setApplyNo(applyNo);
        tstockPlan.setBeginPersonId(beginPersonId);
        tstockPlan.setBeginPersonName(beginPersonName);
        tstockPlan.setBeginPersonPhone(beginPersonPhone);
        tstockPlan.setBeginDate(beginDate);
        tstockPlan.setBeginDepId(beginDepId);
        tstockPlan.setBeginDepName(beginDepName);
        tstockPlan.setPlanTime(planTime);
        tstockPlan.setEndPersonId(endPersonId);
        tstockPlan.setEndPersonName(endPersonName);
        tstockPlan.setRemark(remark);
        tstockPlan.setStatus(status);
        tstockPlan.setAllMoney(allMoney);
        tstockPlan.setAllaMount(allaMount);
        tstockPlan.setTitle(title);
        //1.向t_stock_plan添加记录
        tstockMapper.insertPlan(tstockPlan);

        //添加物品明细
        for (Map<String, String> good : goods) {
            TstockPlanDetail tstockPlanDetail = new TstockPlanDetail();
            tstockPlanDetail.setId(UUID.randomUUID().toString().replace("-", ""));
            tstockPlanDetail.setApplyNo(applyNo);
            tstockPlanDetail.setGoodId(good.get("goodId"));
            tstockPlanDetail.setGoodNo(good.get("goodNo"));
            tstockPlanDetail.setGoodName(good.get("goodName"));
            tstockPlanDetail.setSpecs(good.get("specs"));
            tstockPlanDetail.setBrand(good.get("brand"));
            tstockPlanDetail.setStoreNo(good.get("storeNo"));
            tstockPlanDetail.setUnitPrice((good.get("unitPrice") == null || good.get("unitPrice").equals("")) ? "0" : good.get("unitPrice"));
            //tstockPlanDetail.setAmount(good.get("amount"));
            tstockPlanDetail.setUnit(good.get("unit"));
            tstockPlanDetail.setPictureUrl(good.get("pictureUrl"));
            tstockPlanDetail.setExpectCount(good.get("expectCount"));
            tstockPlanDetail.setLocation(good.get("location"));
            tstockPlanDetail.setRealUnitprice((good.get("realUnitprice") == null || good.get("realUnitprice").equals("") ? "0" : good.get("realUnitprice")));
            tstockPlanDetail.setRealAmount((good.get("realAmount") == null || good.get("realAmount").equals("")) ? "0" : good.get("realAmount"));
            //tstockPlanDetail.setRealAllmoney(good.get("realAllmoney"));
            //向t_stock_plan_detail添加记录
            tstockMapper.insertPlanDetail(tstockPlanDetail);
        }
        return "操作成功";
    }

    /**
     *发起采购申请--下拉框
     * @return
     */
    @Override
    public Object getData() {
        Map resultMap = new LinkedHashMap();
        List<Map> data = tstockMapper.getData();
        for (Map datum : data) {
            String goodname = datum.get("goodname").toString();
            String iswarn = datum.get("iswarn").toString();
            goodname = goodname + "  " + iswarn;
            datum.put("goodname",goodname);
            datum.remove("iswarn");
        }
        resultMap.put("goods",data);
        return resultMap;
    }

    /**
     * 用户查询提交的申请
     * @param map
     * {"pageNum":"","pageSize":"","beginPersonId":"","status":""}
     * @return
     */
    @Override
    public Object getSave(Map<String, Object> map) {
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String beginPersonId = map.get("beginPersonId").toString();
        String status = map.get("status") == null ? "" : map.get("status").toString();
        Integer totalSize = tstockMapper.getListByUserCnt(beginPersonId, status);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> listByUser = tstockMapper.getListByUser(beginPersonId, status, startIndex, pageSize);
            for (Map map1 : listByUser) {
                String applyNo = map1.get("apply_no") == null ? "" : map1.get("apply_no").toString();
                List<Map> goodsByNo = tstockMapper.getGoodsByNo(applyNo);
                map1.put("goods", goodsByNo);
            }
            pageBean.setList(listByUser);
        }
        return pageBean;
    }

    /**
     * 编辑保存的申请
     * @param map
     * {"applyNo":"","planTime":"","remark":"","status":"","allMoney":"","allaMount":"","title":"",
     *  "goods":[{"goodId":"","goodNo":"","goodName":"","specs":"","brand":"","storeNo":"","unitPrice":"","amount":"",
     *  "unit":"","pictureUrl":"","expectCount":"","location":""}]}
     * @return
     */
    @Override
    public Object updateSave(Map<String, Object> map) {
        return null;
    }

    /**
     * 删除已保存的申请
     * @param tstockPlan
     * {"applyNo":""}
     * @return
     */
    @Override
    public Object deleteSava(TstockPlan tstockPlan) {
        tstockMapper.deleteSave(tstockPlan);
        return "删除成功";
    }


    /**
     * 审批人查询采购列表
     * @param map
     * {"pageNum":"","pageSize":"","endPersonId":"","depId":""}
     * @return
     */
    @Override
    public Object getListBySp(Map<String, Object> map) {
        Map resultMap = new LinkedHashMap();
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String endPersonId = map.get("endPersonId") == null ? "" : map.get("endPersonId").toString();
        String depId = map.get("depId") == null ? "" : map.get("depId").toString();
        Integer totalSize = tstockMapper.getListBySpCnt(endPersonId, depId, "1");
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> listBySp = tstockMapper.getListBySp(endPersonId, depId, startIndex, pageSize);
            for (Map map1 : listBySp) {
                String applyNo = map1.get("apply_no") == null ? "" : map1.get("apply_no").toString();
                List<Map> goodsByNo = tstockMapper.getGoodsByNo(applyNo);
                map1.put("goods", goodsByNo);
            }
            pageBean.setList(listBySp);
        }

        //查询待审批数量
        Integer dsp = tstockMapper.getListBySpCnt(endPersonId, depId, "1");
        resultMap.put("page", pageBean);
        resultMap.put("dsp", dsp);
        return resultMap;
    }

    /**
     * 审批人审批
     * @param tstockPlan
     * {"applyNo":"","approvelRemark":"","status":""}
     * @return
     */
    @Override
    public Object updateBySp(TstockPlan tstockPlan) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDate = sdf.format(new Date());
        tstockPlan.setEndDate(endDate);
        tstockMapper.updateBySp(tstockPlan);
        return "操作成功";
    }

    //------------------------------------------------入库管理----------------------------------------------

    /**
     * 查询待入库列表
     * @param map
     * {"pageNum":"","pageSize":"","depId":""}
     * @return
     */
    @Override
    public Object getDrkList(Map<String, Object> map) {
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String depId = map.get("depId") == null ? "" : map.get("depId").toString();
        Integer totalSize = tstockMapper.getDckListCnt(depId);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> dckList = tstockMapper.getDckList(depId, startIndex, pageSize);
            for (Map map1 : dckList) {
                Integer real_amount = map1.get("real_amount") == null ? 0 : Integer.parseInt(map1.get("real_amount").toString());
                String applyNo = map1.get("apply_no") == null ? "" : map1.get("apply_no").toString();
                String goodId = map1.get("goodid") == null ? "" : map1.get("goodid").toString();
                //入库情况
                List<Map> maps = tstockMapper.selectRcRecord(applyNo, goodId,"0");
                map1.put("rc",maps);
                //上架情况
                List<Map> sj = tstockMapper.selectRcRecord(applyNo, goodId,"1");
                map1.put("sj",sj);
                //实际入库数量
                Integer realRkCnt = tstockMapper.getAmount(applyNo, goodId, "0");
                realRkCnt = realRkCnt == null ? 0 : realRkCnt;
                //已上架数量
                Integer ysjCnt = tstockMapper.getAmount(applyNo, goodId, "1");
                ysjCnt = ysjCnt == null ? 0 : ysjCnt;
                //采购中数量
                Integer cgzCnt = real_amount - realRkCnt;
                //待上架数量
                Integer dsjCnt = realRkCnt - ysjCnt;
                map1.put("rkCnt",realRkCnt);
                map1.put("cgzCnt",cgzCnt);
                map1.put("ysjCnt",ysjCnt);
                map1.put("dsjCnt",dsjCnt);
            }
            pageBean.setList(dckList);
        }
        return pageBean;
    }

    /**
     * 仓库管理员入库
     * @param map
     * {"apply_no":"","operate_person_id":"","operate_person_name":"","remark":"","cgzCnt":""
     * "goodno":"","real_unitprice":"","real_amount":"","arrival_order_id":""}
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object storeManagerInStore(Map<String, Object> map) {
        String applyNo = map.get("apply_no") == null ? "" : map.get("apply_no").toString();  //计划申请单编号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operateData = sdf.format(new Date());
        String operatePersonId = map.get("operate_person_id") == null ? "" : map.get("operate_person_id").toString();
        String operatePersonName = map.get("operate_person_name") == null ? "" : map.get("operate_person_name").toString();
        String remark = map.get("remark") == null ? "" : map.get("remark").toString();
        //采购中的数量
        Integer cgzCnt = (map.get("cgzCnt") == null || map.get("cgzCnt").equals("")) ? 0 : Integer.parseInt(map.get("cgzCnt").toString());
        //库存管理员入库时的实际金额
        BigDecimal real_unitprice = new BigDecimal(map.get("real_unitprice") == null ? "0" : map.get("real_unitprice").toString());
        int real_amount = Integer.valueOf(map.get("real_amount") == null ? "0" : map.get("real_amount").toString());
        BigDecimal real_allmoney = real_unitprice.multiply(new BigDecimal(real_amount));
        if (real_amount > cgzCnt){
            return "入库数不能大于" + cgzCnt;
        }
        //在插入t_stock_plan_record记录表  之前要加个记录 记录此次上操作所有整体信息  包含条目
        String operateHistoryRecordId = UUID.randomUUID().toString().replace("-", "");
        //包含入库时图片  入库时备注  时间    以及类型：是上架记录还是入库记录
        TstockPlanRecordMain tstockPlanRecordMain = new TstockPlanRecordMain();
        tstockPlanRecordMain.setId(operateHistoryRecordId);
        tstockPlanRecordMain.setApplyNo(applyNo);
        tstockPlanRecordMain.setOperatePersonId(operatePersonId);
        tstockPlanRecordMain.setOperatePersonName(operatePersonName);
        tstockPlanRecordMain.setOperateTime(operateData);
        tstockPlanRecordMain.setRemark(remark);
        tstockPlanRecordMain.setIsUp("0");//0：入库记录  1：上架记录
        tstockMapper.insertRecordMain(tstockPlanRecordMain);

        //获取物品信息
        String goodid = map.get("goodid").toString();
        Map goodInfo = tstockMapper.getGoodInfoById(goodid);
        //插入t_stock_plan_record记录表
        TstockPlanRecord tstockPlanRecord = new TstockPlanRecord();
        tstockPlanRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        tstockPlanRecord.setApplyNo(applyNo);
        tstockPlanRecord.setGoodId(goodid);
        tstockPlanRecord.setGoodNo(goodInfo.get("goodno") == null ? "" : goodInfo.get("goodno").toString());
        tstockPlanRecord.setGoodName(goodInfo.get("goodname") == null ? "" : goodInfo.get("goodname").toString());
        tstockPlanRecord.setSpecs(goodInfo.get("specs") == null ? "" : goodInfo.get("specs").toString());
        tstockPlanRecord.setBrand(goodInfo.get("brand") == null ? "" : goodInfo.get("brand").toString());
        tstockPlanRecord.setStoreNo(goodInfo.get("storeno") == null ? "" : goodInfo.get("storeno").toString());
        tstockPlanRecord.setUnitPrice(goodInfo.get("unitprice") == null ? "0" : goodInfo.get("unitprice").toString());
        tstockPlanRecord.setAmount(goodInfo.get("amount") == null ? "0" : goodInfo.get("amount").toString());
        tstockPlanRecord.setAllmoney(goodInfo.get("allmoney") == null ? "0" : goodInfo.get("allmoney").toString());
        tstockPlanRecord.setRealUnitprice(real_unitprice.toString());
        tstockPlanRecord.setRealAmount(String.valueOf(real_amount));
        tstockPlanRecord.setRealAllmoney(real_allmoney.toString());
        tstockPlanRecord.setParenttypeId(goodInfo.get("parenttypeid") == null ? "0" : goodInfo.get("parenttypeid").toString());
        tstockPlanRecord.setUnit(goodInfo.get("unit") == null ? "0" : goodInfo.get("unit").toString());
        tstockPlanRecord.setPictureUrl(goodInfo.get("pictureurl") == null ? "0" : goodInfo.get("pictureurl").toString());
        tstockPlanRecord.setIsDel("0");
        //tstockPlanRecord.setIsWarn();
        tstockPlanRecord.setOperatePersonId(operatePersonId);
        tstockPlanRecord.setOperatePersonName(operatePersonName);
        tstockPlanRecord.setOperateTime(operateData);
        tstockPlanRecord.setIsUp("0");
        tstockPlanRecord.setLocation(remark);
        tstockPlanRecord.setArrivalOrderId(map.get("arrival_order_id") == null ? "0" : map.get("arrival_order_id").toString());
        tstockPlanRecord.setMainId(operateHistoryRecordId);
        tstockMapper.insertRecord(tstockPlanRecord);

        //修改库存信息
        Tstock tstock = new Tstock();
        tstock.setId(goodid);
        tstock.setInStore(String.valueOf(real_amount));
        tstockMapper.updateInstore(tstock);
        return "入库成功";
    }

    /**
     * 仓库管理员上架
     * @param map
     * {"apply_no":"","operate_person_id":"","operate_person_name":"","remark":"","dsjCnt":""
     * "goodno":"","real_unitprice":"","real_amount":"","expectcount":""}
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object upToStock(Map<String, Object> map) {
        String applyNo = map.get("apply_no") == null ? "" : map.get("apply_no").toString();  //计划申请单编号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operateData = sdf.format(new Date());
        String operatePersonId = map.get("operate_person_id") == null ? "" : map.get("operate_person_id").toString();
        String operatePersonName = map.get("operate_person_name") == null ? "" : map.get("operate_person_name").toString();
        String remark = map.get("remark") == null ? "" : map.get("remark").toString();
        String expectcount = map.get("expectcount") == null ? "" : map.get("expectcount").toString();  //预警值
        //待上架数量
        Integer dsjCnt = (map.get("dsjCnt") == null || map.get("dsjCnt").equals("")) ? 0 : Integer.parseInt(map.get("dsjCnt").toString());
        //库存管理员入库时的实际金额
        BigDecimal real_unitprice = new BigDecimal(map.get("real_unitprice") == null ? "0" : map.get("real_unitprice").toString());
        int real_amount = Integer.valueOf(map.get("real_amount") == null ? "0" : map.get("real_amount").toString());
        BigDecimal real_allmoney = real_unitprice.multiply(new BigDecimal(real_amount));
        if (real_amount > dsjCnt){
            return "上架数不能大于" + dsjCnt;
        }

        //在插入t_stock_plan_record记录表  之前要加个记录 记录此次上操作所有整体信息  包含条目
        String operateHistoryRecordId = UUID.randomUUID().toString().replace("-", "");
        //包含入库时图片  入库时备注  时间    以及类型：是上架记录还是入库记录
        TstockPlanRecordMain tstockPlanRecordMain = new TstockPlanRecordMain();
        tstockPlanRecordMain.setId(operateHistoryRecordId);
        tstockPlanRecordMain.setApplyNo(applyNo);
        tstockPlanRecordMain.setOperatePersonId(operatePersonId);
        tstockPlanRecordMain.setOperatePersonName(operatePersonName);
        tstockPlanRecordMain.setOperateTime(operateData);
        tstockPlanRecordMain.setRemark(remark);
        tstockPlanRecordMain.setIsUp("1");//0：入库记录  1：上架记录
        tstockMapper.insertRecordMain(tstockPlanRecordMain);

        //获取物品信息
        String goodid = map.get("goodid").toString();
        Map goodInfo = tstockMapper.getGoodInfoById(goodid);
        //插入t_stock_plan_record记录表
        TstockPlanRecord tstockPlanRecord = new TstockPlanRecord();
        tstockPlanRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        tstockPlanRecord.setApplyNo(applyNo);
        tstockPlanRecord.setGoodId(goodid);
        tstockPlanRecord.setGoodNo(goodInfo.get("goodno") == null ? "" : goodInfo.get("goodno").toString());
        tstockPlanRecord.setGoodName(goodInfo.get("goodname") == null ? "" : goodInfo.get("goodname").toString());
        tstockPlanRecord.setSpecs(goodInfo.get("specs") == null ? "" : goodInfo.get("specs").toString());
        tstockPlanRecord.setBrand(goodInfo.get("brand") == null ? "" : goodInfo.get("brand").toString());
        tstockPlanRecord.setStoreNo(goodInfo.get("storeno") == null ? "" : goodInfo.get("storeno").toString());
        tstockPlanRecord.setUnitPrice(goodInfo.get("unitprice") == null ? "0" : goodInfo.get("unitprice").toString());
        tstockPlanRecord.setAmount(goodInfo.get("amount") == null ? "0" : goodInfo.get("amount").toString());
        tstockPlanRecord.setAllmoney(goodInfo.get("allmoney") == null ? "0" : goodInfo.get("allmoney").toString());
        tstockPlanRecord.setRealUnitprice(real_unitprice.toString());
        tstockPlanRecord.setRealAmount(String.valueOf(real_amount));
        tstockPlanRecord.setRealAllmoney(real_allmoney.toString());
        tstockPlanRecord.setParenttypeId(goodInfo.get("parenttypeid") == null ? "0" : goodInfo.get("parenttypeid").toString());
        tstockPlanRecord.setUnit(goodInfo.get("unit") == null ? "0" : goodInfo.get("unit").toString());
        tstockPlanRecord.setPictureUrl(goodInfo.get("pictureurl") == null ? "0" : goodInfo.get("pictureurl").toString());
        tstockPlanRecord.setIsDel("0");
        //tstockPlanRecord.setIsWarn();
        tstockPlanRecord.setOperatePersonId(operatePersonId);
        tstockPlanRecord.setOperatePersonName(operatePersonName);
        tstockPlanRecord.setOperateTime(operateData);
        tstockPlanRecord.setIsUp("1");
        tstockPlanRecord.setLocation(remark);
        //tstockPlanRecord.setArrivalOrderId(map.get("arrival_order_id") == null ? "0" : map.get("arrival_order_id").toString());
        tstockPlanRecord.setMainId(operateHistoryRecordId);
        tstockMapper.insertRecord(tstockPlanRecord);

        //修改库存表t_stock
        Tstock tstock = new Tstock();
        tstock.setId(goodid);
        tstock.setInStore(String.valueOf(real_amount));
        tstock.setAllmoney(real_allmoney.toString());
        tstock.setExpectcount(expectcount);
        String allmoney = goodInfo.get("allmoney") == null ? "0" : goodInfo.get("allmoney").toString();
        Double nowAllMoney = Double.parseDouble(allmoney) + Double.parseDouble(real_allmoney.toString());
        Integer amount = goodInfo.get("nowstore") == null ? 0 : Integer.parseInt(goodInfo.get("nowstore").toString());
        amount = amount + real_amount;
        Double unitprice = nowAllMoney / amount;
        //创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        tstock.setUnitPrice(numberFormat.format(unitprice));
        tstockMapper.updateStock(tstock);
        return "上架成功";
    }

    //------------------------------------------------出库管理----------------------------------------------

    /**
     * 查询出库管理列表
     * @param map
     * {"pageNum":"","pageSize":"","depId":""}
     * @return
     */
    @Override
    public Object getCkList(Map<String, Object> map) {
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String depId = map.get("depId") == null ? "" : map.get("depId").toString();
        Integer totalSize = tstockMapper.getCkListCnt(depId);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> ckList = tstockMapper.getCkList(depId, startIndex, pageSize);
            for (Map<String, Object> stringObjectMap : ckList) {
                String orderId = stringObjectMap.get("orderid") == null ? "" : stringObjectMap.get("orderid").toString();
                List<Map<String, Object>> goods = tapplyOrderMapper.getGoodById(orderId);
                stringObjectMap.put("goods", goods);
            }
            pageBean.setList(ckList);
        }
        return pageBean;
    }


    //------------------------------------------------库存统计----------------------------------------------

    /**
     * 库存统计
     * @return
     */
    @Override
    public Object inventoryStatistics() {
        Map resultMap = new LinkedHashMap();

        //待入库  待上架  待出库  告警数
        Map<String, Integer> stockInfo = tstockMapper.getStockInfo();
        resultMap.put("stockInfo",stockInfo);

        Map stockCount = new LinkedHashMap();
        //物品库存种类
        Integer stockKind = tstockMapper.getStockKind(null);
        //本月新增物品库存种类
        Integer addStockKindMonth = tstockMapper.getStockKind("1");
        //本年新增物品库存种类
        Integer addStockKindYear = tstockMapper.getStockKind("2");
        //物品库存数量
        Integer stockCnt = tstockMapper.getStockCnt();
        //本月新增物品库存数量
        Integer addStockThisMonth = tstockMapper.addStockCnt("0", "3");
        //本年新增物品库存数量
        Integer addStockThisYear = tstockMapper.addStockCnt("0", "1");
        stockCount.put("stockKind",stockKind);
        stockCount.put("addStockKindMonth",addStockKindMonth);
        stockCount.put("addStockKindYear",addStockKindYear);
        stockCount.put("stockCnt",stockCnt);
        stockCount.put("addStockThisMonth",addStockThisMonth);
        stockCount.put("addStockThisYear",addStockThisYear);
        resultMap.put("stockCount",stockCount);

        //各部门本季度领用情况
        List<Map> receiveInfo = tstockMapper.getReceiveInfo();
        resultMap.put("receiveInfo",receiveInfo);
        return resultMap;
    }

    /**
     * 入库情况
     * @param map   {"index":""}
     * @return
     */
    @Override
    public Object inStock(Map<String, Object> map) {
        //入库情况标记
        String index = map.get("index") == null ? "" : map.get("index").toString(); //1：种类  2：数量

        //入库情况
        Map inStock = new LinkedHashMap();
        Integer thisYear = 0;       //本年
        Integer lastYear = 0;       //去年
        Integer thisMonth = 0;      //本月
        Integer lastYearTm = 0;     //去年本月
        Integer lastMonth = 0;      //上月
        Integer lastYearLm = 0;     //去年上月

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        if (index.equals("1")){     //种类
            thisYear = tstockMapper.addStockKind("0", "1");     //本年入库种类
            lastYear = tstockMapper.addStockKind("0", "2");     //去年入库种类
            thisMonth = tstockMapper.addStockKind("0", "3");    //本月入库种类
            lastYearTm = tstockMapper.addStockKind("0", "4");   //去年本月入库种类
            lastMonth = tstockMapper.addStockKind("0", "5");    //上月入库种类
            lastYearLm = tstockMapper.addStockKind("0", "6");   //去年上月入库种类
        }
        if (index.equals("2")){     //数量
            thisYear = tstockMapper.addStockCnt("0", "1");  //本年入库数量
            lastYear = tstockMapper.addStockCnt("0", "2");  //去年入库数量
            thisMonth = tstockMapper.addStockCnt("0", "3");  //本月入库数量
            lastYearTm = tstockMapper.addStockCnt("0", "4");  //去年本月入库数量
            lastMonth = tstockMapper.addStockCnt("0", "5");  //上月入库数量
            lastYearLm = tstockMapper.addStockCnt("0", "6");  //去年上入库数量
        }
        inStock.put("thisYear",thisYear);
        inStock.put("lastYear",lastYear);
        inStock.put("thisMonthTime",sdf.format(new Date()));    //本月时间
        inStock.put("thisMonth",thisMonth);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR,-1);
        inStock.put("lastYearTmTime",sdf.format(c.getTime()));   //去年本月时间
        inStock.put("lastYearTm",lastYearTm);
        c = Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        inStock.put("lastMonthTime",sdf.format(c.getTime()));   //上月时间
        inStock.put("lastMonth",lastMonth);
        c.add(Calendar.YEAR,-1);
        inStock.put("lastYearLmTime",sdf.format(c.getTime()));  //去年上月时间
        inStock.put("lastYearLm",lastYearLm);
        return inStock;
    }

    /**
     * 出库情况
     * @param map   {"index":""}
     * @return
     */
    @Override
    public Object outStock(Map<String, Object> map) {
        //出库情况标记
        String index = map.get("index") == null ? "" : map.get("index").toString(); //1：种类  2：数量

        //出库情况
        Map outStock = new LinkedHashMap();
        Integer thisYear = 0;       //本年
        Integer lastYear = 0;       //去年
        Integer thisMonth = 0;      //本月
        Integer lastYearTm = 0;     //去年本月
        Integer lastMonth = 0;      //上月
        Integer lastYearLm = 0;     //去年上月

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        if (index.equals("1")){     //种类
            thisYear = tstockMapper.outStockKind("1");     //本年出库种类
            lastYear = tstockMapper.outStockKind( "2");     //去年出库种类
            thisMonth = tstockMapper.outStockKind("3");    //本月出库种类
            lastYearTm = tstockMapper.outStockKind( "4");   //去年本月出库种类
            lastMonth = tstockMapper.outStockKind( "5");    //上月出库种类
            lastYearLm = tstockMapper.outStockKind( "6");   //去年上月出库种类
        }
        if (index.equals("2")){     //数量
            thisYear = tstockMapper.outStockCnt("1");  //本年出库数量
            lastYear = tstockMapper.outStockCnt("2");  //去年出库数量
            thisMonth = tstockMapper.outStockCnt( "3");  //本月出库数量
            lastYearTm = tstockMapper.outStockCnt( "4");  //去年本月出库数量
            lastMonth = tstockMapper.outStockCnt( "5");  //上月出库数量
            lastYearLm = tstockMapper.outStockCnt("6");  //去年上出库数量
        }
        outStock.put("thisYear",thisYear);
        outStock.put("lastYear",lastYear);
        outStock.put("thisMonthTime",sdf.format(new Date()));    //本月时间
        outStock.put("thisMonth",thisMonth);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR,-1);
        outStock.put("lastYearTmTime",sdf.format(c.getTime()));   //去年本月时间
        outStock.put("lastYearTm",lastYearTm);
        c = Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        outStock.put("lastMonthTime",sdf.format(c.getTime()));   //上月时间
        outStock.put("lastMonth",lastMonth);
        c.add(Calendar.YEAR,-1);
        outStock.put("lastYearLmTime",sdf.format(c.getTime()));  //去年上月时间
        outStock.put("lastYearLm",lastYearLm);
        return outStock;
    }

    /**
     * 出库金额统计
     * @param map   {"beginDate":"","endDate":""}
     * @return
     */
    @Override
    public Object outStockPrice(Map<String, Object> map) {
        //开始时间
        String beginDate = map.get("beginDate") == null ? "" : map.get("beginDate").toString();
        //结束时间
        String endDate = map.get("endDate") == null ? "" : map.get("endDate").toString();
        //出库金额统计
        Map outStockPrice = new LinkedHashMap();
        Double sb = tstockMapper.outStockPrice("002", beginDate,endDate);       //办公设备
        Double wj = tstockMapper.outStockPrice("003", beginDate,endDate);       //办公文具
        Double hy = tstockMapper.outStockPrice("004", beginDate,endDate);       //会议用品
        Double dy = tstockMapper.outStockPrice("005", beginDate,endDate);       //打印纸
        Double all = tstockMapper.outStockPrice(null, beginDate,endDate);       //总金额
        outStockPrice.put("equipment",sb);
        outStockPrice.put("stationery",wj);
        outStockPrice.put("paper",dy);
        outStockPrice.put("meeting",hy);
        outStockPrice.put("all",all);
        return outStockPrice;
    }

    /**
     * 物品类别查询
     *
     * @param
     * @return
     */
    @Override
    public List<GoodTypeTree> findgoodTypeTree() {
        return tstockMapper.findgoodTypeTree();
    }

}
