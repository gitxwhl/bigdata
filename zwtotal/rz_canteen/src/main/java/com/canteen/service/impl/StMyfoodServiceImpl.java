package com.canteen.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StMydishes;
import com.canteen.entity.StfoodManagement;
import com.canteen.mapper.StMyfoodMapper;
import com.canteen.mapper.StfoodManagementMapper;
import com.canteen.service.StMyfoodService;
import com.canteen.service.StfoodManagementService;
import com.canteen.utils.MySFTP;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;

@Service
public class StMyfoodServiceImpl implements StMyfoodService {
    @Autowired
    private StMyfoodMapper stMyfoodMapper;

    @Autowired
    private StOperationrestaurantServiceImpl operationrestaurantService;

    @Autowired
    private StfoodManagementService stfoodManagementService;

    @Autowired
    private StfoodManagementMapper stfoodManagementMapper;

    /*
    获取不同运维餐厅下所有的菜品列表
     */
    @Override
    public PageBean selectByRestaurant(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String restaurant = paramDate.getString("restaurant");
        String name = paramDate.getString("name");
        String category = paramDate.getString("category");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = stMyfoodMapper.getTotalSize(restaurant,name,category);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List equipmentList = stMyfoodMapper.selectByRestaurant(restaurant,name,category,startIndex,pageSize);
            pageBean.setList(equipmentList);
        }
        return pageBean;
    }

    /*
     * 全量库查询菜品
     *
     * @return
     */
    @Override
    public PageBean selectNameByFoodManage(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String name = paramDate.getString("name");
        String category = paramDate.getString("category");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = stMyfoodMapper.getTotal(name,category);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List equipmentList = stMyfoodMapper.selectNameByFoodManage(name,category,startIndex,pageSize);
            pageBean.setList(equipmentList);
        }
        return pageBean;
    }

    /*
    菜品营养分析
     */
    public List CalculationNutritional(String dishcode) {
        List<Map> list = stMyfoodMapper.CalculationNutritional(dishcode);
        Map map = new LinkedHashMap();
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
            if(magnesium != null && !potassium.equals("")){
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

            map.put("name","能量");
            map.put("energy",energy);
            map.put("energyPro",energyPro);
            list1.add(map);

            Map map1 = new HashMap();
            map1.put("name","蛋白质");
            map1.put("energy",protein);
            map1.put("energyPro",proteinPro);
            list1.add(map1);

            Map map2 = new HashMap();
            map2.put("name","脂肪");
            map2.put("energy",fat);
            map2.put("energyPro",fatPro);
            list1.add(map2);

            Map map3 = new HashMap();
            map3.put("name","碳水化合物");
            map3.put("energy",carbohydrate);
            map3.put("energyPro",carbohydratePro);
            list1.add(map3);

            Map map4 = new HashMap();
            map4.put("name","膳食纤维");
            map4.put("energy",dietaryfiber);
            map4.put("energyPro",dietaryfiberPro);
            list1.add(map4);

            Map map5 = new HashMap();
            map5.put("name","钠");
            map5.put("energy",sodium);
            map5.put("energyPro",sodiumPro);
            list1.add(map5);

            Map map6 = new HashMap();
            map6.put("name","饱和脂肪酸");
            map6.put("energy",saturated);
            map6.put("energyPro",saturatedPro);
            list1.add(map6);

            Map map7 = new HashMap();
            map7.put("name","胆固醇");
            map7.put("energy",cholesterol);
            map7.put("energyPro",cholesterolPro);
            list1.add(map7);

            Map map8 = new HashMap();
            map8.put("name","维生素A");
            map8.put("energy",vitaminA);
            map8.put("energyPro",vitaminAPro);
            list1.add(map8);

            Map map9 = new HashMap();
            map9.put("name","维生素D");
            map9.put("energy",vitaminD);
            map9.put("energyPro",vitaminDPro);
            list1.add(map9);

            Map map10 = new HashMap();
            map10.put("name","维生素E");
            map10.put("energy",vitaminE);
            map10.put("energyPro",vitaminEPro);
            list1.add(map10);

            Map map11 = new HashMap();
            map11.put("name","维生素K");
            map11.put("energy",vitaminK);
            map11.put("energyPro",vitaminKPro);
            list1.add(map11);

            Map map12 = new HashMap();
            map12.put("name","维生素C");
            map12.put("energy",vitaminC);
            map12.put("energyPro",vitaminCPro);
            list1.add(map12);

            Map map13 = new HashMap();
            map13.put("name","维生素B1");
            map13.put("energy",vitaminB1);
            map13.put("energyPro",vitaminB1Pro);
            list1.add(map13);

            Map map14 = new HashMap();
            map14.put("name","维生素B2");
            map14.put("energy",vitaminB2);
            map14.put("energyPro",vitaminB2Pro);
            list1.add(map14);

            Map map15 = new HashMap();
            map15.put("name","维生素B6");
            map15.put("energy",vitaminB6);
            map15.put("energyPro",vitaminB6Pro);
            list1.add(map15);

            Map map16 = new HashMap();
            map16.put("name","维生素B16");
            map16.put("energy",vitamin16);
            map16.put("energyPro",vitamin16Pro);
            list1.add(map16);

            Map map17 = new HashMap();
            map17.put("name","烟酸");
            map17.put("energy",nicotinicacid);
            map17.put("energyPro",nicotinicacidPro);
            list1.add(map17);

            Map map18 = new HashMap();
            map18.put("name","叶酸");
            map18.put("energy",folicacid);
            map18.put("energyPro",folicacidPro);
            list1.add(map18);

            Map map19 = new HashMap();
            map19.put("name","泛酸");
            map19.put("energy",pantothenicacid);
            map19.put("energyPro",pantothenicacidPro);
            list1.add(map19);

            Map map20 = new HashMap();
            map20.put("name","生物素");
            map20.put("energy",biotin);
            map20.put("energyPro",biotinPro);
            list1.add(map20);

            Map map21 = new HashMap();
            map21.put("name","胆碱");
            map21.put("energy",choline);
            map21.put("energyPro",cholinePro);
            list1.add(map21);

            Map map22 = new HashMap();
            map22.put("name","钙");
            map22.put("energy",calcium);
            map22.put("energyPro",calciumPro);
            list1.add(map22);

            Map map23 = new HashMap();
            map23.put("name","磷");
            map23.put("energy",phosphorus);
            map23.put("energyPro",phosphorusPro);
            list1.add(map23);

            Map map24 = new HashMap();
            map24.put("name","钾");
            map24.put("energy",potassium);
            map24.put("energyPro",potassiumPro);
            list1.add(map24);

            Map map25 = new HashMap();
            map25.put("name","镁");
            map25.put("energy",magnesium);
            map25.put("energyPro",magnesiumPro);
            list1.add(map25);

            Map map26 = new HashMap();
            map26.put("name","铁");
            map26.put("energy",iron);
            map26.put("energyPro",ironPro);
            list1.add(map26);

            Map map27 = new HashMap();
            map27.put("name","锌");
            map27.put("energy",zinc);
            map27.put("energyPro",zincPro);
            list1.add(map27);

            Map map28 = new HashMap();
            map28.put("name","碘");
            map28.put("energy",iodine);
            map28.put("energyPro",iodinePro);
            list1.add(map28);

            Map map29 = new HashMap();
            map29.put("name","硒 ");
            map29.put("energy",selenium);
            map29.put("energyPro",seleniumPro);
            list1.add(map29);

            Map map30 = new HashMap();
            map30.put("name","铜");
            map30.put("energy",copper);
            map30.put("energyPro",copperPro);
            list1.add(map30);

            Map map31 = new HashMap();
            map31.put("name","氟");
            map31.put("energy",fluorine);
            map31.put("energyPro",fluorinePro);
            list1.add(map31);

            Map map32 = new HashMap();
            map32.put("name","铬");
            map32.put("energy",chromium);
            map32.put("energyPro",chromiumPro);
            list1.add(map32);

            Map map33 = new HashMap();
            map33.put("name","锰");
            map33.put("energy",manganese);
            map33.put("energyPro",manganesePro);
            list1.add(map33);

            Map map34 = new HashMap();
            map34.put("name","钼");
            map34.put("energy",molybdenum);
            map34.put("energyPro",molybdenumPro);
            list1.add(map34);
        }
        return list1;
    }

    /*
    根据菜品id删除菜品
     */
    @Override
    public void deleteMyFoodById(int id) {
        stMyfoodMapper.deleteMyFoodById(id);
    }

    /*
    添加菜品
     */
    @Override
    public void insertMyFood(String param) {
         JSONObject paramDate  = JSONObject.parseObject(param);
         String id = paramDate.getString("dishcode");
         String restaurant = paramDate.getString("restaurant");
      StMydishes stMydishes = stMyfoodMapper.selectManageFood(id);
      if(stMyfoodMapper.selectDishcode(id,restaurant) == null){
          stMydishes.setRestaurant(restaurant);
          stMyfoodMapper.insertMyFood(stMydishes);
      }
    }
