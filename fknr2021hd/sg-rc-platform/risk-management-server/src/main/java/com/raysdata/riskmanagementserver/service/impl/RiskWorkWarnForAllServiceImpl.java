package com.raysdata.riskmanagementserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskmanagementserver.bean.vo.AreaVo;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskSysTabVoPo;
import com.raysdata.riskmanagementserver.bean.vo.SrpWorkWorkplandaysVo;
import com.raysdata.riskmanagementserver.dao.RiskWorkWarnForAllDao;
import com.raysdata.riskmanagementserver.service.RiskWorkWarnForAllService;
import com.raysdata.riskmanagementserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zyy
 */
@Service
public class RiskWorkWarnForAllServiceImpl implements RiskWorkWarnForAllService {


    @Autowired
    private RiskWorkWarnForAllDao riskWorkWarnForAllDao;

    /**
     *风险作业预警情况
     * warningLevel:风险等级1-5级
     * areaId：省份编码（默认全国不用传，具体下钻省份是传省份编码）
     * workType:作业类型
     * {"warningLevel":"","areaId":"","workType":"","startTime":"","endTime":""}
     * */
    @Override
    public List<Map<String, Object>> getSrpriskworkwarnforallareacnt(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String warningLevel = JSONObject.parseObject(paramJson).getString("warningLevel");
        String areaId = JSONObject.parseObject(paramJson).getString("areaId");
        String workType = JSONObject.parseObject(paramJson).getString("workType");
        String startTime = JSONObject.parseObject(paramJson).getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getString("endTime");

        SrpRiskSysTabVoPo srpRiskSysTabVoPo = new SrpRiskSysTabVoPo();

        srpRiskSysTabVoPo.setStartTime(startTime);
        srpRiskSysTabVoPo.setEndTime(endTime);
//        StringBuilder param = new StringBuilder();
//        String sql = "";
//        if(ObjectUtils.isNotEmpty(startTime) && ObjectUtils.isNotEmpty(endTime)){
//            param.append("a.BEGIN_TIME between STR_TO_DATE('").append(startTime).append("','%Y-%m-%d %H:%i:%s') and STR_TO_DATE('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
//        }else {
//
//            param.append("to_days( a.BEGIN_TIME ) = to_days( now( ) )");
//        }

//        if (ObjectUtils.isNotEmpty(warningLevel)) {

            srpRiskSysTabVoPo.setWarningLevel(warningLevel);
//            param.append(" and a.WORKRISK_LEVEL = '").append(warningLevel).append("'");
//        }
//        if (ObjectUtils.isNotEmpty(workType)) {
            srpRiskSysTabVoPo.setWorkType(workType);
//            param.append(" and a.WORK_TYPE = '").append(workType).append("'");
//        }
//        if (ObjectUtils.isNotEmpty(areaId)) {
            srpRiskSysTabVoPo.setAreaId(areaId);
//            param.append(" and a.DATAREPORT_ORG_ID = '").append(areaId).append("'");
//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLAREACNTS.replaceAll("%param2%", "srg.CITY_ID").replaceAll("%param3%", areaId);
//        }else{

//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLAREACNTS.replaceAll("%param2%", "srg.DATAREPORT_ORG_ID").replaceAll("%param3%", "DATAREPORT_ORG");
//        }
//        sql= sql.replaceAll("%param1%", param.toString());
        return riskWorkWarnForAllDao.getSrpriskworkwarnforallareacnt(srpRiskSysTabVoPo);
    }


    /**
     * 作业类型风险数量统计
     * areaId：地区编码（全国地图时候可以不传，下钻省份时候传值即可）
     * */
    @Override
    public Map<String, Object> getSrpriskworkwarnforallworktypecnt(String areaId) {
//        StringBuilder param = new StringBuilder();
//        String sql = "";
//        if (ObjectUtils.isNotEmpty(areaId)) {
//            param.append(" and c.DATAREPORT_ORG_ID = '").append(areaId).append("' ");
//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLCNT.replaceAll("%param%", param.toString());
//        }else {
//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLCNT.replaceAll("%param%", "");
//        }
        return riskWorkWarnForAllDao.getSrpriskworkwarnforallworktypecnt(areaId);
    }


    /**
     * 风险作业状态数量统计
     * areaId：地区编码（全国地图时候可以不传，下钻省份时候传值即可）
     * */
    @Override
    public Map<String, Object> getSrpriskworkwarnworkstatecnt(String areaId) {
//        StringBuilder param = new StringBuilder();
//        String sql = "";
//        if (ObjectUtils.isNotEmpty(areaId)) {
//            param.append(" and c.DATAREPORT_ORG_ID = '").append(areaId).append("' ");
//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNWORKTYPECNT.replaceAll("%param%", param.toString());
//        }else {
//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNWORKTYPECNT.replaceAll("%param%", "");
//        }
        AreaVo areaVo =new AreaVo();
        areaVo.setAreaId(areaId);
        return riskWorkWarnForAllDao.getSrpriskworkwarnworkstatecnt(areaVo);
    }



