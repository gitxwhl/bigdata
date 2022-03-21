package com.canteen.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.*;
import com.canteen.mapper.StIntelligenceMealMapper;
import com.canteen.service.StIntelligenceMealService;
import com.canteen.utils.ExportExcel;
import com.canteen.utils.ExportExcelPlus;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StIntelligenceMealServiceImpl implements StIntelligenceMealService {
    @Autowired
    StIntelligenceMealMapper stIntelligenceMealMapper;

    /*
    手动添加--菜品查询
     */
    @Override
    public List SelectMyFood(String param) {
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String meals = paramData.getString("meals");
        String dishescategory = paramData.getString("dishescategory");
        String name = paramData.getString("name");
       List<Map> map =stIntelligenceMealMapper.SelectMyFood(restaurant, meals, dishescategory,name);
       List list = new ArrayList();
        int a = 0;
       for(Map ma : map){
           if(a!=(Integer)ma.get("id")){
               list.add(ma);
               a = (Integer) ma.get("id");
           }
       }
       return list;
    }

    /*
    菜品添加
     */
    @Override
    public void UpdateDishescategory(List<Integer> ids) {
        List<Map> list = new ArrayList<>();
        for (int id : ids) {
            //list.add(stIntelligenceMealMapper.UpdateDishescategory(id));
            Date currentTime = new Date();
            SimpleDateFormat DateFormate = new SimpleDateFormat("yyyy-MM-dd");
            String date = DateFormate.format(currentTime);
            if(stIntelligenceMealMapper.selectByDate(id,date) == null){
                stIntelligenceMealMapper.insertDate(id, date);
            }
        }
    }

    /*
    一键排餐
     */
    @Override
    public void SelectMyFoodByRestaurant(String restaurant) {
         //stIntelligenceMealMapper.deleteIntelligence();
        for(int meals=1;meals<=3;meals++){

      for(int dishescategory=1;dishescategory<=5;dishescategory++) {

     List<Map> stOperationList = stIntelligenceMealMapper.SelectMyFoodByRestaurant(restaurant,String.valueOf(meals),String.valueOf(dishescategory));
    for (Map map : stOperationList) {
      int id = (int) map.get("id");
        Date currentTime = new Date();
        SimpleDateFormat DateFormate = new SimpleDateFormat("yyyy-MM-dd");
        String date = DateFormate.format(currentTime);
        if (stIntelligenceMealMapper.selectByDate(id, date) == null) {
            stIntelligenceMealMapper.insertDate(id, date);
        }
    }
}
}

    }

    /*
    根据id删除菜品
     */
    @Override
    public void DeleteMyFoodByID(Integer id) {
stIntelligenceMealMapper.DeleteMyFoodByID(id);
    }

    /*
    一键重排
     */
    @Override
    public void SelectMyFoodByRepeat(String restaurant,String meals) {
        Date currentTime = new Date();
        SimpleDateFormat DateFormate = new SimpleDateFormat("yyyy-MM-dd");
        String date = DateFormate.format(currentTime);
     List<Integer> ids = stIntelligenceMealMapper.queryIds(restaurant,date,meals);
     for(int id : ids){
         stIntelligenceMealMapper.deleteIntelligenceByMeals(id);
     }
       for(int dishescategory=1;dishescategory<=5;dishescategory++) {
           List<Map> stOperationList = stIntelligenceMealMapper.SelectMyFoodByRestaurant(restaurant, meals,String.valueOf(dishescategory));
           for (Map map : stOperationList) {
               int id = (int) map.get("id");
               if (stIntelligenceMealMapper.selectByDate(id, date) == null) {
                   stIntelligenceMealMapper.insertDate(id, date);
               }
           }
       }
    }

    /*
    菜品替换
     */
    @Override
    public void dishesReplacement(String param) {
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String date = paramData.getString("date");
        String meals = paramData.getString("meals");
        //查询排餐菜品种类
        List<Map> mapList = stIntelligenceMealMapper.selectCategory(restaurant,date,meals);
        //查询餐厅关联菜品种类
        List<String> list = stIntelligenceMealMapper.selectCategoryOfRestaurant(restaurant);

        //排餐菜品种类和餐厅关联菜品种类对比
        for(Map map : mapList){
           String category = (String) map.get("category");
            //String[] strs = category.split(",");
           int id = (int) map.get("id");
           String dishescategory = (String)map.get("dishescategory");
           boolean result = true;
            boolean result1 = true;
           if(category.length() == 1){
               result = useLoop(list,category);
           }else {
               String[] strs = category.split(",");
               for(String s : strs){
                   result1 = useLoop(list,s) &&  result1;

               }
               result = result1;
           }
                if(result == false){
                   //删除不是餐厅关联的菜品
                 stIntelligenceMealMapper.deleteCategory(id);
                 //查询该餐厅关联的菜品，并加入排餐列表中
                 for(String ll : list) {
                     List<Integer> list1 = stIntelligenceMealMapper.selectDish(restaurant, ll,dishescategory,meals);
                     for (Integer str : list1) {
                         if (stIntelligenceMealMapper.selectByDate(str, date) == null) {
                             stIntelligenceMealMapper.insertDate(str, date);
                             break;
                         }
                     }
                     break;
                 }
            }

        }
    }

    /*
    判断是否重复
     */
    public static boolean useLoop(List<String> list, String target) {
        for(String s: list){
            if(s.equals(target))
                return true;
        }
        return false;
    }

    /*
    一键导出排餐列表
     */
    @Override
    public Object choiceDishExport(HttpServletRequest request, HttpServletResponse response, String param) throws UnsupportedEncodingException {
        //文件名称
        String fileName = "排餐" + ".xls";
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String date = paramData.getString("date");
        //获取早餐
        List<StIntelligenceMealsVo> BreakStOperationList = stIntelligenceMealMapper.SelectMyBreakFoodByDate(restaurant, date);
        //午餐
        List<StIntelligenceMealsVo> LunchStOperationList = stIntelligenceMealMapper.SelectMyLunchFoodByDate(restaurant, date);
        //晚餐
        List<StIntelligenceMealsVo> DinnerStOperationList = stIntelligenceMealMapper.SelectMyDinnerFoodByDate(restaurant, date);
        //第一行标题
        List<String> titles = new ArrayList<String>();
        titles.add("主食");
        titles.add("汤类");
        titles.add("水果");
        titles.add("冷菜");
        titles.add("热菜");
        //表格数据数据
        List<Map> varList = new ArrayList<>();
        List<Map> lunList = new ArrayList<>();
        List<Map> dinList = new ArrayList<>();
        List v1 = new ArrayList();
        List v2 = new ArrayList();
        List v3 = new ArrayList();
        List v4 = new ArrayList();
        List v5 = new ArrayList();
        for(StIntelligenceMealsVo st : BreakStOperationList){
            if (st.getDishescategory().equals("1")) {
            v1.add(st.getName());
        }
            else if(st.getDishescategory().equals("2")){
                v2.add(st.getName());
            } else if(st.getDishescategory().equals("3")){
                v3.add(st.getName());
            } else if(st.getDishescategory().equals("4")){
                v4.add(st.getName());
            }else {
                v5.add(st.getName());
            }
        }

        for(int a = 0;a<=10;a++){
            Map vpd = new HashMap();
            if(a <= v1.size()-1){
                vpd.put("var1",v1.get(a));
            }else {
                vpd.put("var1",null);
            }
            if(a <= v2.size()-1){
                vpd.put("var2",v2.get(a));
            }else {
                vpd.put("var2",null);
            }
            if(a <= v3.size()-1){
                vpd.put("var3",v3.get(a));
            }else {
                vpd.put("var3",null);
            }
            if(a <= v4.size()-1){
                vpd.put("var4",v4.get(a));
            }else {
                vpd.put("var4",null);
            }
            if(a <= v5.size()-1){
                vpd.put("var5",v5.get(a));
            }else {
                vpd.put("var5",null);
            }
            varList.add(vpd);
        }

        List l1 = new ArrayList();
        List l2 = new ArrayList();
        List l3 = new ArrayList();
        List l4 = new ArrayList();
        List l5 = new ArrayList();
        for(StIntelligenceMealsVo st : LunchStOperationList){
            if (st.getDishescategory().equals("1")) {
                l1.add(st.getName());
            }
            else if(st.getDishescategory().equals("2")){
                l2.add(st.getName());
            } else if(st.getDishescategory().equals("3")){
                l3.add(st.getName());
            } else if(st.getDishescategory().equals("4")){
                l4.add(st.getName());
            }else {
                l5.add(st.getName());
            }
        }
        for(int a = 0;a<=10;a++){
            Map vpd = new HashMap();
            if(a <= l1.size()-1){
                vpd.put("var1",l1.get(a));
            }else {
                vpd.put("var1",null);
            }
            if(a <= l2.size()-1){
                vpd.put("var2",l2.get(a));
            }else {
                vpd.put("var2",null);
            }
            if(a <= l3.size()-1){
                vpd.put("var3",l3.get(a));
            }else {
                vpd.put("var3",null);
            }
            if(a <= l4.size()-1){
                vpd.put("var4",l4.get(a));
            }else {
                vpd.put("var4",null);
            }
            if(a <= l5.size()-1){
                vpd.put("var5",l5.get(a));
            }else {
                vpd.put("var5",null);
            }
            lunList.add(vpd);
        }

        List d1 = new ArrayList();
        List d2 = new ArrayList();
        List d3 = new ArrayList();
        List d4 = new ArrayList();
        List d5 = new ArrayList();
        for(StIntelligenceMealsVo st : DinnerStOperationList){
            if (st.getDishescategory().equals("1")) {
                d1.add(st.getName());
            }
            else if(st.getDishescategory().equals("2")){
                d2.add(st.getName());
            } else if(st.getDishescategory().equals("3")){
                d3.add(st.getName());
            } else if(st.getDishescategory().equals("4")){
                d4.add(st.getName());
            }else {
                d5.add(st.getName());
            }
        }

        for(int a = 0;a<=10;a++){
            Map vpd = new HashMap();
            if(a <= d1.size()-1){
                vpd.put("var1",d1.get(a));
            }else {
                vpd.put("var1",null);
            }
            if(a <= d2.size()-1){
                vpd.put("var2",d2.get(a));
            }else {
                vpd.put("var2",null);
            }
            if(a <= d3.size()-1){
                vpd.put("var3",d3.get(a));
            }else {
                vpd.put("var3",null);
            }
            if(a <= d4.size()-1){
                vpd.put("var4",d4.get(a));
            }else {
                vpd.put("var4",null);
            }
            if(a <= d5.size()-1){
                vpd.put("var5",d5.get(a));
            }else {
                vpd.put("var5",null);
            }
           dinList.add(vpd);
        }
        Map titlesMap = new HashMap();
        titlesMap.put("listfast",titles);
        titlesMap.put("listlunch",titles);
        titlesMap.put("listdinner",titles);

        Map dinMap = new HashMap();
        dinMap.put("listfast1",varList);
        dinMap.put("listlunch1",lunList);
        dinMap.put("listdinner1",dinList);
        ExportExcelPlus ex = new ExportExcelPlus();
        ex.export(response, fileName, titlesMap, dinMap);
        return 1;
    }

    /*
    菜品页面初始化查询
     */
    @Override
    public Map SelectByRestaurant(String restaurant, String date) {
        Map<String, List> map = new HashMap<>();
        //早餐
        List breakfast = new ArrayList();
        List breakfastZs = new ArrayList();
        List breakfastSoup = new ArrayList();
        List breakfastSg = new ArrayList();
        List breakfastLc = new ArrayList();
        List breakfastRc = new ArrayList();

        //午餐
        List lunch = new ArrayList();
        List lunchZs = new ArrayList();
        List lunchSoup = new ArrayList();
        List lunchSg = new ArrayList();
        List lunchLc = new ArrayList();
        List lunchRc = new ArrayList();

        //晚餐
        List dinner = new ArrayList();
        List dinnerZs = new ArrayList();
        List dinnerSoup = new ArrayList();
        List dinnerSg = new ArrayList();
        List dinnerLc = new ArrayList();
        List dinnerRc = new ArrayList();
        List<StIntelligenceMealsVo> stOperationList = stIntelligenceMealMapper.SelectByRestaurant(restaurant,date);
        for (StIntelligenceMealsVo stOperationrestaurant : stOperationList) {
            Map map1 = new HashMap();
            map1.put("name",stOperationrestaurant.getName());
            map1.put("id",stOperationrestaurant.getId());
            if (stOperationrestaurant.getMeals().equals("1")) {
                if (stOperationrestaurant.getDishescategory().equals("1")) {
                    breakfastZs.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("2")) {
                    breakfastSoup.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("3")) {
                    breakfastSg.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("4")) {
                    breakfastLc.add(map1);
                } else {
                    breakfastRc.add(map1);
                }
            } else if (stOperationrestaurant.getMeals().equals("2")) {
                if (stOperationrestaurant.getDishescategory().equals("1")) {
                    lunchZs.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("2")) {
                    lunchSoup.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("3")) {
                    lunchSg.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("4")) {
                    lunchLc.add(map1);
                } else {
                    lunchRc.add(map1);
                }
            } else {
                if (stOperationrestaurant.getDishescategory().equals("1")) {
                    dinnerZs.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("2")) {
                    dinnerSoup.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("3")) {
                    dinnerSg.add(map1);
                } else if (stOperationrestaurant.getDishescategory().equals("4")) {
                    dinnerLc.add(map1);
                } else {
                    dinnerRc.add(map1);
                }
            }

        }
        //早餐
        Map<String, List> breakfastMap = new HashMap<>();
        breakfastMap.put("breakfastZs", breakfastZs);
        breakfastMap.put("breakfastSoup", breakfastSoup);
        breakfastMap.put("breakfastSg", breakfastSg);
        breakfastMap.put("breakfastLc", breakfastLc);
        breakfastMap.put("breakfastRc", breakfastRc);
        breakfast.add(breakfastMap);
        map.put("breakfast", breakfast);
        //午餐
        Map<String, List> lunchMap = new HashMap<>();
        lunchMap.put("breakfastZs", lunchZs);
        lunchMap.put("breakfastSoup", lunchSoup);
        lunchMap.put("breakfastSg", lunchSg);
        lunchMap.put("breakfastLc", lunchLc);
        lunchMap.put("breakfastRc", lunchRc);
        lunch.add(lunchMap);
        map.put("lunch", lunch);
        //晚餐
        Map<String, List> dinnerMap = new HashMap<>();
        dinnerMap.put("breakfastZs", dinnerZs);
        dinnerMap.put("breakfastSoup", dinnerSoup);
        dinnerMap.put("breakfastSg", dinnerSg);
        dinnerMap.put("breakfastLc", dinnerLc);
        dinnerMap.put("breakfastRc", dinnerRc);
        dinner.add(dinnerMap);
        map.put("dinner", dinner);
        return map;
    }



}


