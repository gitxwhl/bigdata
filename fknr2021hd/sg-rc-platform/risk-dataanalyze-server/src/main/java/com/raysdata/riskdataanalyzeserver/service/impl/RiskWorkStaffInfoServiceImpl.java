package com.raysdata.riskdataanalyzeserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskdataanalyzeserver.mapper.RiskWorkStaffInfoMapper;
import com.raysdata.riskdataanalyzeserver.service.RiskWorkStaffInfoService;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RiskWorkStaffInfoServiceImpl implements RiskWorkStaffInfoService {

//    @Autowired
//    private RiskWorkStaffInfoDao riskWorkStaffInfoDao;
    @Autowired
    private RiskWorkStaffInfoMapper riskWorkStaffInfoMapper;

    /*
     *人员基本情况列表查询
     * paramJson:{"page":"", "size":"", "params":{"name":"","orgName":"","datareportOrgId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerInfosList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        System.out.println("================测试执行次数getRiskWorkerInfosList=====================");
        Long data1 = System.currentTimeMillis();
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
        String name = JSONObject.parseObject(paramJson).getJSONObject("params").getString("name");
        String orgName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("orgName");
        String datareportOrgId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("datareportOrgId");
        Long data2 = System.currentTimeMillis();
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("size", size);
        mapParam.put("name", name);
        mapParam.put("orgName", orgName);
        mapParam.put("datareportOrgId", datareportOrgId);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {

            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerInfosListCNT1(mapParam);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
                int offset = (page - 1) * size;
                mapParam.put("offset", offset);
                List<Map<String, Object>> list = riskWorkStaffInfoMapper.getRiskWorkerInfosList1(mapParam);
                return PageBean.<Map<String, Object>>builder()
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *人员信息统计
     * */
    @Override
    public Map<String, Object> getStaffCount() {
System.out.println("=============测试执行次数=getStaffCount==================");
        //人员总数查询
        Integer staffCount = riskWorkStaffInfoMapper.getAllPeopleNum();

        //主业人员数
        Integer majorCount = riskWorkStaffInfoMapper.getPeopleNumByWORKERNATURE("01");

        //外包人员数
        Integer outsourcingCount = riskWorkStaffInfoMapper.getPeopleNumByWORKERNATURE("02");
        //集体人员数
        Integer collectiveCount = riskWorkStaffInfoMapper.getPeopleNumByWORKERNATURE("03");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("staffCount", staffCount);
        map.put("majorCount", majorCount);
        map.put("outsourcingCount", outsourcingCount);
        map.put("collectiveCount", collectiveCount);
        return map;
    }

    /*
     *人员详情查询
     * workerId：人员ID
     * paramJson:{"workerId":""}
     * */
    @Override
    public List<Map<String, Object>> getWorkerInfo(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String workerId = JSONObject.parseObject(paramJson).getString("workerId");
//        StringBuilder param = new StringBuilder();
//        param.append("and BASICFILEINFO_ID = '").append(workerId).append("'");
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("workerId",workerId);
//        List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkerInfosList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERINFOS.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> srpRiskGridWarnList = riskWorkStaffInfoMapper.getRiskWorkerInfosList2(mapParam);
        return srpRiskGridWarnList;
    }

    /*
     *人员项目列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"workerId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkertoProjectList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String workerId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("workerId");
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            int totalSize = this.riskWorkStaffInfoDao.getRiskWorkertoProjectListCNT(workerId);
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkertoProjectListCNT1(workerId);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                StringBuilder param = new StringBuilder();
//                param.append(" and a.WORKER_ID = '").append(workerId).append("'");

//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
                Map<String, Object> mapParam = new HashMap<>();
                mapParam.put("size", size);
                mapParam.put("workerId",workerId);
                mapParam.put("offset", offset);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkertoProjectList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERTOPROJECT.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskWorkStaffInfoDao.getRiskWorkertoProjectList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERTOPROJECT.replaceAll("%param%", param.toString())))
                        .content(riskWorkStaffInfoMapper.getRiskWorkertoProjectList1(mapParam))
//                        .elementTotalSize(this.riskWorkStaffInfoDao.getRiskWorkertoProjectList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERTOPROJECT.replaceAll("%param%", param.toString())).size())
                        .elementTotalSize(riskWorkStaffInfoMapper.getRiskWorkertoProjectList1(mapParam).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     *人员全网信息联动列表查询
     * paramJson:{"page":"", "size":"", "params":{"name":"","orgName":"","datareportOrgId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerLinkageInfosList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String name = JSONObject.parseObject(paramJson).getJSONObject("params").getString("name");
        String orgName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("orgName");
        String datareportOrgId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("datareportOrgId");
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("size", size);
        int offset = (page - 1) * size;
        mapParam.put("offset", offset);
        mapParam.put("name", name);
        mapParam.put("orgName", orgName);
        mapParam.put("datareportOrgId", datareportOrgId);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {


//            int totalSize = this.riskWorkStaffInfoDao.getRiskWorkerLinkageInfosListCNT();
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerLinkageInfosListCNT();
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                StringBuilder param = new StringBuilder();
//                if (ObjectUtils.isNotEmpty(name)) {
//                    param.append(" and a.NAME like '%").append(name).append("%'");
//                }
//                if (ObjectUtils.isNotEmpty(orgName)) {
//                    param.append(" and a.ORG_NAME like '%").append(orgName).append("%'");
//                }
//                if (ObjectUtils.isNotEmpty(datareportOrgId)) {
//                    param.append(" and a.DATAREPORT_ORG_ID = '").append(datareportOrgId).append("'");
//                }

//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
//                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkerLinkageInfosList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERLINKAGEINFOSLIST.replaceAll("%param%", param.toString()));
                List<Map<String, Object>> list = riskWorkStaffInfoMapper.getRiskWorkerLinkageInfosList(mapParam);
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskWorkStaffInfoDao.getRiskWorkerLinkageInfosList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERLINKAGEINFOSLIST.replaceAll("%param%", param.toString())))
                        .content(list)
//                        .elementTotalSize(this.riskWorkStaffInfoDao.getRiskWorkerLinkageInfosList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERLINKAGEINFOSLIST.replaceAll("%param%", param.toString())).size())
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *准入情况列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"workerId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerAccessList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject workerId = JSONObject.parseObject(paramJson).getJSONObject("params");
        String staffId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("workerId");
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            List<Map<String, Object>> orgIdList = riskWorkStaffInfoDao.getOrgId(Const.SQL_DATA_GETORGID.replaceAll("%param%", staffId));
            List<Map<String, Object>> orgIdList = riskWorkStaffInfoMapper.getOrgId(staffId);
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("size", size);
            int offset = (page - 1) * size;
            mapParam.put("offset", offset);
            mapParam.put("orgIdList", orgIdList);
//            StringBuilder param = new StringBuilder();
//            param.append("and SITEINFO_ID in (");
//            for (Map<String, Object> stringObjectMap : orgIdList) {
//                String orgId = (String) stringObjectMap.get("ORG_ID");
//                param.append("'").append(orgId).append("',");
//            }
//            String substring = param.substring(0, param.length() - 1);
//            substring = substring + ")";
//            int totalSize = riskWorkStaffInfoDao.getRiskWorkerInfosListCNT(Const.SQL_DATA_ACCESSCNT.replaceAll("%param%", substring));
            int totalSize = riskWorkStaffInfoMapper.getOrgNumByOrgId(mapParam);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
//                int offset = (page - 1) * size;
//                param = new StringBuilder(substring);
//                param.append(" limit " + size + " offset " + offset);
                //查询准入情况
//                List<Map<String, Object>> accessList = riskWorkStaffInfoDao.getRiskWorkerInfosList(Const.SQL_DATA_ACCESS.replaceAll("%param%", param.toString()));
                List<Map<String, Object>> accessList = riskWorkStaffInfoMapper.getAccessList(mapParam);
                for (Map<String, Object> stringObjectMap : accessList) {
                    String beginTime = stringObjectMap.get("REPORT_CARD_BEGINTIME").toString();
                    String endTime = stringObjectMap.get("REPORT_CARD_ENDTIME").toString();
                    beginTime = beginTime.substring(0, 10);
                    endTime = endTime.substring(0, 10);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date beginDate = sdf.parse(beginTime);
                        Date endDate = sdf.parse(endTime);
                        long accessPeriod = (endDate.getTime() - beginDate.getTime()) / (24 * 3600 * 1000);
                        stringObjectMap.put("accessPeriod", accessPeriod);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                return PageBean.<Map<String, Object>>builder()
                        .content(accessList)
                        .elementTotalSize(accessList.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *人员单位变更记录列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"workerId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerUnitChangeList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String workerId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("workerId");

        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {


//            int totalSize = this.riskWorkStaffInfoDao.getRiskWorkerUnitChangeListCNT(workerId);
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerUnitChangeListCNT(workerId);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                StringBuilder param = new StringBuilder();

//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
//                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkerUnitChangeList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERUNITCHANGELIST.replaceAll("%param%", param.toString()),workerId);
                return PageBean.<Map<String, Object>>builder()
//                        .content(riskWorkStaffInfoMapper.getRiskWorkerUnitChangeList(workerId))
//                        .elementTotalSize(riskWorkStaffInfoMapper.getRiskWorkerUnitChangeList(workerId).size())
                        .content(riskWorkStaffInfoMapper.getRiskWorkerUnitChangeList(workerId))
                        .elementTotalSize(riskWorkStaffInfoMapper.getRiskWorkerUnitChangeList(workerId).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *人员违章记录列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"workerId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerViolationList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String workerId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("workerId");

        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            int totalSize = this.riskWorkStaffInfoDao.getRiskWorkerViolationListCNT(workerId);
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerViolationListCNT(workerId);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                StringBuilder param = new StringBuilder();
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
//                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkerViolationList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONLIST.replaceAll("%param%", param.toString()),workerId);
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskWorkStaffInfoDao.getRiskWorkerViolationList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONLIST.replaceAll("%param%", param.toString()), workerId))
                        .content(riskWorkStaffInfoMapper.getRiskWorkerViolationList(workerId))
//                        .elementTotalSize(this.riskWorkStaffInfoDao.getRiskWorkerViolationList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONLIST.replaceAll("%param%", param.toString()), workerId).size())
                        .elementTotalSize(riskWorkStaffInfoMapper.getRiskWorkerViolationList(workerId).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     *人员安全资信列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"name":"","orgName":"","datareportOrgId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerSafeInfoList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
       long data1 =System.currentTimeMillis();

        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
        String name = JSONObject.parseObject(paramJson).getJSONObject("params").getString("name");
        String orgName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("orgName");
        String datareportOrgId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("datareportOrgId");
        long data2 =System.currentTimeMillis()-data1;
        System.out.println("-----------------json1-------------------------》》》》》》》》" + data2 +"ms");

        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("size", size);
        int offset = (page - 1) * size;
        mapParam.put("offset", offset);
        mapParam.put("name", name);
        mapParam.put("orgName", orgName);
        mapParam.put("datareportOrgId", datareportOrgId);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            long data3 =System.currentTimeMillis();
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerSafeInfoListCNT();
            long data4 =System.currentTimeMillis()-data3;
            System.out.println("-----------------json2-------------------------》》》》》》》》" + data4 +"ms");
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
                long data5 =System.currentTimeMillis();
                List<Map<String, Object>> list = riskWorkStaffInfoMapper.getRiskWorkerSafeInfoList(mapParam);
                long data6 =System.currentTimeMillis()-data5;
                System.out.println("-----------------json3-------------------------》》》》》》》》" + data6 +"ms");
                return PageBean.<Map<String, Object>>builder()
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *人员安全资信统计
     * */
    @Override
    public Map<String, Object> getSafeCount() {
        //人员总数查询
        long data6 =System.currentTimeMillis();
        Integer staffCount = riskWorkStaffInfoMapper.getAllPeopleNum();
        //违章人数查询
        Integer violationCount = riskWorkStaffInfoMapper.getViolationPeopleNum();

        //参加考试人数查询
//        StringBuilder param = new StringBuilder();
//        Integer examinationCount = riskWorkStaffInfoDao.getRiskWorkerInfosListCNT(Const.SQL_DATA_GETEXAMINATION.replaceAll("%param%", param.toString()));
        Integer examinationCount = riskWorkStaffInfoMapper.getExaminationNum(null);

        //考试通过人数查询
//        param.append("and WHETHER_PASS = '是'");
//        Integer count = riskWorkStaffInfoDao.getRiskWorkerInfosListCNT(Const.SQL_DATA_GETEXAMINATION.replaceAll("%param%", param.toString()));
        Integer count = riskWorkStaffInfoMapper.getExaminationNum("是");
        long data7 =System.currentTimeMillis()-data6;
        System.out.println("总数统计================================》》》》》》》" +data7+"ms");
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("staffCount", staffCount);
        map.put("violationCount", violationCount);
        map.put("examinationCount", examinationCount);
        map.put("passCount", count);
        return map;
    }

    /*
     *人员培训记录列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"workerId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerSafeLearnList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String workerId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("workerId");
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("size", size);
        int offset = (page - 1) * size;
        mapParam.put("offset", offset);
        mapParam.put("workerId", workerId);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            int totalSize = this.riskWorkStaffInfoDao.getRiskWorkerSafeLearnListCNT(workerId);
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerSafeLearnListCNT(workerId);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                StringBuilder param = new StringBuilder();
//                if (ObjectUtils.isNotEmpty(workerId)) {
//                    param.append(" and WORKER_ID = '").append(workerId).append("'");
//                }
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
//                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkerSafeLearnList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFELEARNLIST.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskWorkStaffInfoDao.getRiskWorkerSafeLearnList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFELEARNLIST.replaceAll("%param%", param.toString())))
                        .content(riskWorkStaffInfoMapper.getRiskWorkerSafeLearnList(mapParam))
//                        .elementTotalSize(this.riskWorkStaffInfoDao.getRiskWorkerSafeLearnList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFELEARNLIST.replaceAll("%param%", param.toString())).size())
                        .elementTotalSize(riskWorkStaffInfoMapper.getRiskWorkerSafeLearnList(mapParam).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *人员考试记录列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"workerId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerSafetyTestList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String workerId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("workerId");
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("size", size);
        int offset = (page - 1) * size;
        mapParam.put("offset", offset);
        mapParam.put("workerId", workerId);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {


//            int totalSize = this.riskWorkStaffInfoDao.getRiskWorkerSafetyTestListCNT(workerId);
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerSafetyTestListCNT(workerId);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                StringBuilder param = new StringBuilder();
//                if (ObjectUtils.isNotEmpty(workerId)) {
//                    param.append(" and WORKER_ID = '").append(workerId).append("'");
//                }

//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
//                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkerSafetyTestList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFETYTESTLIST.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskWorkStaffInfoDao.getRiskWorkerSafetyTestList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFETYTESTLIST.replaceAll("%param%", param.toString())))
                        .content(riskWorkStaffInfoMapper.getRiskWorkerSafetyTestList(mapParam))
//                        .elementTotalSize(this.riskWorkStaffInfoDao.getRiskWorkerSafetyTestList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFETYTESTLIST.replaceAll("%param%", param.toString())).size())
                        .elementTotalSize(riskWorkStaffInfoMapper.getRiskWorkerSafetyTestList(mapParam).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *人员违章记录列表查询
     * workerId：人员ID
     * paramJson:{"page":"", "size":"", "params":{"workerId":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskWorkerViolationPointsList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String workerId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("workerId");
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("size", size);
        int offset = (page - 1) * size;
        mapParam.put("offset", offset);
        mapParam.put("workerId", workerId);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            int totalSize = this.riskWorkStaffInfoDao.getRiskWorkerViolationPointsListCNT(workerId);
            int totalSize = riskWorkStaffInfoMapper.getRiskWorkerViolationPointsListCNT(workerId);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();

            } else {
//                StringBuilder param = new StringBuilder();
//                if (ObjectUtils.isNotEmpty(workerId)) {
//                    param.append(" and a.PENALTY_STAFF_ID = '").append(workerId).append("'");
//                }
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
//                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkStaffInfoDao.getRiskWorkerViolationPointsList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONPOINTSLIST.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskWorkStaffInfoDao.getRiskWorkerViolationPointsList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONPOINTSLIST.replaceAll("%param%", param.toString())))
                        .content(riskWorkStaffInfoMapper.getRiskWorkerViolationPointsList(mapParam))
//                        .elementTotalSize(this.riskWorkStaffInfoDao.getRiskWorkerViolationPointsList(Const.SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONPOINTSLIST.replaceAll("%param%", param.toString())).size())
                        .elementTotalSize(riskWorkStaffInfoMapper.getRiskWorkerViolationPointsList(mapParam).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }
}
