package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StMeasurechoicedishes;
import com.canteen.mapper.StMeasurechoicedishesMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StFileNameService;
import com.canteen.service.StMeasurechoicedishesService;
import com.canteen.utils.ExportExcel;
import com.canteen.utils.ImportExcelUtil;
import com.canteen.utils.PageBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.*;

@Service
public class StMeasurechoicedishesServiceImpl implements StMeasurechoicedishesService {
    @Autowired
    private StMeasurechoicedishesMapper stMeasurechoicedishesMapper;

    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    @Autowired
    private StFileNameService stFileNameService;

    /*
    * 获取排菜列表
    * {"pageNum":"","pageSize":"","param":{"restaurantName":"","equipmentName":"","dishName":""}}
    * */
    @Override
    public PageBean choiceDishList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        String string = param.getString("restaurantName");
        String restaurantName = stOperationrestaurantService.getIds(string);
        String equipmentName = param.getString("equipmentName");
        String dishName = param.getString("dishName");
        int totalSize = stMeasurechoicedishesMapper.choiceDishCnt(restaurantName, equipmentName, dishName);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            int startIndex = pageBean.getStartIndex();
            List list = stMeasurechoicedishesMapper.choiceDishList(startIndex, pageSize, restaurantName, equipmentName, dishName);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /*
     * 删除排菜
     * {"id":""}
     * */
    @Override
    public String deleteChoiceDish(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String id = rowData.getString("id");
        int i = stMeasurechoicedishesMapper.deleteChoiceDish(id);
        if( i > 0 ){
            return "删除成功";
        }
        return "删除失败";
    }

    //查询可关联属性（计量设备，菜品名称，所属餐厅）
    @Override
    public Map getRelevance() {
       // Map<String, List> map = stOperationrestaurantService.getEquipment();
        Map map = new LinkedHashMap();
        List equipment = stOperationrestaurantMapper.getEquipment();
        List restaurant = stOperationrestaurantMapper.getRestaurant();
        List food = stMeasurechoicedishesMapper.getFood();
        map.put("equipment",equipment);
        map.put("restaurant",restaurant);
        map.put("food",food);
        return map;
    }

    /*
     * 新增排菜
     * {"meteringEquipment":"","dishName":"","restaurant":"","numbers":"","remarks":""}
     * */
    @Override
    public String insertChoiceDish(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String meteringEquipment = rowData.getString("meteringEquipment");
        String dishName = rowData.getString("dishName");
        String restaurant = rowData.getString("restaurant");
        Double numbers = Double.parseDouble(rowData.getString("numbers"));
        String remarks = rowData.getString("remarks");
        Double referenceprice = null;
        Double weight = null;
        List<Map> foodByCode = stMeasurechoicedishesMapper.getFoodByCode(dishName);
        if( foodByCode != null && foodByCode.size() != 0 ){
            Double price = Double.parseDouble(foodByCode.get(0).get("referencePrice").toString());
            Double weight1 = Double.parseDouble(foodByCode.get(0).get("weight").toString());
            referenceprice = numbers * price;
            weight = numbers * weight1;
        }
        StMeasurechoicedishes stMeasurechoicedishes = new StMeasurechoicedishes(0,meteringEquipment,dishName,restaurant,numbers.toString(),
                weight.toString(),referenceprice,remarks,"0");
        int i = stMeasurechoicedishesMapper.insertChoiceDish(stMeasurechoicedishes);
        if( i > 0 ){
            return "新增成功";
        }
        return "新增失败";
    }

    /*
     * 修改排菜
     * {"id":"","meteringEquipment":"","dishName":"","restaurant":"","numbers":"","remarks":""}
     * */
    @Override
    public String updateChoiceDish(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.parseInt(rowData.getString("id"));
        String meteringEquipment = rowData.getString("meteringEquipment");
        String dishName = rowData.getString("dishName");
        String restaurant = rowData.getString("restaurant");
        Double numbers = Double.parseDouble(rowData.getString("numbers"));
        String remarks = rowData.getString("remarks");
        Double referenceprice = null;
        Double weight = null;
        List<Map> foodByCode = stMeasurechoicedishesMapper.getFoodByCode(dishName);
        if( foodByCode != null && foodByCode.size() != 0 ){
            Double price = Double.parseDouble(foodByCode.get(0).get("referencePrice").toString());
            Double weight1 = Double.parseDouble(foodByCode.get(0).get("weight").toString());
            referenceprice = numbers * price;
            weight = numbers * weight1;
        }
        StMeasurechoicedishes stMeasurechoicedishes = new StMeasurechoicedishes(id,meteringEquipment,dishName,restaurant,numbers.toString(),
                weight.toString(),referenceprice,remarks,"0");
        int i = stMeasurechoicedishesMapper.updateChoiceDish(stMeasurechoicedishes);
        if( i > 0 ){
            return "修改成功";
        }
        return "修改失败";
    }

