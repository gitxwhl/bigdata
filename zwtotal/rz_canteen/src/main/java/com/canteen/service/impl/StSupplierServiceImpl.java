package com.canteen.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.*;
import com.canteen.mapper.StSupplierMapper;
import com.canteen.service.StSupplierService;
import com.canteen.utils.ExportExcel;
import com.canteen.utils.MySFTP;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StSupplierServiceImpl implements StSupplierService {
    private static final int allYear = 0;
    private static final int thisYear = 1;
    private static final int lastYear = 2;
    private static final String eligible = "0"; //符合条件的供应商
    private static final String alternative = "1"; //备选的供应商
    private static final String designate = "2"; //选定的供应商
    public static List fileList = new ArrayList();

    @Autowired
    private StSupplierMapper supplierMapper;

    /*
    *供应商列表
    * {"pageNum":"","pageSize":"","name":"","number":"","code":"","count":""}
    * */
    @Override
    public Object getSupplier(String paramJson)  {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String name = rowData.getString("name");
        String number = rowData.getString("number");
        String code = rowData.getString("code");
        String count = rowData.getString("count");
        Integer totalSize = supplierMapper.getSupplierCnt(name, number, code, count);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> supplier = supplierMapper.getSupplier(name, number, code, count, startIndex, pageSize,"1");
            /*for (Map map : supplier) {
                //查询准入状态
                Object beginTime = map.get("beginTime");
                Object endTime = map.get("endTime");
                String access = selectAccessState(beginTime, endTime);
                //查询积分
                Object id = map.get("id");
                int score = getScore(Integer.parseInt(id.toString()), thisYear);
                //查询资质文件
                List list = new ArrayList();
                Object certificates = map.get("certificates");
                if(certificates != null && !certificates.equals("")){
                    String[] split = certificates.toString().split(",");
                    list = Arrays.asList(split);
                }
                //查询项目法人
                List<Map> persons = supplierMapper.getPerson(Integer.parseInt(id.toString()));
                for (Map person : persons) {
                    List list1 = new ArrayList();
                    Object files = person.get("files");
                    if(files != null && !files.equals("")){
                        String[] split = files.toString().split(",");
                        list1 = Arrays.asList(split);
                    }
                    person.put("fileList",list1);
                }

                map.put("score",score);
                map.put("access", access);
                map.put("fileList",list);
                map.put("persons",persons);

            }*/
            pageBean.setList(supplier);
        }
        return pageBean;
    }

    /**
     * 导出供应商
     * @param map
     * {"name":"","number":"","code":"","count":""}
     * @return
     */
    @Override
    public Object exportSupplier(HttpServletResponse response,Map<String, Object> map) throws UnsupportedEncodingException {
        String name = map.get("name") == null ? "" : map.get("name").toString();
        String number = map.get("number") == null ? "" : map.get("number").toString();
        String code = map.get("code") == null ? "" : map.get("code").toString();
        String count = map.get("count") == null ? "" : map.get("count").toString();
        List<Map> supplier = supplierMapper.getSupplier(name, number, code, count, 0, 0,null);
        //文件名称
        String fileName = "供应商"+".xls";
        //第一行标题
        List<String> titles = new ArrayList<String>();
        titles.add("供应商名称");
        titles.add("供应商编号");
        titles.add("统一社会信用代码");
        titles.add("成立时间");
        titles.add("企业地址");
        titles.add("营业执照有效期");
        titles.add("法人代表");
        titles.add("法人电话");
        titles.add("法人身份证号");
        titles.add("采购食材数量");
        titles.add("剩余应付账款");
        //表格数据数据
        List<Map> varList = new ArrayList<Map>();
        for (Map map1 : supplier) {
            Map vpd = new LinkedHashMap();
            vpd.put("var1", map1.get("NAME"));//供应商名称
            vpd.put("var2", map1.get("number"));//供应商编号
            vpd.put("var3", map1.get("CODE"));//统一社会信用代码
            vpd.put("var4", map1.get("establishTime"));//成立时间
            vpd.put("var5", map1.get("address"));//企业地址
            vpd.put("var6", map1.get("businessvalidity"));//营业执照有效期
            vpd.put("var7", map1.get("representative"));//法人代表
            vpd.put("var8", map1.get("phone"));//法人电话
            vpd.put("var9", map1.get("idCard"));//法人身份证号
            vpd.put("var10", map1.get("cnt"));//采购食材数量
            vpd.put("var11", map1.get("payablesurplus"));//剩余应付账款

            varList.add(vpd);
        }
        ExportExcel ex = new ExportExcel();
        ex.export(response, fileName, titles, varList);
        return "导出成功";
    }

    /*
    * 新增准入供应商
    * {"param":{"name":"","number":"","code":"","establishTime":"","registeredCapital":"","address":"","businessValidity":"",
    * "representative":"","phone":"","idCard":"","beginTime":"","endTime":"","reviewResults":"","explain":"","files":[]}},
    * "param1":[{"person":"","entryName":"","scale":"","scope":"","period":"","files":[]}]}
    * */
    @Transactional
    @Override
    public Object addSupplier(String paramJson) {
        List list = new ArrayList();
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String url = "";
        List files= param.getJSONArray("files");
        String decode = MySFTP.getDecode(files);
        if( decode != null && !decode.equals("")){
            url = decode.substring(0,decode.length()-1);
        }
        list.add(MySFTP.newFileNameList);
        StSupplier stSupplier = new StSupplier();
        stSupplier.setName(param.getString("name"));
        stSupplier.setNumber(param.getString("number"));
        stSupplier.setCode(param.getString("code"));
        stSupplier.setEstablishTime(param.getString("establishTime"));
        stSupplier.setRegisteredCapital(param.getString("registeredCapital"));
        stSupplier.setAddress(param.getString("address"));
        stSupplier.setBusinessValidity(param.getString("businessValidity"));
        stSupplier.setRepresentative(param.getString("representative"));
        stSupplier.setPhone(param.getString("phone"));
        stSupplier.setIdCard(param.getString("idCard"));
        stSupplier.setBeginTime(param.getString("beginTime"));
        stSupplier.setEndTime(param.getString("endTime"));
        stSupplier.setReviewResults(param.getString("reviewResults"));
        stSupplier.setExplains(param.getString("explain"));
        stSupplier.setCertificates(url);
        stSupplier.setSelection("0");
        stSupplier.setPayableSurplus("0");
        stSupplier.setState("0");
        supplierMapper.addSupplier(stSupplier);

        Integer supplierId = stSupplier.getId();
        List param1 = rowData.getJSONArray("param1");
        for (Object o : param1) {
            JSONObject jsonObject = (JSONObject)o;
            StCertificate stCertificate = new StCertificate();
            stCertificate.setPerson(jsonObject.getString("person"));
            stCertificate.setEntryName(jsonObject.getString("entryName"));
            stCertificate.setScale(jsonObject.getString("scale"));
            stCertificate.setScope(jsonObject.getString("scope"));
            stCertificate.setPeriod(jsonObject.getString("period"));
            stCertificate.setSupplierId(supplierId);
            List files1 = jsonObject.getJSONArray("files");
            String decode1 = MySFTP.getDecode(files1);
            if( decode1 != null && !decode1.equals("")){
                url = decode1.substring(0,decode1.length()-1);
            }
            list.add(MySFTP.newFileNameList);
            stCertificate.setFiles(url);
            supplierMapper.addCertificate(stCertificate);
        }
        fileList = list;
        System.out.println(fileList);
        return "添加成功";
    }

    /**
     * 采购食材详情
     * @param
     * @return
     */
    @Override
    public Object getIngredient(Map<String,Object> map) {
        return null;
    }

    /**
     * 添加食材
     * @param map
     * @return
     */
    @Override
    public Object addIngredient(Map<String,Object> map) {
        return null;
    }

    /*
     * 编辑供应商
     * {"param":{"id":"","name":"","number":"","code":"","establishTime":"","registeredCapital":"","address":"","businessValidity":"",
     * "representative":"","phone":"","idCard":"","beginTime":"","endTime":"","reviewResults":"","explain":"","files":[]}},
     * "param1":[{"person":"","entryName":"","scale":"","scope":"","period":""}]}
     * */
    /*@Transactional
    @Override
    public Object updateSupplier(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String url = "";
        List files= param.getJSONArray("files");
        String decode = MySFTP.getDecode(files);
        if( decode != null && !decode.equals("")){
            url = decode.substring(0,decode.length()-1);
        }
        StSupplier stSupplier = new StSupplier();
        int id = Integer.parseInt(param.getString("id"));
        //通过id查询附件
        String certificates = supplierMapper.getCertificates(id);
        stSupplier.setId(id);
        stSupplier.setName(param.getString("name"));
        stSupplier.setNumber(param.getString("number"));
        stSupplier.setCode(param.getString("code"));
        stSupplier.setEstablishTime(param.getString("establishTime"));
        stSupplier.setRegisteredCapital(param.getString("registeredCapital"));
        stSupplier.setAddress(param.getString("address"));
        stSupplier.setBusinessValidity(param.getString("businessValidity"));
        stSupplier.setRepresentative(param.getString("representative"));
        stSupplier.setPhone(param.getString("phone"));
        stSupplier.setIdCard(param.getString("idCard"));
        stSupplier.setBeginTime(param.getString("beginTime"));
        stSupplier.setEndTime(param.getString("endTime"));
        stSupplier.setReviewResults(param.getString("reviewResults"));
        stSupplier.setExplains(param.getString("explain"));
        stSupplier.setCertificates(url);
        supplierMapper.updateSupplier(stSupplier);
        String[] split = certificates.split(",");
        for (String s : split) {
            MySFTP.delete(s);
        }

        return "变更成功";
    }*/

    /*
     * 供应商遴选列表
     * {"name":"","number":"","address":"","establishTime":"","startMoney":"","endMoney":"",
     * "lastPenalty":"","thisPenalty":"","startScore":"","endScore":""}
     * */
    /*@Override
    public Object getChooseList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String name = rowData.getString("name");   //供应商名称
        String number = rowData.getString("number");    //供应商编号
        String address = rowData.getString("address");  //企业地址
        String establishTime = rowData.getString("establishTime");  //成立时间
        String startMoney = rowData.getString("startMoney");    //注册资本（开始）
        String endMoney = rowData.getString("endMoney");    //注册资本（结束）
        String lastPenalty = rowData.getString("lastPenalty");  //上年度是否被惩罚
        String thisPenalty = rowData.getString("thisPenalty");  //本年度是否被惩罚
        String startScore = rowData.getString("startScore");  //上年度积分（开始）
        String endScore = rowData.getString("endScore");  //上年度积分（结束）

        Map resultMap = new LinkedHashMap();
        //符合条件的供应商
        List<Map> eligibles = supplierMapper.getEligible(name, number, address, establishTime, startMoney, endMoney, lastPenalty, thisPenalty,eligible);
        for (int i = 0; i < eligibles.size(); i++) {
            Map map = eligibles.get(i);
            Object beginTime = map.get("beginTime");
            Object endTime = map.get("endTime");
            //查询准入状态
            String access = selectAccessState(beginTime, endTime); //准入状态
            Object id = map.get("id");
            //查询上年度积分
            int score = getScore(Integer.parseInt(id.toString()), lastYear);
            if( startScore != null && !startScore.equals("") && endScore != null && !endScore.equals("") ){
                if( !(score >= Integer.parseInt(startScore) && score <= Integer.parseInt(endScore))) {
                    eligibles.remove(i);
                }
            }
            map.put("score",score);
            map.put("access",access);
        }*/

        //备选的供应商
        /*List<Map> alternatives = supplierMapper.getEligible(name, number, address, establishTime, startMoney, endMoney, lastPenalty, thisPenalty, alternative);
        for (int i = 0; i < alternatives.size(); i++) {
            Map map = alternatives.get(i);
            Object beginTime = map.get("beginTime");
            Object endTime = map.get("endTime");
            //查询准入状态
            String access = selectAccessState(beginTime, endTime); //准入状态
            Object id = map.get("id");
            //查询上年度积分
            int score = getScore(Integer.parseInt(id.toString()), lastYear);
            if( startScore != null && !startScore.equals("") && endScore != null && !endScore.equals("") ){
                if( !(score >= Integer.parseInt(startScore) && score <= Integer.parseInt(endScore))) {
                    alternatives.remove(i);
                }
            }
            map.put("score",score);
            map.put("access",access);
        }

        //选定的供应商
        List<Map> designates = supplierMapper.getEligible(name, number, address, establishTime, startMoney, endMoney, lastPenalty, thisPenalty, designate);
        for (int i = 0; i < designates.size(); i++) {
            Map map = designates.get(i);
            Object beginTime = map.get("beginTime");
            Object endTime = map.get("endTime");
            //查询准入状态
            String access = selectAccessState(beginTime, endTime); //准入状态
            Object id = map.get("id");
            //查询上年度积分
            int score = getScore(Integer.parseInt(id.toString()), lastYear);
            if( startScore != null && !startScore.equals("") && endScore != null && !endScore.equals("") ){
                if( !(score >= Integer.parseInt(startScore) && score <= Integer.parseInt(endScore))) {
                    designates.remove(i);
                }
            }
            map.put("score",score);
            map.put("access",access);
        }
        resultMap.put("fh",eligibles);
        resultMap.put("bx",alternatives);
        resultMap.put("xd",designates);
        return resultMap;
    }*/

    /*
    * 供应商遴选
    * {"id":"","state":""}
    * */
    /*@Transactional
    @Override
    public Object choose(StSupplier stSupplier) {

        String state = stSupplier.getState();
        if(state.equals("1")){ //添加备选
            supplierMapper.choose(stSupplier);
            return "添加备选成功";
        }
        if(state.equals("2")){  //选定
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String format = sdf.format(date);
            stSupplier.setIncludedTime(format);
            supplierMapper.choose(stSupplier);

            String selection = supplierMapper.getSelection(stSupplier);
            int i = Integer.parseInt(selection);
            stSupplier.setSelection(String.valueOf(i+1));
            supplierMapper.updateSelection(stSupplier);
            return "选定成功";
        }
        return "传递数据不正确";
    }*/

    /*
    *供应商评价
    * {"id":""}
    * */
    /*@Override
    public Object getEvaluate(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        //历史记录
        List<Map> history = supplierMapper.getHistory(id);
        for (Map map : history) {
            List list = new ArrayList();
            Object enclosure = map.get("enclosure");
            if(enclosure != null && !enclosure.equals("")){
                String[] split = enclosure.toString().split(",");
                for (String s : split) {
                    list.add(MySFTP.getPath(s));
                }
            }
            map.put("files",list);
        }
        //本年度黑名单
        List<Map> blacklist = supplierMapper.getBlacklist(id,thisYear);
        for (Map map : blacklist) {
            List list = new ArrayList();
            Object enclosure = map.get("enclosure");
            if(enclosure != null && !enclosure.equals("")){
                String[] split = enclosure.toString().split(",");
                for (String s : split) {
                    list.add(MySFTP.getPath(s));
                }
            }
            map.put("files",list);
        }
        //累积积分
        int score = getScore(id, allYear);
        Map map = new LinkedHashMap();
        map.put("history",history);
        map.put("blacklist",blacklist);
        map.put("score",score);
        return map;
    }*/

    /*
    * 添加评分
    * {"timeScore":"","releaseTime":"","issued":"","scoring":"","supplierId":"","files":[]}
    * */
    /*@Override
    public Object addScore(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        StSupplierScore supplierScore = new StSupplierScore();
        supplierScore.setTimeScore(rowData.getString("timeScore"));
        supplierScore.setReleaseTime(rowData.getString("releaseTime"));
        supplierScore.setIssued(rowData.getString("issued"));
        supplierScore.setScoring(rowData.getString("scoring"));
        supplierScore.setSupplierId(Integer.parseInt(rowData.getString("supplierId")));
        List files = rowData.getJSONArray("files");
        String decode = MySFTP.getDecode(files);
        String url = "";
        if( decode != null && !decode.equals("")){
            url = decode.substring(0,decode.length()-1);
        }
        supplierScore.setEnclosure(url);
        supplierMapper.addScore(supplierScore);
        return "添加成功";
    }*/

    /*
    * 惩罚详情
    * {"id":""}
    * */
    /*@Override
    public Object getPunishment(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        Map resultMap = new LinkedHashMap();
        List<Map> punishment = supplierMapper.getPunishment(id);
        for (Map map : punishment) {
            List list = new ArrayList();
            Object enclosure = map.get("enclosure");
            if(enclosure != null && !enclosure.equals("")){
                String[] split = enclosure.toString().split(",");
                for (String s : split) {
                    list.add(MySFTP.getPath(s));
                }
            }
            map.put("files",list);
        }
        resultMap.put("punishment",punishment);
        return resultMap;
    }*/

    /*
    * 添加惩罚
    * {"issued":"","releaseTime":"","reason":"","deductionPoints":"","fine":"","otherPenaltie":"","supplierId":"","files":[]}
    * */
    /*@Override
    public Object addPunishment(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        StPenaltyList stPenaltyList = new StPenaltyList();
        stPenaltyList.setIssued(rowData.getString("issued"));
        stPenaltyList.setReleaseTime(rowData.getString("releaseTime"));
        stPenaltyList.setReason(rowData.getString("reason"));
        stPenaltyList.setDeductionPoints(rowData.getString("deductionPoints"));
        stPenaltyList.setFine(rowData.getString("fine"));
        stPenaltyList.setOtherPenaltie(rowData.getString("otherPenaltie"));
        stPenaltyList.setSupplierId(Integer.parseInt(rowData.getString("supplierId")));
        List files = rowData.getJSONArray("files");
        String decode = MySFTP.getDecode(files);
        String url = "";
        if( decode != null && !decode.equals("")){
            url = decode.substring(0,decode.length()-1);
        }
        stPenaltyList.setEnclosure(url);
        supplierMapper.addPunishment(stPenaltyList);
        return "添加成功";
    }*/

    /*
    * 黑名单详情
    * {"id":""}
    * */
    /*@Override
    public Object getBlacklist(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        Map resultMap = new LinkedHashMap();
        List<Map> blacklist = supplierMapper.getBlacklist(id, allYear);
        for (Map map : blacklist) {
            List list = new ArrayList();
            Object enclosure = map.get("enclosure");
            if(enclosure != null && !enclosure.equals("")){
                String[] split = enclosure.toString().split(",");
                for (String s : split) {
                    list.add(MySFTP.getPath(s));
                }
            }
            map.put("files",list);
        }
        resultMap.put("blacklist",blacklist);
        return resultMap;
    }*/

    /*
    * 添加黑名单
    * {"issued":"","releaseTime":"","relieveTime":"","reason":"","supplierId":"","files":[]}
    * */
    /*@Override
    public Object addBlacklist(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        StBlacklist blacklist = new StBlacklist();
        blacklist.setIssued(rowData.getString("issued"));
        blacklist.setReleaseTime(rowData.getString("releaseTime"));
        blacklist.setRelieveTime(rowData.getString("relieveTime"));
        blacklist.setReason(rowData.getString("reason"));
        blacklist.setSupplierId(Integer.parseInt(rowData.getString("supplierId")));
        List files = rowData.getJSONArray("files");
        String decode = MySFTP.getDecode(files);
        String url = "";
        if( decode != null && !decode.equals("")){
            url = decode.substring(0,decode.length()-1);
        }
        blacklist.setEnclosure(url);
        supplierMapper.addBlacklist(blacklist);
        return "添加成功";
    }*/


    //查询总积分
    /*public int getScore(int id,int timeType){
        int addScore = 0;
        int subScore = 0;
        //查询获得积分
        Integer scoreCnt = supplierMapper.getScoreCnt(id, timeType);
        if( scoreCnt != 0 ){
            List<Map> scoreList = supplierMapper.getScore(id, timeType);
            for (Map map : scoreList) {
                Object timescore = map.get("timescore");
                if( timescore != null && !timescore.equals("")){
                    addScore = addScore + Integer.parseInt(timescore.toString());
                }
            }
        }
        //查询扣除积分
        Integer integer = supplierMapper.deductScoreCnt(id, timeType);
        if( integer != 0 ){
            List<Map> deductScores = supplierMapper.deductScore(id, timeType);
            for (Map map : deductScores) {
                Object deductionpoints = map.get("deductionpoints");
                if( deductionpoints != null && !deductionpoints.equals("")){
                    subScore = subScore + Integer.parseInt(deductionpoints.toString());
                }
            }
        }
        //所获得总积分
        int totalScore = addScore - subScore;
        return totalScore;
    }*/

    //查询准入状态
    /*public String selectAccessState(Object beginTime,Object endTime)  {
        String access = "无效";
        try {
            if (beginTime != null && !beginTime.equals("") && endTime != null && !endTime.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date bTime = null;
                bTime = sdf.parse(beginTime.toString());
                Date eTime = sdf.parse(endTime.toString());
                Date date = new Date();
                if (date.getTime() >= bTime.getTime() && date.getTime() <= eTime.getTime()) {
                    access = "有效";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return access;
    }*/
}
