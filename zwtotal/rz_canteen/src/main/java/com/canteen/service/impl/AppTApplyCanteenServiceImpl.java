package com.canteen.service.impl;

import com.canteen.entity.*;
import com.canteen.entity.vo.AppTApplyCanteenVO;
import com.canteen.entity.vo.RemoteDiningListVO;
import com.canteen.mapper.AppTApplyCanteenMapper;
import com.canteen.mapper.AppTFlowMapper;
import com.canteen.mapper.AppTFlowdetailMapper;
import com.canteen.service.IAppTApplyCanteenService;
import com.canteen.service.IAppTFlowdetailService;
import com.canteen.util.UUIDUtil;
import com.canteen.utils.DateUtils;
import com.canteen.utils.PageBean;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@Transactional(rollbackFor = { Exception.class })
@Service
public class AppTApplyCanteenServiceImpl implements IAppTApplyCanteenService {


    @Autowired
    private AppTFlowMapper appTFlowMapper;
    @Autowired
    private AppTFlowdetailMapper appTFlowdetailMapper;

    @Autowired
    private IAppTFlowdetailService iAppTFlowdetailService;

    @Autowired
    private AppTApplyCanteenMapper appTApplyCanteenMapper;
    /**
     * 根据ACCOUNT获取用户信息
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, String>> getApprovelPerson(String userId) {
        return appTApplyCanteenMapper.getApprovelPerson(userId);
    }


    /**
     * 异地就餐申请
     * @param
     */
    @Override
    public void applyRemoteDining(AppTApplyCanteenVO appTApplyCanteenVO) {
        //往业务表新增一条数据
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
//        String uuid = createRepairId();//生成单号
        AppTApplyCanteen appTApplyCanteen = new AppTApplyCanteen();
        //主键
        appTApplyCanteen.setId(uuid);
        appTApplyCanteen.setUserId(appTApplyCanteenVO.getUserId());
        appTApplyCanteen.setUserName(appTApplyCanteenVO.getUserName());
        appTApplyCanteen.setOrgId(appTApplyCanteenVO.getOrgId());
        appTApplyCanteen.setOrgName(appTApplyCanteenVO.getOrgName());
        appTApplyCanteen.setDeptId(appTApplyCanteenVO.getDeptId());
        appTApplyCanteen.setDeptName(appTApplyCanteenVO.getDeptName());
        appTApplyCanteen.setApplyOrgId(appTApplyCanteenVO.getApplyOrgId());
        appTApplyCanteen.setApplyOrgName(appTApplyCanteenVO.getApplyOrgName());
        appTApplyCanteen.setReceptionPersonId(appTApplyCanteenVO.getReceptionPersonId());
        appTApplyCanteen.setReceptionPersonName(appTApplyCanteenVO.getReceptionPersonName());
        appTApplyCanteen.setStartTime(appTApplyCanteenVO.getStartTime());
        appTApplyCanteen.setEndTime(appTApplyCanteenVO.getEndTime());
        appTApplyCanteen.setRemark(appTApplyCanteenVO.getRemark());
        appTApplyCanteen.setCTime(LocalDateTime.now());
        appTApplyCanteen.setStaus("1");
        appTApplyCanteen.setUserPhone(appTApplyCanteenVO.getUserPhone());
        appTApplyCanteen.setReceptionPersonPhone(appTApplyCanteenVO.getReceptionPersonPhone());
        appTApplyCanteen.setApplyRestaurantId(appTApplyCanteenVO.getApplyRestaurantId());
        appTApplyCanteen.setApplyRestaurantName(appTApplyCanteenVO.getApplyRestaurantName());
        /**
         * 添加申请单
         */
        appTApplyCanteenMapper.saveAppTApplyCanteen(appTApplyCanteen);
        //往审批表里插入一条数据
        AppTFlow appTFlow = new AppTFlow();
        //流程主键
        appTFlow.setFlowNo(appTApplyCanteen.getId());
        //标题
        appTFlow.setTitle("异地就餐申请");
        //审批类型
        appTFlow.setBusType("applyRemoteDining");
        //申请人
        appTFlow.setAddUserNo(appTApplyCanteen.getUserId());
        //添加时间
        appTFlow.setAddTime(LocalDateTime.now());
        //审批状态
        appTFlow.setApproStatus(0);
        /**
         * 添加审批记录
         */
        appTFlowMapper.saveAppTFlow(appTFlow);

        //往审批明细表里插入 多人审批记录
        List<AppTFlowdetail> appTFlowdetailList = new ArrayList<>();
//        //开始节点
//        AppTFlowdetail appTFlowdetail0 = new AppTFlowdetail();
//        //主键
//        appTFlowdetail0.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//        //流程主键
//        appTFlowdetail0.setFlowNo(appTApplyCanteen.getId());
//        //审批人ID
//        appTFlowdetail0.setAuditUserID("");
//        appTFlowdetail0.setAuditUserName("");
//        appTFlowdetail0.setAuditStatus("");
//        appTFlowdetail0.setFlowstatus("0");
//        appTFlowdetailList.add(appTFlowdetail0);

        //第一级节点 第一级审批员 获取当前登陆人的审批人
        List<Map<String, String>> personList = appTApplyCanteenMapper.getApprovelPerson(appTApplyCanteenVO.getUserId());
        String USER_ID =  personList.get(0).get("USER_ID");
        String USER_NAME = personList.get(0).get("USER_NAME");
        AppTFlowdetail appTFlowdetail = new AppTFlowdetail();
        //主键
        appTFlowdetail.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        //流程主键
        appTFlowdetail.setFlowNo(appTApplyCanteen.getId());
        //审批人ID
        appTFlowdetail.setAuditUserID(USER_ID);
        appTFlowdetail.setAuditUserName(USER_NAME);
        appTFlowdetail.setAuditStatus("0");
        appTFlowdetail.setFlowstatus("1");
        appTFlowdetailList.add(appTFlowdetail);
        //第二级节点 审批员 接收人
        AppTFlowdetail appTFlowdetail2 = new AppTFlowdetail();
        //主键
        appTFlowdetail2.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        //流程主键
        appTFlowdetail2.setFlowNo(appTApplyCanteen.getId());
        //审批人ID
        appTFlowdetail2.setAuditUserID(appTApplyCanteen.getReceptionPersonId());
        appTFlowdetail2.setAuditUserName(appTApplyCanteen.getReceptionPersonName());
        appTFlowdetail2.setAuditStatus("0");
        appTFlowdetail2.setFlowstatus("2");
        appTFlowdetailList.add(appTFlowdetail2);
        //第三级节点 审批员 接收人的后勤部管理员
        AppTFlowdetail appTFlowdetail3 = new AppTFlowdetail();
        //主键
        appTFlowdetail3.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        //流程主键
        appTFlowdetail3.setFlowNo(appTApplyCanteen.getId());
        //审批人ID
        //获取该节点的审批员(接待人所在公司的审批员)
        String aa = appTApplyCanteen.getReceptionPersonId();
        List<Map<String, String>> thirdApprovelPeson = appTApplyCanteenMapper.getApprovelPerson(appTApplyCanteen.getReceptionPersonId());
        String thirdApprovelPesonID =  thirdApprovelPeson.get(0).get("USER_ID");
        String thirdApprovelPesonName = thirdApprovelPeson.get(0).get("USER_NAME");
        appTFlowdetail3.setAuditUserID(thirdApprovelPesonID);
        appTFlowdetail3.setAuditUserName(thirdApprovelPesonName);
        appTFlowdetail3.setAuditStatus("0");
        appTFlowdetail3.setFlowstatus("3");
        appTFlowdetailList.add(appTFlowdetail3);

//        //结束节点
//        AppTFlowdetail appTFlowdetail4 = new AppTFlowdetail();
//        //主键
//        appTFlowdetail4.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//        //流程主键
//        appTFlowdetail4.setFlowNo(appTApplyCanteen.getId());
//        //审批人ID
//        appTFlowdetail4.setAuditUserID("");
//        appTFlowdetail4.setAuditUserName("");
//        appTFlowdetail4.setAuditStatus("");
//        appTFlowdetail4.setFlowstatus("4");
//        appTFlowdetailList.add(appTFlowdetail4);

        //插入审批记录表
            iAppTFlowdetailService.saveBatch(appTFlowdetailList);

        //提交后 往消息表里插数据  推送
//        TNewsinfo mp = new TNewsinfo();
//        String uuids = UUID.randomUUID().toString().replace("-", "").toLowerCase();
//        mp.setObjId(uuids); //主键
//        mp.setNewsId(uuid); //流程编号
//        mp.setNewsTitle("您有一条异地就餐的审批消息 ");
//        mp.setNewsContent(appTApplyCanteen.getUserName()+"于"+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(appTApplyCanteen.getcTime())+"提交一条异地就餐申请，请在异地就餐审核里进行处理。");
//        mp.setNewsType("B006"); //异地就餐
//        mp.setCreateUserId(appTApplyCanteen.getUserId()); //报修人工号
//        mp.setCreatPerson(appTApplyCanteen.getUserName()); //报修人姓名
//        mp.setCreatTime(LocalDateTime.now()); //创建时间
//        mp.setMsgType("1");//消息状态 0未发送  1已发送
//
//        mp.setMsgIsordinary("1");
//        mp.setMsgReceive(0); //接收状态，0 未接受  1已接收
//        mp.setMsgRead(0);// 是否已读
//        mp.setReceptUserId(appTFlowdetail.getAuditUserID());
//        mp.setReceptUserName(appTFlowdetail.getAuditUserName());
//        mp.setDeptId("");
//        mp.setDeptName("");

//        newsinfoMapper.insert(mp);
    }

