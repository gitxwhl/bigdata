package com.canteen.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StDietaryStructureVo;
import com.canteen.entity.StfoodManagement;
import com.canteen.entity.StnutritionalComponents;
import com.canteen.entity.bo.FoodManagement;
import com.canteen.mapper.StfoodManagementMapper;
import com.canteen.service.StfoodManagementService;
import com.canteen.utils.ExportExcel;
import com.canteen.utils.MySFTP;
import com.canteen.utils.PageBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Encoder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


@Service
public class StfoodManagementServiceImpl implements StfoodManagementService {

    @Autowired
    StfoodManagementMapper stfoodManagementMapper;

    /**
     * 查询可用食材
     * @return
     */
    @Override
    public Object getIngredient() {
        List<Map> ingredient = stfoodManagementMapper.getIngredient();
        return ingredient;
    }

    /**
     * 根据食材id查询营养成分
     * @param map   {"id":""}
     * @return
     */
    @Override
    public Object getNutritionById(Map<String, Object> map) {
        Integer id = Integer.parseInt(map.get("id").toString());
        List<Map> nutritionById = stfoodManagementMapper.getNutritionById(id);
        return nutritionById;
    }

    /**
     * 新增菜品
     * @param foodManagement
     * {"stfoodManagement":{"dishName":"","dishCategory":"","referencePrice":"","company":"","foodPictures":"",
     *  "dishesCategory":"","meals":"","taste":"","degree":"","feature":""},"components":{"energy":"","protein":"","fat":"",
     *  "saturated":"","cholesterol":"","carbohydrate":"","dietaryfiber":"","vitaminA":"","vitaminD":"","vitaminE":"",
     *  "vitaminK":"","vitaminB1":"","vitaminB2":"","vitaminB6":"","vitamin16":"","vitaminC":"","nicotinicacid":"",
     *  "folicacid":"","pantothenicacid":"","biotin":"","choline":"","calcium":"","phosphorus":"","potassium":"","sodium":"",
     *  "magnesium":"","iron":"","zinc":"","iodine":"","selenium":"","copper":"","fluorine":"","chromium":"","manganese":"",
     *  "molybdenum":""},"param":[{"ingredientId":"","weight":""}]}
     * @return
     */
    @Override
    @Transactional
    public String insertFood(FoodManagement foodManagement) {
        //获取菜品编号
        String dishCode = stfoodManagementMapper.getDishCode();
        //菜品重量
        Double dishWeight = 0.0;

        //添加菜品和配料的关联数据
        List<Map> list = foodManagement.getParam();
        for (Map map : list) {
            Double weight = (map.get("weight") == null || map.get("weight").equals("")) ? 0.0 : Double.parseDouble(map.get("weight").toString());
            dishWeight = dishWeight + weight;

            map.put("dishCode",dishCode);
            stfoodManagementMapper.insertNutritionDishes(map);
        }

        //添加菜品表
        StfoodManagement stfoodManagement = foodManagement.getStfoodManagement();
        stfoodManagement.setDishCode(dishCode);
        //图片的base64码
        String foodPictures = stfoodManagement.getFoodPictures();
        byte[] inputStream = MySFTP.getInputStream(foodPictures);
        stfoodManagement.setPic(inputStream);
        stfoodManagement.setWeight(dishWeight.toString());
        stfoodManagementMapper.insertFoodInfo(stfoodManagement);

        //添加菜品营养成分
        StnutritionalComponents components = foodManagement.getComponents();
        components.setCode(dishCode);
        stfoodManagementMapper.insertFoodElement(components);

        return "新增菜品成功";
    }

