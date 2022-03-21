package com.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wyspace;
import com.property.mapper.WyspaceMapper;
import com.property.service.WyspaceService;
import com.property.utils.MySFTP;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WyspaceServiceImpl implements WyspaceService {

    @Autowired
    private WyspaceMapper wyspaceMapper;

    /*
    * 保洁区域列表
    * {"pageNum":"","pageSize":"","spaceName":""}
    * */
    @Override
    public Object getCleanSpace(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String spaceName = rowData.getString("spaceName");
        Integer totalSize = wyspaceMapper.getCleanSpaceCnt(spaceName);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Wyspace> cleanSpace = wyspaceMapper.getCleanSpace(spaceName, startIndex, pageSize);
            for (Wyspace wyspace : cleanSpace) {
                String ids = wyspace.getEquipmentIds();
                if( ids != null && !ids.equals("")){
                    List<Map> equipmentById = wyspaceMapper.getEquipmentById(ids);
                    List list = new ArrayList();
                    StringBuilder param = new StringBuilder();
                    for (Map map : equipmentById) {
                        param.append(map.get("name")).append(",");
                        list.add(map.get("name"));
                    }
                    String s = param.substring(0, param.length() - 1);
                    wyspace.setEquipmentIds(s);
                    wyspace.setDelFlag(ids);
                    wyspace.setList(list);
                }
            }
            pageBean.setList(cleanSpace);
        }
        return pageBean;
    }

    /*
    * 查看附件
    *  {"id":""}
    * */
    @Override
    public Object getFiles(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        String enclosureById = wyspaceMapper.getEnclosureById(id);
        List list = new ArrayList();
        if (enclosureById != null && !enclosureById.equals("")){
            String[] split = enclosureById.split(",");
            for (String s : split) {
                String path = MySFTP.getPath(s);
                list.add(path);
            }
        }
        return list;
    }


    /*
    * 新增保洁区域
    * {"spaceName":"","builtArea":"","detailedAddress":"","description":"","equipmentIds":[],"enclosure":[]}
    * */
    @Override
    public Object addCleanSpace(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String spaceName = rowData.getString("spaceName");
        String builtArea = rowData.getString("builtArea");
        String detailedAddress = rowData.getString("detailedAddress");
        String description = rowData.getString("description");
        JSONArray ids = rowData.getJSONArray("equipmentIds");
        List enclosure = rowData.getJSONArray("enclosure");
        StringBuilder sb = new StringBuilder();
        String equipmentIds = null;
        if(ids != null && ids.size() != 0){
            for (Object id : ids) {
                sb.append("'").append(id.toString()).append("',");
            }
            equipmentIds = sb.substring(0,sb.length()-1);
            List<Map> equipmentId = wyspaceMapper.getEquipmentId(equipmentIds);
            sb = new StringBuilder();
            for (Map map : equipmentId) {
                Object id = map.get("id");
                sb.append(id).append(",");
            }
            equipmentIds = sb.substring(0,sb.length()-1);
        }
        //获取附件
        String decode = MySFTP.getDecode(enclosure);
        String url = "";
        if( decode != null && !decode.equals("")){
            url = decode.substring(0,decode.length()-1);
        }
        Wyspace wyspace = new Wyspace(0,spaceName,builtArea,null,detailedAddress,description,url,equipmentIds,"0",0,null);
        wyspaceMapper.addCleanSpace(wyspace);
        return "新增成功";
    }

    /*
     * 修改保洁区域
     * {"id":"","spaceName":"","builtArea":"","detailedAddress":"","description":"","equipmentIds":[],"enclosure":[]}
     * */
    @Override
    public Object updateCleanSpace(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        Integer id = Integer.parseInt(rowData.getString("id"));
        String enclosureById = wyspaceMapper.getEnclosureById(id);
        String spaceName = rowData.getString("spaceName");
        String builtArea = rowData.getString("builtArea");
        String detailedAddress = rowData.getString("detailedAddress");
        String description = rowData.getString("description");
        JSONArray ids = rowData.getJSONArray("equipmentIds");
        List enclosure = rowData.getJSONArray("enclosure");
        StringBuilder sb = new StringBuilder();
        String equipmentIds = null;
        if(ids != null && ids.size() != 0){
            for (Object i : ids) {
                sb.append("'").append(i.toString()).append("',");
            }
            equipmentIds = sb.substring(0,sb.length()-1);
            List<Map> equipmentId = wyspaceMapper.getEquipmentId(equipmentIds);
            sb = new StringBuilder();
            for (Map map : equipmentId) {
                Object id1 = map.get("id");
                sb.append(id1).append(",");
            }
            equipmentIds = sb.substring(0,sb.length()-1);
        }
        //获取附件
        String decode = MySFTP.getDecode(enclosure);
        String url = "";
        if( decode != null && !decode.equals("")){
            url = decode.substring(0,decode.length()-1);
        }
        Wyspace wyspace = new Wyspace(id,spaceName,builtArea,null,detailedAddress,description,url,equipmentIds,"0",0,null);
        wyspaceMapper.updateCleanSpace(wyspace);
        if(enclosureById != null && !enclosureById.equals("")){
            String[] split = enclosureById.split(",");
            for (String s : split) {
                MySFTP.delete(s);
            }
        }
        return "修改成功";
    }

    /*
    * 删除保洁区域
    * {"id":""}
    * */
    @Override
    public Object deleteCleanSpace(Wyspace wyspace) {
        wyspaceMapper.deleteCleanSpace(wyspace);
        return "删除成功";
    }

    /*
    * 获取下拉框数据
    * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();
        List equipmentList = new ArrayList();
        //设备设施
        List<Map> equipment = wyspaceMapper.getEquipment();
        equipment.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("name")+"','name':'"+map.get("name")+"'}");
            equipmentList.add(jsonObject);
        });
        resultMap.put("equipment",equipmentList);
        return resultMap;
    }
}
