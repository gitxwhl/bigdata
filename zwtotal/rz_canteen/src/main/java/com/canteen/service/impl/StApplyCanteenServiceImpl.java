package com.canteen.service.impl;


import com.canteen.entity.StFlow;
import com.canteen.entity.StFlowdetail;
import com.canteen.entity.bo.StApplyCanteen;
import com.canteen.entity.vo.RemoteDiningListVO;
import com.canteen.entity.vo.StApplyCanteenVO;
import com.canteen.mapper.StApplyCanteenMapper;
import com.canteen.mapper.StFlowMapper;
import com.canteen.mapper.StFlowdetailMapper;
import com.canteen.service.IStApplyCanteenService;
import com.canteen.service.IStFlowdetailService;
import com.canteen.service.UserService;
import com.canteen.utils.DateUtils;
import com.canteen.utils.PageBean;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @Auther: lilong
 * @Date: 2020/11/11 14:02
 * @Description:
 */
@Transactional(rollbackFor = { Exception.class })
@Service
public class StApplyCanteenServiceImpl implements IStApplyCanteenService {

    @Autowired
    private StFlowMapper stFlowMapper;
    @Autowired
    private StFlowdetailMapper stFlowdetailMapper;

    @Autowired
    private IStFlowdetailService stFlowdetailService;
    /*@Autowired
    private TNewsinfoMapper newsinfoMapper;*/
    @Autowired
    private  StApplyCanteenMapper stApplyCanteenMapper;

    @Autowired
    private UserService userService;


    @Override
    public List<Map<String, String>> getApprovelPerson(String userId) {
        return stApplyCanteenMapper.getApprovelPerson(userId);
    }