    /**
     * 查询关联属性（下拉框数据）
     * @return
     */
    @Override
    public Object getRelevance() {
        Map resultMap = new LinkedHashMap();

        //菜品分类
        List<JSONObject>  dishesCategory = new ArrayList<>();

        //餐别
        List<JSONObject>  meals = new ArrayList<>();

        //口味
        List<JSONObject>  taste = new ArrayList<>();

        //菜品类别
        List<Map> category = stfoodManagementMapper.getType();

        List<Map> dictionaries = stfoodManagementMapper.dictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("taste".equals(map.get("paraname"))){
                taste.add(jsonObject);
            }
            if("dishescategory".equals(map.get("paraname"))){
                dishesCategory.add(jsonObject);
            }
            if("mealtype".equals(map.get("paraname"))){
                meals.add(jsonObject);
            }
        });

        resultMap.put("dishesCategory",dishesCategory);
        resultMap.put("meals",meals);
        resultMap.put("taste",taste);
        resultMap.put("category",category);
        return resultMap;
    }

    /**
     * 菜品查询
     * @param map
     * {"pageNum":"","pageSize":"","msg":"","date":"","meals":"","dishesCategory":"","category":""}
     * @return
     */
    @Override
    public Object getFood(Map<String, Object> map) {
        Integer pageNum = Integer.parseInt(map.get("pageNum").toString());
        Integer pageSize = Integer.parseInt(map.get("pageSize").toString());
        String msg = map.get("msg") == null ? "" : map.get("msg").toString();
        String date = map.get("date") == null ? "" : map.get("date").toString();
        String meals = map.get("meals") == null ? "" : map.get("meals").toString();
        String category = map.get("category") == null ? "" : map.get("category").toString();
        String dishesCategory = map.get("dishesCategory") == null ? "" : map.get("dishesCategory").toString();
        Integer totalSize = stfoodManagementMapper.getFoodCnt(msg, category, dishesCategory, date, meals);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> food = stfoodManagementMapper.getFood(msg, category, dishesCategory, date, meals, startIndex, pageSize);
            for (Map map1 : food) {
                Object pictureUrl = map1.get("foodpictures");
                String picture = MySFTP.getPicture(pictureUrl);
                map1.put("foodpictures",picture);
            }
            pageBean.setList(food);
        }
        return pageBean;
    }

    /*
     * 菜品营养成分分析
     * {"dishcode":""}
     * */
    @Override
    public Object foodAnalyze(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String dishcode = rowData.getString("dishcode");
        List<Map> list = stfoodManagementMapper.foodAnalyze(dishcode);
        List list1 = getNutritional(list);
        return list1;
    }

    //获取菜品营养成分
    public static List getNutritional(List<Map> list){
        List list1 = new ArrayList();

        //创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        if ( list != null && list.size() != 0){
            //能量
            Object energy = list.get(0).get("energy");
            String energyPro = "0";
            if(energy != null && !energy.equals("")){
                //能量占比
                energyPro = numberFormat.format((float) Integer.parseInt(energy.toString()) / (float) 8400 * 100);
            }else {
                energy = 0;
            }

            //蛋白质
            Object protein = list.get(0).get("protein");
            String proteinPro = "0";
            if(protein != null && !protein.equals("")){
                //蛋白质占比
                proteinPro = numberFormat.format((float) Integer.parseInt(protein.toString()) / (float) 60 * 100);
            }else {
                protein = 0;
            }

            //脂肪
            Object fat = list.get(0).get("fat");
            String fatPro = "0";
            if(fat != null && !fat.equals("")){
                //脂肪占比
                fatPro = numberFormat.format((float) Integer.parseInt(fat.toString()) / (float) 60 * 100);
            }else {
                fat = 0;
            }

            //碳水化合物
            Object carbohydrate = list.get(0).get("carbohydrate");
            String carbohydratePro = "0";
            if(carbohydrate != null && !carbohydrate.equals("")){
                //碳水化合物占比
                carbohydratePro = numberFormat.format((float) Integer.parseInt(carbohydrate.toString()) / (float) 300 * 100);
            }else {
                carbohydrate = 0;
            }

            //膳食纤维
            Object dietaryfiber = list.get(0).get("dietaryfiber");
            String dietaryfiberPro = "0";
            if(dietaryfiber != null && !dietaryfiber.equals("")){
                //膳食纤维占比
                dietaryfiberPro = numberFormat.format((float) Integer.parseInt(dietaryfiber.toString()) / (float) 25 * 100);
            }else {
                dietaryfiber = 0;
            }

            //钠
            Object sodium = list.get(0).get("sodium");
            String sodiumPro = "0";
            if(sodium != null && !sodium.equals("")){
                //钠占比
                sodiumPro = numberFormat.format((float) Integer.parseInt(sodium.toString()) / (float) 2000 * 100);
            }else {
                sodium = 0;
            }

            //饱和脂肪酸
            Object saturated = list.get(0).get("saturated");
            String saturatedPro = "0";
            if(saturated != null && !saturated.equals("")){
                saturatedPro = numberFormat.format((float) Integer.parseInt(saturated.toString()) / (float) 20 * 100);
            }else {
                saturated = 0;
            }

            //胆固醇
            Object cholesterol = list.get(0).get("cholesterol");
            String cholesterolPro = "0";
            if(cholesterol != null && !cholesterol.equals("")){
                cholesterolPro = numberFormat.format((float) Integer.parseInt(cholesterol.toString()) / (float) 300 * 100);
            }else {
                cholesterol = 0;
            }

            //维生素A
            Object vitaminA = list.get(0).get("vitaminA");
            String vitaminAPro = "0";
            if(vitaminA != null && !vitaminA.equals("")){
                vitaminAPro = numberFormat.format((float) Integer.parseInt(vitaminA.toString()) / (float) 800 * 100);
            }else {
                vitaminA = 0;
            }

            //维生素D
            Object vitaminD = list.get(0).get("vitaminD");
            String vitaminDPro = "0";
            if(vitaminD != null && !vitaminD.equals("")){
                vitaminDPro = numberFormat.format((float) Integer.parseInt(vitaminD.toString()) / (float) 5 * 100);
            }else {
                vitaminD = 0;
            }

            //维生素E
            Object vitaminE = list.get(0).get("vitaminE");
            String vitaminEPro = "0";
            if(vitaminE != null && !vitaminE.equals("")){
                vitaminEPro = numberFormat.format((float) Integer.parseInt(vitaminE.toString()) / (float) 14 * 100);
            }else {
                vitaminE = 0;
            }

            //维生素K
            Object vitaminK = list.get(0).get("vitaminK");
            String vitaminKPro = "0";
            if(vitaminK != null && !vitaminK.equals("")){
                vitaminKPro = numberFormat.format((float) Integer.parseInt(vitaminK.toString()) / (float) 80 * 100);
            }else {
                vitaminK = 0;
            }

            //维生素C
            Object vitaminC = list.get(0).get("vitaminC");
            String vitaminCPro = "0";
            if(vitaminC != null && !vitaminC.equals("")){
                vitaminCPro = numberFormat.format((float) Integer.parseInt(vitaminC.toString()) / (float) 100 * 100);
            }else {
                vitaminC = 0;
            }

            //维生素B1
            Object vitaminB1 = list.get(0).get("vitaminB1");
            String vitaminB1Pro = "0";
            if(vitaminB1 != null && !vitaminB1.equals("")){
                vitaminB1Pro = numberFormat.format((float) Integer.parseInt(vitaminB1.toString()) / (float) 1.4 * 100);
            }else {
                vitaminB1 = 0;
            }

            //维生素B2
            Object vitaminB2 = list.get(0).get("vitaminB2");
            String vitaminB2Pro = "0";
            if(vitaminB2 != null && !vitaminB2.equals("")){
                vitaminB2Pro = numberFormat.format((float) Integer.parseInt(vitaminB2.toString()) / (float) 1.4 * 100);
            }else {
                vitaminB2 = 0;
            }

            //维生素B6
            Object vitaminB6 = list.get(0).get("vitaminB6");
            String vitaminB6Pro = "0";
            if(vitaminB6 != null && !vitaminB6.equals("")){
                vitaminB6Pro = numberFormat.format((float) Integer.parseInt(vitaminB6.toString()) / (float) 1.4 * 100);
            }else {
                vitaminB6 = 0;
            }

            //维生素16
            Object vitamin16 = list.get(0).get("vitamin16");
            String vitamin16Pro = "0";
            if(vitamin16 != null && !vitamin16.equals("")){
                vitamin16Pro = numberFormat.format((float) Integer.parseInt(vitamin16.toString()) / (float) 0.024 * 100);
            }else {
                vitamin16 = 0;
            }

            //烟酸
            Object nicotinicacid = list.get(0).get("nicotinicacid");
            String nicotinicacidPro = "0";
            if(nicotinicacid != null && !nicotinicacid.equals("")){
                nicotinicacidPro = numberFormat.format((float) Integer.parseInt(nicotinicacid.toString()) / (float) 14 * 100);
            }else {
                nicotinicacid = 0;
            }

            //泛酸
            Object pantothenicacid = list.get(0).get("pantothenicacid");
            String pantothenicacidPro = "0";
            if(pantothenicacid != null && !pantothenicacid.equals("")){
                pantothenicacidPro = numberFormat.format((float) Integer.parseInt(pantothenicacid.toString()) / (float) 5 * 100);
            }else {
                pantothenicacid = 0;
            }

            //叶酸
            Object folicacid = list.get(0).get("folicacid");
            String folicacidPro = "0";
            if(folicacid != null && !folicacid.equals("")){
                //钠占比
                folicacidPro = numberFormat.format((float) Integer.parseInt(folicacid.toString()) / (float) 0.4 * 100);
            }else {
                folicacid = 0;
            }

            //生物素
            Object biotin = list.get(0).get("biotin");
            String biotinPro = "0";
            if(biotin != null && !biotin.equals("")){
                biotinPro = numberFormat.format((float) Integer.parseInt(biotin.toString()) / (float) 300 * 100);
            }else {
                biotin = 0;
            }

            //胆碱
            Object choline = list.get(0).get("choline");
            String cholinePro = "0";
            if(choline != null && !choline.equals("")){
                cholinePro = numberFormat.format((float) Integer.parseInt(choline.toString()) / (float) 450 * 100);
            }else {
                choline = 0;
            }

            //钙
            Object calcium = list.get(0).get("calcium");
            String calciumPro = "0";
            if(calcium != null && !calcium.equals("")){
                calciumPro = numberFormat.format((float) Integer.parseInt(calcium.toString()) / (float) 800 * 100);
            }else {
                calcium = 0;
            }

            //宁
            Object phosphorus = list.get(0).get("phosphorus");
            String phosphorusPro = "0";
            if(phosphorus != null && !phosphorus.equals("")){
                phosphorusPro = numberFormat.format((float) Integer.parseInt(phosphorus.toString()) / (float) 700 * 100);
            }else {
                phosphorus = 0;
            }


            //钾
            Object potassium = list.get(0).get("potassium");
            String potassiumPro = "0";
            if(potassium != null && !potassium.equals("")){
                potassiumPro = numberFormat.format((float) Integer.parseInt(potassium.toString()) / (float) 2000 * 100);
            }else {
                potassium = 0;
            }

            //镁
            Object magnesium = list.get(0).get("magnesium");
            String magnesiumPro = "0";
            if(magnesium != null && !magnesium.equals("")){
                magnesiumPro = numberFormat.format((float) Integer.parseInt(magnesium.toString()) / (float) 300 * 100);
            }else {
                magnesium = 0;
            }

            //铁
            Object iron = list.get(0).get("iron");
            String ironPro = "0";
            if(iron != null && !iron.equals("")){
                ironPro = numberFormat.format((float) Integer.parseInt(iron.toString()) / (float) 15 * 100);
            }else {
                iron = 0;
            }

            //锌
            Object zinc = list.get(0).get("zinc");
            String zincPro = "0";
            if(zinc != null && !zinc.equals("")){
                zincPro = numberFormat.format((float) Integer.parseInt(zinc.toString()) / (float) 15 * 100);
            }else {
                zinc = 0;
            }

            //碘
            Object iodine = list.get(0).get("iodine");
            String iodinePro = "0";
            if(iodine != null && !iodine.equals("")){
                iodinePro = numberFormat.format((float) Integer.parseInt(iodine.toString()) / (float) 0.15 * 100);
            }else {
                iodine = 0;
            }

            //晒
            Object selenium = list.get(0).get("selenium");
            String seleniumPro = "0";
            if(selenium != null && !selenium.equals("")){
                seleniumPro = numberFormat.format((float) Integer.parseInt(selenium.toString()) / (float) 0.05 * 100);
            }else {
                selenium = 0;
            }

            //铜
            Object copper = list.get(0).get("copper");
            String copperPro = "0";
            if(copper != null && !copper.equals("")){
                copperPro = numberFormat.format((float) Integer.parseInt(copper.toString()) / (float) 1.5 * 100);
            }else {
                copper = 0;
            }

            //氟
            Object fluorine = list.get(0).get("fluorine");
            String fluorinePro = "0";
            if(fluorine != null && !fluorine.equals("")){
                fluorinePro = numberFormat.format((float) Integer.parseInt(fluorine.toString()) / (float) 1 * 100);
            }else {
                fluorine = 0;
            }

            //铬
            Object chromium = list.get(0).get("chromium");
            String chromiumPro = "0";
            if(chromium != null && !chromium.equals("")){
                chromiumPro = numberFormat.format((float) Integer.parseInt(fluorine.toString()) / (float) 0.05 * 100);
            }else {
                chromium = 0;
            }
            //锰
            Object manganese = list.get(0).get("manganese");
            String manganesePro = "0";
            if(manganese != null && !manganese.equals("")){
                manganesePro = numberFormat.format((float) Integer.parseInt(manganese.toString()) / (float) 3 * 100);
            }else {
                manganese = 0;
            }

            //泪
            Object molybdenum = list.get(0).get("molybdenum");
            String molybdenumPro = "0";
            if(molybdenum != null && !molybdenum.equals("")){
                molybdenumPro = numberFormat.format((float) Integer.parseInt(molybdenum.toString()) / (float) 0.04 * 100);
            }else {
                molybdenum = 0;
            }

            Map map = new LinkedHashMap();
            map.put("name","能量");
            map.put("energy",energy);
            map.put("energyPro",energyPro);
            list1.add(map);

            Map map1 = new LinkedHashMap();
            map1.put("name","蛋白质");
            map1.put("energy",protein);
            map1.put("energyPro",proteinPro);
            list1.add(map1);

            Map map2 = new LinkedHashMap();
            map2.put("name","脂肪");
            map2.put("energy",fat);
            map2.put("energyPro",fatPro);
            list1.add(map2);

            Map map3 = new LinkedHashMap();
            map3.put("name","碳水化合物");
            map3.put("energy",carbohydrate);
            map3.put("energyPro",carbohydratePro);
            list1.add(map3);

            Map map4 = new LinkedHashMap();
            map4.put("name","膳食纤维");
            map4.put("energy",dietaryfiber);
            map4.put("energyPro",dietaryfiberPro);
            list1.add(map4);

            Map map5 = new LinkedHashMap();
            map5.put("name","钠");
            map5.put("energy",sodium);
            map5.put("energyPro",sodiumPro);
            list1.add(map5);

            Map map6 = new LinkedHashMap();
            map6.put("name","饱和脂肪酸");
            map6.put("energy",saturated);
            map6.put("energyPro",saturatedPro);
            list1.add(map6);

            Map map7 = new LinkedHashMap();
            map7.put("name","胆固醇");
            map7.put("energy",cholesterol);
            map7.put("energyPro",cholesterolPro);
            list1.add(map7);

            Map map8 = new LinkedHashMap();
            map8.put("name","维生素A");
            map8.put("energy",vitaminA);
            map8.put("energyPro",vitaminAPro);
            list1.add(map8);

            Map map9 = new LinkedHashMap();
            map9.put("name","维生素D");
            map9.put("energy",vitaminD);
            map9.put("energyPro",vitaminDPro);
            list1.add(map9);

            Map map10 = new HashMap();
            map10.put("name","维生素E");
            map10.put("energy",vitaminE);
            map10.put("energyPro",vitaminEPro);
            list1.add(map10);

            Map map11 = new LinkedHashMap();
            map11.put("name","维生素K");
            map11.put("energy",vitaminK);
            map11.put("energyPro",vitaminKPro);
            list1.add(map11);

            Map map12 = new LinkedHashMap();
            map12.put("name","维生素C");
            map12.put("energy",vitaminC);
            map12.put("energyPro",vitaminCPro);
            list1.add(map12);

            Map map13 = new LinkedHashMap();
            map13.put("name","维生素B1");
            map13.put("energy",vitaminB1);
            map13.put("energyPro",vitaminB1Pro);
            list1.add(map13);

            Map map14 = new LinkedHashMap();
            map14.put("name","维生素B2");
            map14.put("energy",vitaminB2);
            map14.put("energyPro",vitaminB2Pro);
            list1.add(map14);

            Map map15 = new LinkedHashMap();
            map15.put("name","维生素B6");
            map15.put("energy",vitaminB6);
            map15.put("energyPro",vitaminB6Pro);
            list1.add(map15);

            Map map16 = new LinkedHashMap();
            map16.put("name","维生素B16");
            map16.put("energy",vitamin16);
            map16.put("energyPro",vitamin16Pro);
            list1.add(map16);

            Map map17 = new LinkedHashMap();
            map17.put("name","烟酸");
            map17.put("energy",nicotinicacid);
            map17.put("energyPro",nicotinicacidPro);
            list1.add(map17);

            Map map18 = new LinkedHashMap();
            map18.put("name","叶酸");
            map18.put("energy",folicacid);
            map18.put("energyPro",folicacidPro);
            list1.add(map18);

            Map map19 = new LinkedHashMap();
            map19.put("name","泛酸");
            map19.put("energy",pantothenicacid);
            map19.put("energyPro",pantothenicacidPro);
            list1.add(map19);

            Map map20 = new LinkedHashMap();
            map20.put("name","生物素");
            map20.put("energy",biotin);
            map20.put("energyPro",biotinPro);
            list1.add(map20);

            Map map21 = new LinkedHashMap();
            map21.put("name","胆碱");
            map21.put("energy",choline);
            map21.put("energyPro",cholinePro);
            list1.add(map21);

            Map map22 = new LinkedHashMap();
            map22.put("name","钙");
            map22.put("energy",calcium);
            map22.put("energyPro",calciumPro);
            list1.add(map22);

            Map map23 = new LinkedHashMap();
            map23.put("name","磷");
            map23.put("energy",phosphorus);
            map23.put("energyPro",phosphorusPro);
            list1.add(map23);

            Map map24 = new LinkedHashMap();
            map24.put("name","钾");
            map24.put("energy",potassium);
            map24.put("energyPro",potassiumPro);
            list1.add(map24);

            Map map25 = new LinkedHashMap();
            map25.put("name","镁");
            map25.put("energy",magnesium);
            map25.put("energyPro",magnesiumPro);
            list1.add(map25);

            Map map26 = new LinkedHashMap();
            map26.put("name","铁");
            map26.put("energy",iron);
            map26.put("energyPro",ironPro);
            list1.add(map26);

            Map map27 = new LinkedHashMap();
            map27.put("name","锌");
            map27.put("energy",zinc);
            map27.put("energyPro",zincPro);
            list1.add(map27);

            Map map28 = new LinkedHashMap();
            map28.put("name","碘");
            map28.put("energy",iodine);
            map28.put("energyPro",iodinePro);
            list1.add(map28);

            Map map29 = new LinkedHashMap();
            map29.put("name","硒 ");
            map29.put("energy",selenium);
            map29.put("energyPro",seleniumPro);
            list1.add(map29);

            Map map30 = new LinkedHashMap();
            map30.put("name","铜");
            map30.put("energy",copper);
            map30.put("energyPro",copperPro);
            list1.add(map30);

            Map map31 = new LinkedHashMap();
            map31.put("name","氟");
            map31.put("energy",fluorine);
            map31.put("energyPro",fluorinePro);
            list1.add(map31);

            Map map32 = new LinkedHashMap();
            map32.put("name","铬");
            map32.put("energy",chromium);
            map32.put("energyPro",chromiumPro);
            list1.add(map32);

            Map map33 = new LinkedHashMap();
            map33.put("name","锰");
            map33.put("energy",manganese);
            map33.put("energyPro",manganesePro);
            list1.add(map33);

            Map map34 = new LinkedHashMap();
            map34.put("name","钼");
            map34.put("energy",molybdenum);
            map34.put("energyPro",molybdenumPro);
            list1.add(map34);
        }
        return list1;
    }



    //获取菜品类别
    @Override
    public String getDishCategory(String dishcategory){
        String substring = "";
        if(dishcategory != null && !dishcategory.equals("")){
            StringBuilder sb = new StringBuilder();
            String[] split = dishcategory.split(",");
            for (String s : split) {
                sb.append("'").append(s).append("',");
            }
            dishcategory = sb.substring(0,sb.length()-1);
            List<Map> list = stfoodManagementMapper.selectType(dishcategory);

            sb = new StringBuilder();
            for (Map map : list) {
                Object paravalue = map.get("varietiesname");
                sb.append(paravalue).append(",");
            }
            if(sb.toString() != null && !sb.toString().equals("")){
                substring = sb.substring(0,sb.length()-1);
            }
        }
        return substring;
    }

    //图片上传
    @Override
    public Object imgUploading(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("files");
        //MultipartFile[] files = multipartRequest.getFile("file");
        String contentImg = null;
        for (MultipartFile file : files) {

            /*boolean upload = MySFTP.upload(file);
            if( upload == true){
                list.add(MySFTP.directory + "/" + MySFTP.newFileName);
            }else {
                return "上传文件失败";
            }*/
            //如果图片为空，直接返回
			if(file.isEmpty()){
                return "上传文件失败，上传文件为空";
            }
            BASE64Encoder encoder = new BASE64Encoder();
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
            // 通过base64来转化图片
            String data = null;
            try {
                data = encoder.encode(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return "上传文件失败";
            }
            //返回数据库中要存储的信息
            contentImg = "data:image/"+type+";base64,"+data;
        }
        return contentImg;
    }

    //JSONArray数组排序
    public String sort(JSONArray array){
        StringBuilder sb = new StringBuilder();
        String sort = null;
        if( array != null && array.size() != 0 ){
            Object[] objects = array.toArray();
            Arrays.sort(objects);
            for (Object object : objects) {
                sb.append(object.toString()).append(",");
            }
            sort = sb.substring(0,sb.length()-1);
        }
        return sort;
    }
}