    /**
     * mybatispuls分页修改
     * 异地就餐列表
     * @param remoteDiningListVO
     * @return
     */
//    @Override
//    public IPage<Map<String, Object>> getRemoteDiningList(RemoteDiningListVO remoteDiningListVO) {
//        Integer pageNo = Integer.valueOf(remoteDiningListVO.getPageIndex());
//        Integer pageSize = Integer.valueOf(remoteDiningListVO.getPageSize());
//        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);
//        IPage<Map<String, Object>> resultList = appTApplyCanteenMapper.getRemoteDiningList(page,remoteDiningListVO);
//        return resultList;
//    }

    /**
     * 根据流程节点获取申请和审批信息 app_t_flowdetail ,app_t_apply_canteen
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getRemoteDiningDetail(String flowId) {
        return appTApplyCanteenMapper.getRemoteDiningDetail(flowId);
    }


    /**
     * 异地就餐审批列表
     * @param
     * @return
     */
    @Override
    public PageBean<AppTFlowdetail> getAppTFlowdetailPage(PageBean<AppTFlowdetail> pageBean, RemoteDiningListVO remoteDiningListVO) {
        Integer startNum = (Integer.valueOf(remoteDiningListVO.getPageIndex())-1)*Integer.valueOf(remoteDiningListVO.getPageSize());
        remoteDiningListVO.setPageIndex(startNum);
        List<AppTFlowdetail> appTFlowdetailList = appTApplyCanteenMapper.getRemoteDiningApprovelTGList(remoteDiningListVO);
        Integer account = appTApplyCanteenMapper.getRemoteDiningApprovelTGAccount(remoteDiningListVO);
        pageBean.setList(appTFlowdetailList);
        pageBean.setTotalRecord(account);
        return pageBean;
    }