    @Override
    public void applyRemoteDining(StApplyCanteenVO StApplyCanteenVO) {

        //往业务表新增一条数据
//        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();//生成单号
        StApplyCanteen StApplyCanteen = new StApplyCanteen();

        //主键
        StApplyCanteen.setId(uuid);
        StApplyCanteen.setUserId(StApplyCanteenVO.getUserId());
//        account
        StApplyCanteen.setUserName(StApplyCanteenVO.getUserName());
        StApplyCanteen.setOrgId(StApplyCanteenVO.getOrgId());
        StApplyCanteen.setOrgName(StApplyCanteenVO.getOrgName());
        StApplyCanteen.setDeptId(StApplyCanteenVO.getDeptId());
        StApplyCanteen.setDeptName(StApplyCanteenVO.getDeptName());
        StApplyCanteen.setApplyOrgId(StApplyCanteenVO.getApplyOrgId());
        StApplyCanteen.setApplyOrgName(StApplyCanteenVO.getApplyOrgName());
        StApplyCanteen.setReceptionPersonId(StApplyCanteenVO.getReceptionPersonId());
        StApplyCanteen.setReceptionPersonPhone(StApplyCanteenVO.getReceptionPersonPhone());
        StApplyCanteen.setReceptionPersonName(StApplyCanteenVO.getReceptionPersonName());
        StApplyCanteen.setStartTime(StApplyCanteenVO.getStartTime());
        StApplyCanteen.setEndTime(StApplyCanteenVO.getEndTime());
        StApplyCanteen.setRemark(StApplyCanteenVO.getRemark());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
        String createDate = df.format(new Date());
        StApplyCanteen.setcTime(createDate);
        StApplyCanteen.setStaus("1");
        StApplyCanteen.setUserPhone(StApplyCanteenVO.getUserPhone());
        StApplyCanteen.setReceptionPersonPhone(StApplyCanteenVO.getReceptionPersonPhone());
        StApplyCanteen.setApplyRestaurantId(StApplyCanteenVO.getApplyRestaurantId());
        StApplyCanteen.setApplyRestaurantName(StApplyCanteenVO.getApplyRestaurantName());
        stApplyCanteenMapper.insert(StApplyCanteen);
        //往审批表里插入一条数据
        StFlow appTFlow = new StFlow();
        //流程主键
        appTFlow.setFlowNo(StApplyCanteen.getId());
        //标题
        appTFlow.setTitle("异地就餐申请");
        //审批类型
        appTFlow.setBusType("applyRemoteDining");
        //申请人
        appTFlow.setAddUserNo(StApplyCanteen.getUserId());
        //添加时间
        appTFlow.setAddTime(LocalDateTime.now());
        //审批状态
        appTFlow.setApproStatus(0);
        stFlowMapper.insert(appTFlow);
        //往审批明细表里插入 多人审批记录
        List<StFlowdetail> appTFlowdetailList = new ArrayList<>();
//        //开始节点
//        StFlowdetail appTFlowdetail0 = new StFlowdetail();
//        //主键
//        appTFlowdetail0.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//        //流程主键
//        appTFlowdetail0.setFlowNo(StApplyCanteen.getId());
//        //审批人ID
//        appTFlowdetail0.setAuditUserID("");
//        appTFlowdetail0.setAuditUserName("");
//        appTFlowdetail0.setAuditStatus("");
//        appTFlowdetail0.setFlowstatus("0");
//        appTFlowdetailList.add(appTFlowdetail0);

        //第一级节点 第一级审批员 获取当前登陆人的审批人
        List<Map<String, String>> personList = stApplyCanteenMapper.getApprovelPerson(StApplyCanteenVO.getUserId());



        String USER_ID =  personList.get(0).get("USER_ID");
        String USER_NAME = personList.get(0).get("USER_NAME");
        StFlowdetail appTFlowdetail = new StFlowdetail();
        //主键
        appTFlowdetail.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        //流程主键
        appTFlowdetail.setFlowNo(StApplyCanteen.getId());
        //审批人ID
        appTFlowdetail.setAuditUserID(USER_ID);
        appTFlowdetail.setAuditUserName(USER_NAME);
        appTFlowdetail.setAuditStatus("0");
        appTFlowdetail.setFlowstatus("1");
        appTFlowdetailList.add(appTFlowdetail);
        //第二级节点 审批员 接收人
        StFlowdetail appTFlowdetail2 = new StFlowdetail();
        //主键
        appTFlowdetail2.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        //流程主键
        appTFlowdetail2.setFlowNo(StApplyCanteen.getId());
        //审批人ID
        appTFlowdetail2.setAuditUserID(StApplyCanteen.getReceptionPersonId());
        appTFlowdetail2.setAuditUserName(StApplyCanteen.getReceptionPersonName());
        appTFlowdetail2.setAuditStatus("0");
        appTFlowdetail2.setFlowstatus("2");
        appTFlowdetailList.add(appTFlowdetail2);
        //第三级节点 审批员 接收人的后勤部管理员
        StFlowdetail appTFlowdetail3 = new StFlowdetail();
        //主键
        appTFlowdetail3.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        //流程主键
        appTFlowdetail3.setFlowNo(StApplyCanteen.getId());
        //审批人ID
        //获取该节点的审批员(接待人所在公司的审批员)
        List<Map<String, String>> thirdApprovelPeson = stApplyCanteenMapper.getApprovelPerson(StApplyCanteen.getReceptionPersonId());
        String thirdApprovelPesonID =  thirdApprovelPeson.get(0).get("USER_ID");
        String thirdApprovelPesonName = thirdApprovelPeson.get(0).get("USER_NAME");
        appTFlowdetail3.setAuditUserID(thirdApprovelPesonID);
        appTFlowdetail3.setAuditUserName(thirdApprovelPesonName);
        appTFlowdetail3.setAuditStatus("0");
        appTFlowdetail3.setFlowstatus("3");
        appTFlowdetailList.add(appTFlowdetail3);
//        //结束节点
//        StFlowdetail appTFlowdetail4 = new StFlowdetail();
//        //主键
//        appTFlowdetail4.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//        //流程主键
//        appTFlowdetail4.setFlowNo(StApplyCanteen.getId());
//        //审批人ID
//        appTFlowdetail4.setAuditUserID("");
//        appTFlowdetail4.setAuditUserName("");
//        appTFlowdetail4.setAuditStatus("");
//        appTFlowdetail4.setFlowstatus("4");
//        appTFlowdetailList.add(appTFlowdetail4);

        //插入审批记录表
        // stFlowdetailService.saveBatch(appTFlowdetailList);
        stFlowdetailService.batchSave(appTFlowdetailList);
        //提交后 往消息表里插数据  推送
       /* TNewsinfo mp = new TNewsinfo();
        String uuids = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        mp.setObjId(uuids); //主键
        mp.setNewsId(uuid); //流程编号
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

    @Override
    public PageBean getRemoteDiningList(RemoteDiningListVO remoteDiningListVO) {
        Integer pageNo = Integer.valueOf(remoteDiningListVO.getPageIndex());
        Integer pageSize = Integer.valueOf(remoteDiningListVO.getPageSize());
        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);
        PageBean pageBean = new PageBean();
        List<Map<String, Object>> resultList = stApplyCanteenMapper.getRemoteDiningList(page,remoteDiningListVO);
        pageBean.setTotalRecord(stApplyCanteenMapper.getRemoteDiningListCount(remoteDiningListVO));
        pageBean.setPageNum(pageNo);
        pageBean.setPageSize(pageSize);
        pageBean.setList(resultList);
        return pageBean;
    }

    @Override
    public List<Map<String, Object>> getRemoteDiningDetail(String flowId) {


        return stApplyCanteenMapper.getRemoteDiningDetail(flowId);
    }

    @Override
    public PageBean getRemoteDiningApprovelList(RemoteDiningListVO remoteDiningListVO) {
        Integer pageNo = Integer.valueOf(remoteDiningListVO.getPageIndex());
        Integer pageSize = Integer.valueOf(remoteDiningListVO.getPageSize());
        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);
        PageBean pageBean = new PageBean();
        if("1".equals(remoteDiningListVO.getStatus())){
            List<Map<String, Object>> resultList = stApplyCanteenMapper.getRemoteDiningList(page,remoteDiningListVO);
            pageBean.setTotalRecord(stApplyCanteenMapper.getRemoteDiningListCount(remoteDiningListVO));
            pageBean.setPageNum(pageNo);
            pageBean.setPageSize(pageSize);
            pageBean.setList(resultList);
        } else {
            List<Map<String, Object>> resultList = stApplyCanteenMapper.getRemoteDiningApprovelList(page,remoteDiningListVO);
            pageBean.setTotalRecord(stApplyCanteenMapper.getRemoteDiningApprovelCount(remoteDiningListVO));
            pageBean.setPageNum(pageNo);
            pageBean.setPageSize(pageSize);
            pageBean.setList(resultList );
        }
        return pageBean;
    }

    @Override
    public void approvelRemoteDining(String flowId, String status, String userId,String remark,LocalDateTime now,String id,String reason) { //id:ydjc_xxxxx


        StApplyCanteen StApplyCanteen = stApplyCanteenMapper.selectById(flowId);
        String flowStatus = StApplyCanteen.getStaus(); //流程节点
        String dateTime = DateUtils.now();
        id = flowId;
        //通过 流程单流转到下一节点
        if("1".equals(status)){
            //插入下一审批人相关信息
            StFlowdetail appTFlowdetail = new StFlowdetail();
            //流程节点为0，说明处于第一节点，审批人是本部门审批人，通过时，更新流转单 status为1
            if("1".equals(flowStatus)){
                //更新审批表
                stApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
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
                stApplyCanteenMapper.updateStatus(id, "2");

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
                stApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
                //获取当前登陆人的审批人
//                List<Map<String, String>> personList = stApplyCanteenMapper.getApprovelPerson(userId);
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
                Integer rows = stApplyCanteenMapper.update(canteen, updateWrapper);*/
                stApplyCanteenMapper.updateStatus(id, "3");
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
                stApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
                //更新流转单的通过
                /*Wrapper<StFlow> update = new UpdateWrapper<>();
                update.eq("FlowNo",id);
                StFlow appTFlow = new StFlow();
                //通过
                appTFlow.setApproStatus(1);
                stFlowMapper.update(appTFlow,update);*/
                Map<String,String> paramMap =new HashMap<>();
                paramMap.put("id",id);
                paramMap.put("staus","1");
                stFlowMapper.updateStatus(paramMap);

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
                Integer rows = stApplyCanteenMapper.update(canteen, updateWrapper);*/
                stApplyCanteenMapper.updateStatus(id, "4");
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
            stFlowMapper.update(appTFlow,update);*/
            Map<String,String> paramMap =new HashMap<>();
            paramMap.put("id",id);
            paramMap.put("staus","2");
            stFlowMapper.updateStatus(paramMap);

            stApplyCanteenMapper.approvelRemoteDining(flowId,status,userId,remark,dateTime,reason);
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

        return stApplyCanteenMapper.getCompanyList();
    }