    /**
     *各省公司作业风险数量统计和各省公司作业电网风险数量统计
     * warningLevel:风险等级1-5级
     * areaId：省份编码（默认全国不用传，具体下钻省份是传省份编码）
     * warnType:1作业类型；2电网风险
     * {"areaId":"","warnType":""}
     * */
    @Override
    public List<Map<String, Object>> getSrpriskworkwarnareacnt(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String areaId = JSONObject.parseObject(paramJson).getString("areaId");
//        int warnType = Integer.parseInt(JSONObject.parseObject(paramJson).getString("warnType"));
        int warnType=1;
//        SrpRiskSysTabVoPo srpRiskSysTabVoPo =new SrpRiskSysTabVoPo();
//        StringBuilder param = new StringBuilder();
//        String sql = "";
//        if(1==warnType){
//            param.append(" and a.WORKRISK_LEVEL = '06' ");
//        }else{
//            param.append(" and a.GRIDRISK_LEVEL = '09' ");
//        }
//
//
//        if (ObjectUtils.isNotEmpty(areaId)) {
//            param.append(" and a.DATAREPORT_ORG_ID = '").append(areaId).append("'");
//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLAREACNT.replaceAll("%param2%", "srg.CITY_ID").replaceAll("%param3%", areaId);
//        }else{
//            sql = Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLAREACNT.replaceAll("%param2%", "srg.DATAREPORT_ORG_ID").replaceAll("%param3%", "DATAREPORT_ORG");
//        }
//
//        sql= sql.replaceAll("%param1%", param.toString());










        return riskWorkWarnForAllDao.findSrpriskworkwarnforallareacnt(Integer.toString(warnType),areaId);
    }



    /**
     *地市作业计划列表
     * paramJson:{"page":"", "size":"", "params":{"cityID":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getSrpriskgridconstworklist(String paramJson) {

//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String cityId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("cityID");
        SrpWorkWorkplandaysVo srpWorkWorkplandaysVo =new SrpWorkWorkplandaysVo();
        srpWorkWorkplandaysVo.setCityID(cityId);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            int totalSize = this.riskWorkWarnForAllDao.getSrpriskworkplanlistcnt(srpWorkWorkplandaysVo);
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
                int offset = (page - 1) * size;
                srpWorkWorkplandaysVo.setOffset(offset);
                srpWorkWorkplandaysVo.setSize(size);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskWorkWarnForAllDao.getSrpriskworkplanlist(Const.SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKPLANLIST.replaceAll("%param%", param.toString()),cityId);
                return PageBean.<Map<String, Object>>builder()
                        .content(this.riskWorkWarnForAllDao.getSrpriskworkplanlist(srpWorkWorkplandaysVo))
                        .elementTotalSize(this.riskWorkWarnForAllDao.getSrpriskworkplanlist(srpWorkWorkplandaysVo).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }


    /**
     * 作业计划详情查询
     * workPlanId作业计划id
     * */
    @Override
    public Map<String, Object> getSrpriskworkplaninfo(String workPlanId) {
        return riskWorkWarnForAllDao.getSrpriskworkplaninfo(workPlanId);
    }

    /**
     * 视频下载
     * @param httpUrl
     * @param saveFile
     * @return
     */
//    @Override
//    public boolean  httpDownload(String httpUrl, String saveFile) throws MalformedURLException {
//        URL url = new URL(httpUrl);
//        URLConnection conn;
//        InputStream inStream = null;
//        FileOutputStream fs=null;
//
//        try {
//            //2.获取链接
//            conn = url.openConnection();
//            //3.输入流
//            inStream = conn.getInputStream();
//            //3.写入文件
//            fs = new FileOutputStream(saveFile);
//            byte[] buffer = new byte[BLOCK_SIZE];
//            int byteRead;
//            while ((byteRead = inStream.read(buffer)) != -1) {
//                fs.write(buffer, 0, byteRead);
//            }
//            inStream.close();
//            fs.close();
//            return true;
//        } catch (FileNotFoundException e) {
//            e.getMessage();
//            return false;
//        } catch (IOException e) {
//            e.getMessage();
//            return false;
//        }
//        finally {
//            if(inStream!=null){
//                safeClose(inStream);
//            }
//            if(fs!=null){
//                safeCloseOut(fs);
//            }
//        }
//    }
//
//    public static void safeClose(InputStream fis) {
//        if (fis != null) {
//            try {
//                fis.close();
//            } catch (IOException e) {
//                e.getMessage();
//            }
//        }
//    }
//
//    public static void safeCloseOut(FileOutputStream fos) {
//        if (fos != null) {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.getMessage();
//            }
//        }
//    }





}