    /**
     * 申请
     * @param remoteDiningListVO
     * @return
     */
//    @Override
//    public IPage<Map<String, Object>> getRemoteDiningApprovelList(RemoteDiningListVO remoteDiningListVO) {
//        Integer pageNo = Integer.valueOf(remoteDiningListVO.getPageIndex());
//        Integer pageSize = Integer.valueOf(remoteDiningListVO.getPageSize());
//        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);
//
//        if("1".equals(remoteDiningListVO.getStatus())){
//            IPage<Map<String, Object>> resultList = appTApplyCanteenMapper.getRemoteDiningApprovelTGList(page,remoteDiningListVO);
//            return resultList;
//        }else {
//            IPage<Map<String, Object>> resultList = appTApplyCanteenMapper.getRemoteDiningApprovelList(page,remoteDiningListVO);
//            return resultList;
//        }
//    }


    /**
     * 异地就餐审批
     * @param
     */
    @Override
    public void approvelRemoteDining( String status, String userId,String remark,LocalDateTime now,String id,String reason) { //id:ydjc_xxxxx
        AppTApplyCanteen StApplyCanteen = appTApplyCanteenMapper.selectById(id);
        String flowStatus = StApplyCanteen.getStaus(); //流程节点
        String dateTime = DateUtils.now();
        /**
         *  id = flowId; 李龙
         */
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
       String flowId = uuid;
        //通过 流程单流转到下一节点
        if("1".equals(status)){
            //插入下一审批人相关信息
           /**
           StFlowdetail appTFlowdetail = new StFlowdetail();
            */
            AppTFlowdetail appTFlowdetail = new AppTFlowdetail();
            //流程节点为0，说明处于第一节点，审批人是本部门审批人，通过时，更新流转单 status为1
            if("1".equals(flowStatus)){
                //更新审批表
                appTApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
                //插入接收人的审批信息
//                appTFlowdetail.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//                appTFlowdetail.setFlowNo(id);
//                appTFlowdetail.setAuditUserID(StApplyCanteen.getReceptionPersonId());
//                appTFlowdetail.setAuditUserName(StApplyCanteen.getReceptionPersonName());
//                appTFlowdetail.setAuditStatus("0");
//                appTFlowdetail.setAuditRemark("");
//                appTFlowdetail.setFlowstatus("1");
//                appTFlowdetailMapper.insert(appTFlowdetail);
                //更新流转单状态为1
              /*  UpdateWrapper<StApplyCanteen> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",id);
                StApplyCanteen canteen = new StApplyCanteen();
                canteen.setStaus("2");*/
                appTApplyCanteenMapper.updateStatus(id, "2");

                //插入消息
                //提交后 往消息表里插数据  推送
                /*TNewsinfo mp = new TNewsinfo();
                String uuids = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                mp.setObjId(uuids); //主键
                mp.setNewsId(id); //流程编号
                mp.setNewsTitle("您有一条异地就餐的审批消息 ");
                mp.setNewsContent(StApplyCanteen.getUserName()+"于"+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(StApplyCanteen.getcTime())+"提交一条报修信息，请在异地就餐审核里进行处理。");
                mp.setNewsType("B006"); //异地就餐
                mp.setCreateUserId(StApplyCanteen.getUserId());
                mp.setCreatPerson(StApplyCanteen.getUserName());
                mp.setCreatTime(LocalDateTime.now()); //创建时间
                mp.setMsgType("1");//消息状态 0未发送  1已发送
                mp.setMsgIsordinary("1");
                mp.setMsgReceive(0); //接收状态，0 未接受  1已接收
                mp.setMsgRead(0);// 是否已读
                mp.setReceptUserId(appTFlowdetail.getAuditUserID());
                mp.setReceptUserName(appTFlowdetail.getAuditUserName());
                mp.setDeptId("");
                mp.setDeptName("");

                newsinfoMapper.insert(mp);*/
            }
            //如果处于第二级，则审批人是接待人，往审批表里插入下一条审批人信息 更新流转单 status为2
            if("2".equals(flowStatus)){
                appTApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
                //获取当前登陆人的审批人
//                List<Map<String, String>> personList = appTApplyCanteenMapper.getApprovelPerson(userId);
//                String USER_ID =  personList.get(0).get("USER_ID");
//                String USER_NAME = personList.get(0).get("USER_NAME");
//                //插入下一审批人相关信息
//                appTFlowdetail.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//                appTFlowdetail.setFlowNo(id);
//                appTFlowdetail.setAuditUserID(USER_ID);
//                appTFlowdetail.setAuditUserName(USER_NAME);
//                appTFlowdetail.setAuditStatus("0");
//                appTFlowdetail.setAuditRemark("");
//                appTFlowdetail.setFlowstatus("2");
//                appTFlowdetailMapper.insert(appTFlowdetail);
                //更新流转单状态为2
               /* UpdateWrapper<StApplyCanteen> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",id);
                StApplyCanteen canteen = new StApplyCanteen();
                canteen.setStaus("3");
                Integer rows = appTApplyCanteenMapper.update(canteen, updateWrapper);*/
                appTApplyCanteenMapper.updateStatus(id, "3");
                //插入消息
                //提交后 往消息表里插数据  推送
               /* TNewsinfo mp = new TNewsinfo();
                String uuids = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                mp.setObjId(uuids); //主键
                mp.setNewsId(id); //流程编号
                mp.setNewsTitle("您有一条异地就餐的审批消息 ");
                mp.setNewsContent(StApplyCanteen.getUserName()+"于"+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(StApplyCanteen.getcTime())+"提交一条报修信息，请在异地就餐审核里进行处理。");
                mp.setNewsType("B006"); //异地就餐
                mp.setCreateUserId(StApplyCanteen.getUserId()); //报修人工号
                mp.setCreatPerson(StApplyCanteen.getUserName()); //报修人姓名
                mp.setCreatTime(LocalDateTime.now()); //创建时间
                mp.setMsgType("1");//消息状态 0未发送  1已发送
                mp.setMsgIsordinary("1");
                mp.setMsgReceive(0); //接收状态，0 未接受  1已接收
                mp.setMsgRead(0);// 是否已读
                mp.setReceptUserId(appTFlowdetail.getAuditUserID());
                mp.setReceptUserName(appTFlowdetail.getAuditUserName());
                mp.setDeptId("");
                mp.setDeptName("");

                newsinfoMapper.insert(mp);*/

            }
            //如果处于第三级，则审批人是接待人所在部门审批人，往审批表里插入下一条审批人信息 流转单节点 status为2
            if("3".equals(flowStatus)){
                appTApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
                //更新流转单的通过
                /*Wrapper<StFlow> update = new UpdateWrapper<>();
                update.eq("FlowNo",id);
                StFlow appTFlow = new StFlow();
                //通过
                appTFlow.setApproStatus(1);
                appTFlowMapper.update(appTFlow,update);*/
                Map<String,String> paramMap =new HashMap<>();
                paramMap.put("id",id);
                paramMap.put("staus","1");
                appTFlowMapper.updateStatus(paramMap);

//                UpdateWrapper<StFlowdetail> appTFlowdetailUpdateWrapper = new UpdateWrapper<>();
//                appTFlowdetailUpdateWrapper.eq("id",flowId);
//                StFlowdetail appTFlowdetail1 = new StFlowdetail();
//                appTFlowdetail1.setFlowstatus("3");
//                appTFlowdetailMapper.update(appTFlowdetail1,appTFlowdetailUpdateWrapper);
                //插入下一审批人相关信息
//                appTFlowdetail.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//                appTFlowdetail.setFlowNo(id);
//                appTFlowdetail.setAuditUserID("");
//                appTFlowdetail.setAuditUserName("");
//                appTFlowdetail.setAuditStatus("1");
//                appTFlowdetail.setAuditRemark("");
//                appTFlowdetail.setFlowstatus("4");
//                appTFlowdetailMapper.insert(appTFlowdetail);

                //更新流转单状态为3
                /*UpdateWrapper<StApplyCanteen> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",id);
                StApplyCanteen canteen = new StApplyCanteen();
                canteen.setStaus("4");
                Integer rows = appTApplyCanteenMapper.update(canteen, updateWrapper);*/
                appTApplyCanteenMapper.updateStatus(id, "4");
            }
        }else {
            //驳回
            //流转单节点变为0
            //更新流转单的通过
           /* UpdateWrapper<StFlow> update = new UpdateWrapper<>();
            update.eq("FlowNo",id);
            StFlow appTFlow = new StFlow();
            //通过
            appTFlow.setApproStatus(2);
            appTFlowMapper.update(appTFlow,update);*/
            Map<String,String> paramMap =new HashMap<>();
            paramMap.put("id",id);
            paramMap.put("staus","2");
            appTFlowMapper.updateStatus(paramMap);
            appTApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
        }

        //
        //将消息模块 消息设置已读
       /* QueryWrapper<TNewsinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NEWS_ID",id);
        queryWrapper.eq("RECEPT_USER_ID",userId);
        TNewsinfo tNewsinfo = new TNewsinfo();
        tNewsinfo.setNewsId(id);
        tNewsinfo.setMsgRead(1); //已读
        tNewsinfo.setMsgReceive(1);//已接收
        newsinfoMapper.update(tNewsinfo,queryWrapper);*/

    }