    /*
     * 导出排菜列表
     * {"param":{"restaurantName":"","equipmentName":"","dishName":""}}
     * */
    @Transactional
    @Override
    public Object choiceDishExport(HttpServletRequest request, HttpServletResponse response, String paramJson) throws UnsupportedEncodingException {
        //文件名称
        String fileName = "菜品"+".xls";
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String string = param.getString("restaurantName");
        String restaurantName = stOperationrestaurantService.getIds(string);
        String equipmentName = param.getString("equipmentName");
        String dishName = param.getString("dishName");
        List<StMeasurechoicedishes> list = stMeasurechoicedishesMapper.ExportChoiceDishList(restaurantName, equipmentName, dishName);
        //第一行标题
        List<String> titles = new ArrayList<String>();
        titles.add("计量设备");
        titles.add("菜品名称");
        titles.add("所属餐厅");
        titles.add("数量（份）");
        titles.add("重量（g）");
        titles.add("参考价格");
        titles.add("备注");
        //表格数据数据
        List<Map> varList = new ArrayList<Map>();
        for (StMeasurechoicedishes stMeasurechoicedishes : list) {
            Map vpd = new LinkedHashMap();
            vpd.put("var1", stMeasurechoicedishes.getMeteringEquipment());//计量设备
            vpd.put("var2", stMeasurechoicedishes.getDishName());//菜品名称
            vpd.put("var3", stMeasurechoicedishes.getRestaurant());//所属餐厅
            vpd.put("var4", stMeasurechoicedishes.getNumbers());//数量
            vpd.put("var5", stMeasurechoicedishes.getWeight());//重量
            vpd.put("var6", stMeasurechoicedishes.getReferencePrice());//参考价格
            vpd.put("var7", stMeasurechoicedishes.getRemarks());//备注

            varList.add(vpd);
        }
        ExportExcel ex = new ExportExcel();
        ex.export(response, fileName, titles, varList);
        return 1;
    }

    //导入排菜列表
    @Override
    public Object choiceDishImport(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int j = 0;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        InputStream in = null;
        List<List<Object>> listob = null;
        MultipartFile file = multipartRequest.getFile("file");
        //获取文件名
        String fileName = file.getOriginalFilename();
//        System.out.println("文件名-fileNames" + fileName);
        //存到到本地
       stFileNameService.instName(fileName);
        try {
            if (file.isEmpty()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        in = file.getInputStream();
        listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
        in.close();
        String meteringEquipment = null, dishName = null, restaurant = null, numbers = null,remarks =null;
        Double referenceprice = null,weight = null;
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            meteringEquipment = String.valueOf(lo.get(0));
            dishName = String.valueOf(lo.get(1));
            restaurant = String.valueOf(lo.get(2));
            numbers = String.valueOf(lo.get(3));
            remarks = String.valueOf(lo.get(4));
            Integer count = stMeasurechoicedishesMapper.getCntByCode(dishName);
            if( count != 0 ){
                List<Map> foodByCode = stMeasurechoicedishesMapper.getFoodByCode(dishName);
                Double price = Double.parseDouble(foodByCode.get(0).get("referencePrice").toString());
                Double weight1 = Double.parseDouble(foodByCode.get(0).get("weight").toString());
                referenceprice = Double.parseDouble(numbers) * price;
                weight = Double.parseDouble(numbers) * weight1;
                StMeasurechoicedishes stMeasurechoicedishes = new StMeasurechoicedishes(0,meteringEquipment,dishName,restaurant,numbers,
                        weight.toString(),referenceprice,remarks,"0");
                int i1 = stMeasurechoicedishesMapper.insertChoiceDish(stMeasurechoicedishes);
                if( i1 > 0 ){
                    j++;
                }
            }
        }
        return "成功导入"+j+"条数据";
    }

    @Override
    public List<String> getfileName() {
        return stFileNameService.selsctName();
    }
}
