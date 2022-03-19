package com.raysdata.riskdataanalyzeserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nariit.rmcp.common.util.DateUtil;
import com.raysdata.riskdataanalyzeserver.bean.ImportTempVO;
import com.raysdata.riskdataanalyzeserver.bean.SheetName;
import com.raysdata.riskdataanalyzeserver.mapper.RelationinfoMapper;
import com.raysdata.riskdataanalyzeserver.service.RelationinfoService;
import com.raysdata.riskdataanalyzeserver.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.raysdata.riskdataanalyzeserver.utils.ExcelUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author dell
 */
@Service
public class RelationinfoServiceImpl implements RelationinfoService {

//    @Autowired
//    RelationinfoDao relationinfoDao;
    @Autowired
    private RelationinfoMapper mapper;
    public static final ObjectMapper mapperData = new ObjectMapper();

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 关系文件导出
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public Map<String, Object> fileImport(HttpServletRequest request, HttpServletResponse response , String data) throws IOException {
        //        int j = 1;
//        StringBuilder param = new StringBuilder();
//        //文件名称
//        String fileName = "关系模板" + ".xls";
//        //查询表数据关系表
//        List<Map<String, Object>> relationin = relationinfoDao.ServiceAll(Const.SQL_SRP_BD_KG_RELATIONINFO.replaceAll("%param%", param.toString()));
//        //第一行标题
//        List<String> titles = new ArrayList<String>();
//        //titles.add("知识图谱建模");
//        titles.add("关系名称");
//        titles.add("源类别");
//        titles.add("目标类别");
//        titles.add("关联关系类型");
//        titles.add("描述");
//        //表格数据数据
//        List<Map> varList = new ArrayList<Map>();
//        for (Map<String, Object> map : relationin) {
//            Map vpd = new HashMap();
//            vpd.put("var1", map.get("RELATION_NAME").toString());//关系名称(下发、委派、存在、包含、产生、生成
//            vpd.put("var2", map.get("SOURCE_TYPE").toString());//源类别
//            vpd.put("var3", map.get("TARGET_TYPE").toString());//目标类别
//            vpd.put("var4", map.get("RELATION_TYPE").toString());//关系类型(一对一/一对多)
//            vpd.put("var5", map.get("RELATION_DESCRIBE").toString());//关系描述
//
//            varList.add(vpd);
//        }
//        ExportExcel ex = new ExportExcel();
//        ex.export(response, fileName, titles, varList);
//        return j;
        Map<String, Object> exprotMap = new HashMap<>();
        try {
            /*List<List<Object>> newList = new ArrayList<>();
            StringBuilder limitFilter = new StringBuilder();*/
            //获取分页参数
//        JSONObject rowData = JSONObject.parseObject(data);
//            int pageSize = Integer.parseInt(JSONObject.parseObject(data).getString("size"));
//            int pageNo = Integer.parseInt(JSONObject.parseObject(data).getString("page"));
//            limitFilter.append(" LIMIT " + pageNo + "," + pageSize );
            //查询表数据关系表
//            List<Map<String, Object>> relationin = relationinfoDao.ServiceAll(Const.SQL_SRP_BD_KG_RELATIONINFO.replaceAll("%param%", limitFilter.toString()));
//            Map paramMap=new HashMap();
//            paramMap.put("limit", pageSize);
//            paramMap.put("offset", pageNo);
            List<Map<String, Object>> relationin = mapper.ServiceAll();
            BusinessLog.TYPE_EXPORT = mapperData.writeValueAsString(relationin);
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("关系模板");
            String [] titles={"名称","编码","源类别","目标类别","关联关系类型","描述","更新时间"};
            Row row= sheet.createRow(0);
            for(int i=0;i < titles.length;i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(titles[i]);
            }
            for(int i= 0;i < relationin.size() ;i++){
                row= sheet.createRow(i +1);
                Map<String, Object> relationinMap = relationin.get(i);

                Cell relationnameCell= row.createCell(0);
                relationnameCell.setCellValue(relationinMap.get("RELATION_NAME").toString());

                Cell relationcodeCell= row.createCell(1);
                relationcodeCell.setCellValue(relationinMap.get("RELATION_CODE").toString());
                Cell sourcetypeCell= row.createCell(2);
                sourcetypeCell.setCellValue(relationinMap.get("SOURCE_TYPE").toString());

                Cell ratgettypeCell= row.createCell(3);
                ratgettypeCell.setCellValue(relationinMap.get("TARGET_TYPE").toString());

                Cell relationtypeCell= row.createCell(4);
                relationtypeCell.setCellValue(relationinMap.get("RELATION_TYPE").toString());

                Cell relationdescribeCell= row.createCell(5);
                relationdescribeCell.setCellValue(relationinMap.get("RELATION_DESCRIBE").toString());

                Cell  processendtimeCell= row.createCell(6);
                processendtimeCell.setCellValue(relationinMap.get("PROCESSENDTIME").toString());
            }

            String fileName= URLEncoder.encode("关系模板表.xls","UTF-8");

            response.setContentType("application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + fileName);
            response.setHeader("filename",fileName);
            workbook.write(response.getOutputStream());

            exprotMap.put("msg","导出成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.getMessage();
            exprotMap.put("msg","导出失败");
        }
        return exprotMap;
    }

    /**
     * 关系文件导入
     * @param request
     * @param response
     * @return
     */
//    @Override
//    public Object fileRelationLead(HttpServletRequest request, HttpServletResponse response) throws Throwable {
//        int j = 0;
//        String t1 = null, t2 = null, t3 = null, t4 = null, t5 = null, t6 = null, t7=null;
//        String k1 = "关系",k2 = null ,k3 = null, k4 = null,k5 ="srp_bd_kg_relationinfo";
//        StringBuilder param = new StringBuilder();
//        MultipartFile file=null;
//        try {
//            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
//           // MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//             file = multipartRequest.getFile("file");
//        }catch (Exception e){
//            System.out.println(e);
//        }
//        InputStream in = null;
//        List<List<Object>> listob = null;
////        MultipartFile file = null;
//             if (file.isEmpty()) {
//            }
////        try {
//
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//       String filename = file.getOriginalFilename();
//        BusinessLog.Import_FILENAME=filename;
//        in = file.getInputStream();
//
//        listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
//
////        listob = new ReadExcel().getBankListByExcel(file.getOriginalFilename());
//        in.close();
//        //获取导入的条数 k2 需要更新的条数
//        k2 = String.valueOf(listob.size());
//        //查询关系导入前的数据条数
////        Integer k = relationinfoDao.serviceAlls(Const.SQL_SRP_BD_KG_RELATIONINFOCOUNTS.replaceAll("%param%", param.toString()));
//        Integer k = mapper.getAllCount();
//        k3 = String.valueOf(k);
//        if(listob !=null && !listob.isEmpty()){
//            for (int i = 0; i < listob.size(); i++) {
//                List<Object> lo = listob.get(i);
//                t2 = String.valueOf(lo.get(0));
//                t3 = String.valueOf(lo.get(1));
//                t4 = String.valueOf(lo.get(2));
//                t5 = String.valueOf(lo.get(3));
//                t6 = String.valueOf(lo.get(4));
//                //报错
////                List<Map<String, Object>> counts = relationinfoDao.getCounts(Const.SQL_DATA_MODELINGGRAPH.replaceAll("%param%", t3));
//                List<Map<String, Object>> counts=null;
//                try{
//                    counts = mapper.getList(t3);
//                }catch (Exception e){
//                    System.out.println(e);
//                }
//                t1 = counts.get(0).get("TYPE_ID").toString();
//                //生成关系编码
//                t7 = "GX";
//                NumberFormat f = new DecimalFormat("000000");
//                //查询关系数据库中的编号
////                Integer count = relationinfoDao.serviceCount(Const.SQL_SRP_BD_KG_RELATIONINFOCOUNT.replaceAll("%param%", param.toString()));
//                Integer count = mapper.getCodeCount();
//                String format = f.format(count +1);
//                t7 = t7 + format;
////                j = relationinfoDao.fileRelationLead(t1,t2,t3,t4,t5,t6,t7);
//                Map<String,Object> map=new HashMap<>();
//                map.put("t1", t1);
//                map.put("t2",t2 );
//                map.put("t3", t3);
//                map.put("t4", t4);
//                map.put("t5",t5 );
//                map.put("t6",t6 );
//                map.put("t7", t7);
//                j = mapper.fileRelationLead(map);
//            }
//        }
//
//        //记录关系导入后的总数据数据k4
////        Integer K = relationinfoDao.serviceAlls(Const.SQL_SRP_BD_KG_RELATIONINFOCOUNTS.replaceAll("%param%", param.toString()));
//        Integer K = mapper.getAllCount();;
//        k4 = String.valueOf(K);
//        //存放的位置 k5
//        //保存数据到知识融合
////        relationinfoDao.instKnowlwdgeFusion(k1,k2,k3,k4,k5);
//        Map<String,Object> map1=new HashMap<>();
//        map1.put("k1", k1);
//        map1.put("k2",k2);
//        map1.put("k3", k3);
//        map1.put("k4", k4);
//        map1.put("k5",k5);
//        mapper.instKnowlwdgeFusion(map1);
//        return j;
//    }

    /**
     * 关系文件导入
     * @param request
     * @param response
     * @return
     */
    @Transactional
    @Override
    public Map<String,String> fileRelationLead(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HashMap<String,String> resultMap = new HashMap<>();
        int j = 0;
        String t7=null;
        String k1 = "关系",k2 = null ,k3 = null, k4 = null,k5 ="srp_bd_kg_relationinfo";
        Map<String, Object> map = new HashMap<String, Object>();
        MultipartFile file=null;
        try {
            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
            // MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFile("file");
        }catch (Exception e){
            System.out.println(e);
        }
        // 判断文件是否为空
        if (!StringUtils.isEmpty(file)) {
            try {
                Map<String, Object> result = ExcelUtil.readExcel(file, ImportTempVO.class);
                List<ImportTempVO> importTempVOS = new ArrayList<>();
                if(result.get("code").equals("200")){
                    Object obj = result.get("value");
                    if (obj instanceof LinkedList<?>) {
                        for (Object o : (List<?>) obj) {
                            importTempVOS.add(ImportTempVO.class.cast(o));
                        }
                    }
                }else{
                    resultMap.put("code","500");
                    resultMap.put("msg",String.valueOf(result.get("value")));
                    return resultMap;
                }
                k2 = String.valueOf(importTempVOS.size());
                Integer k = mapper.getAllCount();
                k3 = String.valueOf(k);
                if(importTempVOS !=null && !importTempVOS.isEmpty()){
                    for (ImportTempVO importTempVO : importTempVOS) {
                        String relationName = importTempVO.getRelationName();
                        String sourceType = importTempVO.getSourceType();
                        String targetType = importTempVO.getTargetType();
                        String relationType = importTempVO.getRelationType();
                        String relationDescribe = importTempVO.getRelationDescribe();
                        int len=relationDescribe.length();
                        if(len>50){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:描述内容不能超过50");
                        }
                        if(!relationName.matches("^-?[1-9]\\d*$")){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:关系名称不正确");
                        }
                        if(!sourceType.matches("^-?[1-9]\\d*$")){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:源类别名称不正确");
                        }
                        if(!targetType.matches("^-?[1-9]\\d*$")){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:目标类别不正确");
                        }
                        if(!relationType.matches("^-?[1-9]\\d*$")){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:关联关系类型不正确");
                        }
                        if(!relationDescribe.matches("^-?[1-9]\\d*$")){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:描述不正确");
                        }

                        if(StringUtils.isEmpty(relationName)){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:关系名称不能为空");
                            return resultMap;
                        }
                        if(StringUtils.isEmpty(sourceType)){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:源类别不能为空");
                            return resultMap;
                        }
                        if(StringUtils.isEmpty(targetType)){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:目标类别不能为空");
                            return resultMap;
                        }
                        if(StringUtils.isEmpty(relationType)){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:关联关系类型不能为空");
                            return resultMap;
                        }
                        if(StringUtils.isEmpty(relationDescribe)){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:描述不能为空");
                            return resultMap;
                        }

                        List<Map<String, Object>> counts=null;
                        counts = mapper.getList(targetType);
                        String modelingGraphId = "";
                        if(CollectionUtils.isNotEmpty(counts) && counts.size() > 0){
                            modelingGraphId = counts.get(0).get("TYPE_ID").toString();
                        }
                        //生成关系编码
                        t7 = "GX";
                        NumberFormat f = new DecimalFormat("000000");
                        //查询关系数据库中的编号
                        Integer count = mapper.getCodeCount();
                        String format = f.format(count +1);
                        String relationCode = t7 + format;
                        if(StringUtils.isEmpty(relationCode)){
                            resultMap.put("code","500");
                            resultMap.put("msg","导入失败:编码不能为空");
                            return resultMap;
                        }
                        Map<String,Object> maps=new HashMap<>();
                        maps.put("modelingGraphId", modelingGraphId);
                        maps.put("relationName",relationName );
                        maps.put("relationCode", relationCode);
                        maps.put("sourceType", sourceType);
                        maps.put("targetType",targetType );
                        maps.put("relationType",relationType );
                        maps.put("relationDescribe", relationDescribe);
                        maps.put("processendTime", DateUtil.formatToString(new Date(), "yyyyMMddHHmmss"));
                        j = mapper.fileRelationLead(maps);
                        j++;
                    }
                }

                Integer K = mapper.getAllCount();;
                k4 = String.valueOf(K);
                //存放的位置 k5
                //保存数据到知识融合
                Map<String,Object> map1=new HashMap<>();
                map1.put("k1", k1);
                map1.put("k2",k2);
                map1.put("k3", k3);
                map1.put("k4", k4);
                map1.put("k5",k5);
                mapper.instKnowlwdgeFusion(map1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(j > 0){
            resultMap.put("code","200");
            resultMap.put("msg","导入成功"+j+"条");
        }else {
            resultMap.put("code","500");
            resultMap.put("msg","导入失败:系统异常");
            return resultMap;
        }

        return resultMap;
    }

    /**
     * 实例文件导入
     * @param request
     * @param response
     * @return
     */
//    @Override
//    public Object fileExample(HttpServletRequest request, HttpServletResponse response) throws Throwable {
//        int j = 0;
//        StringBuilder param = new StringBuilder();
//        String k1 = null ,k3 = "实例", k4 = null,k5=null,k6=null,k7 ="srp_bd_kg_instanceinfo";
////        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
//        InputStream in = null;
//        List<List<Object>> listob = null;
//        MultipartFile file = multipartRequest.getFile("file");
//        String fileName = file.getOriginalFilename();
////       try {
//        if (file.isEmpty()) {
//        }
////       } catch (Exception e) {
////            e.printStackTrace();
////        }
//        in = file.getInputStream();
//        //调用解读文件工具类
//        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
//        in.close();
//        //获取导入数据的类型名称
//        List<Object> objects = listob.get(0);
//        k1 = String.valueOf(objects.get(0));
//
//        //获取导入的条数
//        k4 = String.valueOf(listob.size());
//        //查询实例导入前的数据库的数据条数
////        Integer c =  relationinfoDao.serviceAllS(Const.SQL_SRP_BD_KG_KNOWLWDGE_FUSION.replaceAll("%param%", param.toString()));
//        Integer c =  mapper.serviceAlls();
//        k5 = String.valueOf(c);
//        String t1 = null, t2 = null, t3 = null, t4 = null,t5 =null;
//        if(listob!=null){
//            for (int i = 0; i < listob.size(); i++) {
//                List<Object> lo = listob.get(i);
//                t1 = String.valueOf(lo.get(0));
//                t2 = String.valueOf(lo.get(1));
//                t3 = String.valueOf(lo.get(2));
////                List<Map<String, Object>> counts = relationinfoDao.getCounts(Const.SQL_DATA_MODELINGGRAPH.replaceAll("%param%", t1));
//                List<Map<String, Object>> counts = mapper.getList(t1);;
//                t4 = counts.get(0).get("TYPE_CODE").toString();
//                //生成实例编码
//                t5 = "SL";
//                NumberFormat f = new DecimalFormat("000000");
//                //查询实例编号
////                Integer count = relationinfoDao.serviceExampleCount(Const.SQL_SRP_BD_KG_INSTANCEINFO.replaceAll("%param%", param.toString()));
//                Integer count = mapper.getInstanceCount();
//                String format = f.format(count +1);
//                t5 = t5 + format;
////                j = relationinfoDao.fileExample(t1,t2,t3,t4,t5);
//                Map<String,Object> map=new HashMap<>();
//                map.put("t1", t1);
//                map.put("t2",t2 );
//                map.put("t3", t3);
//                map.put("t4", t4);
//                map.put("t5",t5 );
//                j = mapper.fileExample(map);
//            }
//        }
//
//        //记录关系导入后的总数据数据
////        Integer g = relationinfoDao.serviceAllS(Const.SQL_SRP_BD_KG_KNOWLWDGE_FUSION.replaceAll("%param%", param.toString()));
//        Integer g =  mapper.serviceAlls();
//        k6 = String.valueOf(g);
//        //保存到知识融合表的数据
////        relationinfoDao.saveExample(k1,k2,k3,k4,k5);
//        Map<String,Object> map1=new HashMap<>();
//        map1.put("k1", k1);
//        map1.put("k3", k3);
//        map1.put("k4", k4);
//        map1.put("k5",k5);
//        map1.put("k6", k6);
//        map1.put("k7", k7);
//        mapper.saveExample(map1);
//        return j;
//    }

    /**
     * 实例文件导入
     * @param request
     * @param response
     * @return
     */
    @Transactional
    @Override
    public Object fileExample(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        int j = 0;
        StringBuilder param = new StringBuilder();
        String k1 = null ,k2 = null,k3 = "实例", k4 = null,k5=null,k6=null,k7 ="srp_bd_kg_instanceinfo";
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        InputStream in = null;
        List<List<Object>> listob = null;
        MultipartFile file = multipartRequest.getFile("file");
        String fileName = file.getOriginalFilename();
        if (file.isEmpty()) {
        }
        in = file.getInputStream();
        //调用解读文件工具类
        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        in.close();

        List<String> sheetlists= SheetName.sheetList;
        for (int i=0;i <sheetlists.size() ;i++){
            if(!sheetlists.contains("作业计划")){
                return "导入模板作业计划类型不匹配";
            }
            if(!sheetlists.contains("外包单位")){
                return "导入模板外包单位不匹配";
            }
            if(!sheetlists.contains("人员")){
                return "导入模板人员不匹配";
            }
            if(!sheetlists.contains("风险")){
                return "导入模板风险不匹配";
            }
            if(!sheetlists.contains("隐患")){
                return "导入模板隐患不匹配";
            }
            if(!sheetlists.contains("事件")){
                return "导入模板事件不匹配";
            }
            if(!sheetlists.contains("违章信息")){
                return "导入模板违章信息不匹配";
            }
        }
        //获取导入数据的类型名称
        List<Object> objects = listob.get(0);
        k1 = String.valueOf(objects.get(0));
        //获取导入的条数
        k4 = String.valueOf(listob.size());
        //查询实例导入前的数据库的数据条数
//        Integer c =  relationinfoDao.serviceAllS(Const.SQL_SRP_BD_KG_KNOWLWDGE_FUSION.replaceAll("%param%", param.toString()));
        Integer c =  mapper.serviceAlls();
        k5 = String.valueOf(c);
        String t1 = null, t2 = null, t3 = null, t4 = null,t5 =null;
        if(listob!=null){
            for (int i = 0; i < listob.size(); i++) {
                List<Object> lo = listob.get(i);
                t1 = String.valueOf(lo.get(0));
                t2 = String.valueOf(lo.get(1));
                t3 = String.valueOf(lo.get(2));
                int len= t3.length();
                if(len>50){
                    return "6";
                }
                if(!t1.matches("^-?[1-9]\\d*$")){
                    return "5";
                }
                if(t1.isEmpty()){
                    return "1";
                }
                if(t2.isEmpty()){
                    return "2";
                }
                if(t3.isEmpty()){
                    return "3";
                }
//                List<Map<String, Object>> counts = relationinfoDao.getCounts(Const.SQL_DATA_MODELINGGRAPH.replaceAll("%param%", t1));
                List<Map<String, Object>> counts = mapper.getList(t1);
                t4 = counts.get(0).get("TYPE_CODE").toString();
                if(t4.isEmpty()){
                    return "4";
                }
                //生成实例编码
                t5 = "SL";
                NumberFormat f = new DecimalFormat("000000");
                //查询实例编号
//                Integer count = relationinfoDao.serviceExampleCount(Const.SQL_SRP_BD_KG_INSTANCEINFO.replaceAll("%param%", param.toString()));
                Integer count = mapper.getInstanceCount();
                String format = f.format(count +1);
                t5 = t5 + format;
//                j = relationinfoDao.fileExample(t1,t2,t3,t4,t5);
                Map<String,Object> map=new HashMap<>();
                map.put("t1", t1);
                map.put("t2",t2 );
                map.put("t3", t3);
                map.put("t4", t4);
                map.put("t5",t5 );
                try {
                    j = mapper.fileExample(map);
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        }
        //记录关系导入后的总数据数据
//        Integer g = relationinfoDao.serviceAllS(Const.SQL_SRP_BD_KG_KNOWLWDGE_FUSION.replaceAll("%param%", param.toString()));
        Integer g =  mapper.serviceAlls();
        k6 = String.valueOf(g);
        //保存到知识融合表的数据
//        relationinfoDao.saveExample(k1,k2,k3,k4,k5);
        Map<String,Object> map1=new HashMap<>();
        map1.put("k1", k1);
        map1.put("k3", k3);
        map1.put("k4", k4);
        map1.put("k5",k5);
        map1.put("k6", k6);
        map1.put("k7", k7);
        mapper.saveExample(map1);
        return j;
    }


    /**
     * 查询知识融合列表
     * @param
     * @return
     */
    @Override
    public PageBean<Map<String, Object>> knowledge(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            Integer totalSize = mapper.getCount();
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
                Map paramMap=new HashMap();
                paramMap.put("limit",size);
                paramMap.put("offset",offset );
//                List<Map<String,Object>> listCNT = mapper.getCounts(paramMap);
                List<Map<String, Object>> list=null;
                //序列化
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                 long beginTime = System.currentTimeMillis();
                System.out.println(beginTime +"--------1-----------");
                list= (List<Map<String, Object>>)redisTemplate.opsForValue().get("rhkey"+page);
                System.out.println(System.currentTimeMillis()- beginTime +"--------读redis耗时 2-----------");
//                if(null==list){
                    //双重检测锁
                    synchronized (this){
                        //再读redis
                        long beginTime1 = System.currentTimeMillis();
                        System.out.println(beginTime1 +"--------3-----------");
                        list= (List<Map<String, Object>>)redisTemplate.opsForValue().get("rhkey"+page);
                        System.out.println(System.currentTimeMillis()-beginTime1+"--------读redis再耗时4-----------");
                        if(null== list){
                            //查询数据库
                            long beginTime2 = System.currentTimeMillis();
                            System.out.println(beginTime2+"--------5-----------");
                            list=mapper.getCounts(paramMap);
                            System.out.println(System.currentTimeMillis()-beginTime2+"--------读数据库耗时6-----------");
                            long beginTime3 = System.currentTimeMillis();
                            redisTemplate.opsForValue().set("rhkey"+page,list);
                            System.out.println(System.currentTimeMillis() - beginTime3 +"--------插入redis值耗时7-----------");
                        }else {
                        }
                        return PageBean.<Map<String, Object>>builder()
                                .content(list)
                                .elementTotalSize(totalSize)
                                .totalSize(totalSize)
                                .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                                .page(page)
                                .size(size)
                                .build();
                    }
//                }
//                return PageBean.<Map<String, Object>>builder()
//                        .content(list)
//                        .elementTotalSize(totalSize)
//                        .totalSize(totalSize)
//                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
//                        .page(page)
//                        .size(size)
//                        .build();
            }
        }
    }

    /**
     * 查询知识融合列表
     * @param
     * @return
     */
   /* @Override
    public PageBeans<SrpBdkgKnowlwdgeFusion> knowledge(Map<String,Object> map) {
        PageBeans<SrpBdkgKnowlwdgeFusion> page = new PageBeans<>();
        page.setPageNum(Integer.parseInt(map.get("page").toString()));
        page.setPageSize(Integer.parseInt(map.get("size").toString()));
        map.put("page",Integer.parseInt(map.get("page").toString()));
        int count = mapper.getCount();
        page.setTotalRecord(count);

        if (count > 0) {
            List<SrpBdkgKnowlwdgeFusion> list= mapper.getCounts(map);
            page.setList(list);
        } else {
            page.setList(new ArrayList<>());
        }
        return page;
    }*/
    /**
     *
     * EXCEL格式文件校验
     */
//    public static  FileType getFileType(InputStream is)throws IOException{
//        byte[] src = new byte[28];
//        is.read(src,0,28);
//        StringBuilder stringBuilder =new StringBuilder("");
//        if(src==null || src.length<=0){
//            return null;
//        }
//        for(int i=0;i < src.length;i++){
//            int v=src[i] & 0xFF;
//            String hv = Integer.toHexString(v).toUpperCase();
//            if(hv.length()<2){
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//
//        FileType []  fileTypes= FileType.values();
//        for(FileType fileType :  fileTypes){
//            if(stringBuilder.toString().startsWith(fileType.getValue())){
//                return fileType;
//            }
//        }
//        return null;
//    }

}