    @Override
    public List<Map<String, String>> getCompanyList() {
        return appTApplyCanteenMapper.getCompanyList();
    }

    @Override
    public List<Map<String, String>> getRestaurantList(Map<String, Object> map) {
        return appTApplyCanteenMapper.getRestaurantList(map);
    }

    @Override
    public List<Map<String, String>> getNextDeptInfo(Map map) {
        String deptId = map.get("deptId") == null?"" : map.get("deptId").toString();
        Integer pageNo = Integer.valueOf(map.get("pageNo")==null?"":map.get("pageNo").toString());
        Integer pageSize = Integer.valueOf(map.get("pageSize")==null?"":map.get("pageSize").toString());
        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);


        return appTApplyCanteenMapper.getNextDeptInfo(page,deptId);
    }


    @Override
    public List<Map<String, String>> getPersonList(Map map) {
        String deptId = map.get("deptId") == null?"" : map.get("deptId").toString();
        Integer pageNo = Integer.valueOf(map.get("pageNo")==null?"":map.get("pageNo").toString());
        Integer pageSize = Integer.valueOf(map.get("pageSize")==null?"":map.get("pageSize").toString());
        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);

        return appTApplyCanteenMapper.getPersonList(page,deptId);
    }


    @Override
    public List<Map<String, Object>> getFlowDetail(String flowId) {
        return appTApplyCanteenMapper.getFlowDetail(flowId);
    }


    @Override
    public List<Map<String, String>> getApplyResList(Map map) {

        String org_id = map.get("org_id") == null?"" : map.get("org_id").toString();

        return appTApplyCanteenMapper.getApplyResList(org_id);
    }

    /**
     * 代办
     * @param map
     * @return
     */
    @Override
    public PageBean<Map<String, String>> getAllInfo(Map map) {
        //搜索关键字
        String keyWord = String.valueOf(map.get("keyWord")) ;
        //流程状态，1.待审,2.通过.3.驳回,4.撤销
        //当前接口查询条件
        //1  待审批（需要自己审批的）
        //2  已提交（自己提交的所有申请）
        //3  已审批（流程已走完）
        String status = map.get("status").toString();
        String userId = map.get("userId").toString();
        if(keyWord != null && !"".equals(keyWord) && keyWord.replaceAll(" ","").length()>0){
            map.put("keyWord","%"+keyWord+"%");
        }else{
            map.put("keyWord","");
        }
        int start = (Integer.valueOf(String.valueOf(map.get("index")))-1)*Integer.valueOf(String.valueOf( map.get("pageSize")));
        Integer pageSize = (map.get("pageSize") == null || map.get("pageSize").equals("")) ? 0 : Integer.parseInt(map.get("pageSize").toString());
        map.put("index",start);
        List<Map<String, String>> list = appTApplyCanteenMapper.getAllInfo(map);
        Integer count = appTApplyCanteenMapper.getAllInfoCount(map);
        PageBean<Map<String, String>> page = new PageBean<>();
        page.setList(list);
        page.setPageSize(pageSize);
        page.setTotalRecord(count);
        return page;
    }


    /**
     * 自动生成报修单号
     */
    public String createRepairId() {
        StringBuffer sql = new StringBuffer();
        String serialNumber = "";
        List<Map<String, Object>> list = appTApplyCanteenMapper.createRepairId();

        if (null != list && list.size() > 0 && null != list.get(0)&& null != list.get(0).get("SN")) {
            int sn = Integer.parseInt(String.valueOf(list.get(0).get("SN")));
            Date date = new Date();
            serialNumber = String.format("YDJC_%tY%<tm%<td%03d", date, sn);
        }
        return serialNumber;
    }

}