    @Override
    public List<Map<String, String>> getRestaurantList(Map<String, Object> map) {
        return stApplyCanteenMapper.getRestaurantList(map);
    }

    @Override
    public List<Map<String, String>> getNextDeptInfo(Map map) {
        String deptId = map.get("deptId") == null?"" : map.get("deptId").toString();
        Integer pageNo = Integer.valueOf(map.get("pageNo")==null?"":map.get("pageNo").toString());
        Integer pageSize = Integer.valueOf(map.get("pageSize")==null?"":map.get("pageSize").toString());
        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);


        return stApplyCanteenMapper.getNextDeptInfo(page,deptId);
    }

    @Override
    public List<Map<String, String>> getPersonList(Map map) {
        String deptId = map.get("deptId") == null?"" : map.get("deptId").toString();
        Integer pageNo = Integer.valueOf(map.get("pageNo")==null?"":map.get("pageNo").toString());
        Integer pageSize = Integer.valueOf(map.get("pageSize")==null?"":map.get("pageSize").toString());
        Page<Map<String,Object>> page = new Page<>(pageNo, pageSize);

        return stApplyCanteenMapper.getPersonList(page,deptId);
    }

    @Override
    public List<Map<String, Object>> getFlowDetail(String flowId) {
        return stApplyCanteenMapper.getFlowDetail(flowId);
    }

    @Override
    public List<Map<String, String>> getApplyResList(Map map) {

        String org_id = map.get("org_id") == null?"" : map.get("org_id").toString();

        return stApplyCanteenMapper.getApplyResList(org_id);
    }


    /**
     * 自动生成报修单号
     */
    public String createRepairId() {
        StringBuffer sql = new StringBuffer();
        String serialNumber = "";
        List<Map<String, Object>> list = stApplyCanteenMapper.createRepairId();

        if (null != list && list.size() > 0 && null != list.get(0)&& null != list.get(0).get("SN")) {
            int sn = Integer.parseInt(String.valueOf(list.get(0).get("SN")));
            Date date = new Date();
            serialNumber = String.format("YDJC_%tY%<tm%<td%03d", date, sn);
        }
        return serialNumber;
    }
}