/*
菜类查询
 */
    @Override
    public List selectdish() {
        return stMyfoodMapper.selectdish();
    }


    //------------------------------------菜品查询---------------------------------------------------
    /*
    * 菜品查询
    * {"pageNum":"","pageSize":"","restaurant":"","name":"","category":[],"dishescategory":"","time":"","meals":""}
    * */
    @Override
    public Object queryDish(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String string = rowData.getString("restaurant");
        String restaurant = operationrestaurantService.getIds(string);
        String name = rowData.getString("name");
        JSONArray category = rowData.getJSONArray("category");
        String sort = stfoodManagementService.sort(category);
        String dishescategory = rowData.getString("dishescategory");
        String time = rowData.getString("time");
        String meals = rowData.getString("meals");
        Integer totalSize = stMyfoodMapper.selectDishCnt(restaurant, name, sort, dishescategory, time, meals);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> list = stMyfoodMapper.selectDish(restaurant, name, sort, dishescategory, time, meals, startIndex, pageSize);
            for (Map map : list) {
                String category1 = map.get("category").toString();
                String dishCategory = stfoodManagementService.getDishCategory(category1);
                map.put("category",dishCategory);

                Object foodpictures = map.get("foodpictures");
                if(foodpictures != null && !foodpictures.equals("")){
                    foodpictures  = MySFTP.getPath(foodpictures.toString());
                }
                map.put("foodpictures",foodpictures);
            }
            pageBean.setList(list);
        }
        return pageBean;
    }


    /*
     * 菜品营养成分分析
     * {"dishcode":""}
     * */
    @Override
    public Object getNutritional(String paramJson) {
        Object o = stfoodManagementService.foodAnalyze(paramJson);
        return o;
    }


    //菜品查询-字典表
    @Override
    public Object queryDishDic() {
        Map resultMap = new LinkedHashMap();

        //菜品类别
        List<JSONObject> dishType = new ArrayList<>();
        dishType.add(JSONObject.parseObject("{'id':'','text':'不限'}"));

        //菜品分类
        List<JSONObject> dishesCategory = new ArrayList<>();
        dishesCategory.add(JSONObject.parseObject("{'id':'','text':'不限'}"));

        //餐别
        List<JSONObject> meals = new ArrayList<>();
        meals.add(JSONObject.parseObject("{'id':'','text':'不限'}"));

        List<Map> dictionaries = stfoodManagementMapper.dictionaries();
        dictionaries.stream().forEach(map ->{
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("CANTEEN_MATERIALTYPE".equals(map.get("paraname"))){
                dishType.add(jsonObject);
            }
            if("dishescategory".equals(map.get("paraname"))){
                dishesCategory.add(jsonObject);
            }
            if("mealtype".equals(map.get("paraname"))){
                meals.add(jsonObject);
            }
        });

        resultMap.put("dishType",dishType);
        resultMap.put("dishesCategory",dishesCategory);
        resultMap.put("meals",meals);
        return resultMap;
    }


}
