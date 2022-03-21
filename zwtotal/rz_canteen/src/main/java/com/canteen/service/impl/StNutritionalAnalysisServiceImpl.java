package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.canteen.entity.StDietaryStructureVo;
import com.canteen.entity.vo.NutrientAnalysisVo;
import com.canteen.entity.vo.NutritionalAnalysisVo;
import com.canteen.mapper.StNutritionalAnalysisMapper;
import com.canteen.service.StNutritionalAnalysisService;
import com.canteen.utils.DatePlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;


@Service
public class StNutritionalAnalysisServiceImpl implements StNutritionalAnalysisService {

    @Autowired
    StNutritionalAnalysisMapper stNutritionalAnalysisMapper;
    /*
    排餐总能量 分析
     */
    @Override
    public Map totalEnergyAnalysis(String param)throws ParseException {
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String date = paramData.getString("date");
        Map<String,Integer> map = new HashMap<>();
        int BreakTotal =0;
        int lunchTotal = 0;
        int DinnerTotal = 0;
        //获取当天早中晚能量
       List<NutritionalAnalysisVo> nutritionalAnalysisVos = stNutritionalAnalysisMapper.totalEnergyAnalysis(restaurant,date);
       for(NutritionalAnalysisVo nu : nutritionalAnalysisVos){
           if(nu.getMeals().equals("1")){
             BreakTotal = BreakTotal  +  Integer.parseInt(nu.getEnergy());
           }else if(nu.getMeals().equals("2")){
               lunchTotal = lunchTotal+Integer.parseInt(nu.getEnergy());
           }else {
               DinnerTotal = DinnerTotal+Integer.parseInt(nu.getEnergy());
           }
       }
       map.put("BreakTotal",BreakTotal);
       map.put("lunchTotal",lunchTotal);
       map.put("DinnerTotal",DinnerTotal);
       //获取本周早中晚能量
        int BreakTotal1 =0;
        int lunchTotal1 = 0;
        int DinnerTotal1 = 0;
        DatePlus datePlus = new DatePlus();
        Map WeekMap = datePlus.weekDate(date);
       String StartDate = (String) WeekMap.get("weekStart");
       String EndDate = (String) WeekMap.get("weekEnd");
        List<NutritionalAnalysisVo> list =stNutritionalAnalysisMapper.totalEnergyAnalysisOfWeek(restaurant,StartDate,EndDate);
        for(NutritionalAnalysisVo nu : list){
            if(nu.getMeals().equals("1")){
                BreakTotal1 = BreakTotal1  +  Integer.parseInt(nu.getEnergy());
            }else if(nu.getMeals().equals("2")){
                lunchTotal1 = lunchTotal1+Integer.parseInt(nu.getEnergy());
            }else {
                DinnerTotal1 = DinnerTotal1+Integer.parseInt(nu.getEnergy());
            }
        }
        map.put("BreakTotal1",BreakTotal1);
        map.put("lunchTotal1",lunchTotal1);
        map.put("DinnerTotal1",DinnerTotal1);

        //获取本月早中晚能量
        int BreakTotal2 =0;
        int lunchTotal2 = 0;
        int DinnerTotal2 = 0;
        Map MonthMap = datePlus.monthFormat(date);
        String startDate = (String) MonthMap.get("startDate");
        String endDate = (String) MonthMap.get("endDate");
        List<NutritionalAnalysisVo> nutritionalAnalysisVos1u =stNutritionalAnalysisMapper.totalEnergyAnalysisOfWeek(restaurant,startDate,endDate);
        for(NutritionalAnalysisVo nu : nutritionalAnalysisVos1u){
            if(nu.getMeals().equals("1")){
                BreakTotal2 = BreakTotal2  +  Integer.parseInt(nu.getEnergy());
            }else if(nu.getMeals().equals("2")){
                lunchTotal2 = lunchTotal2+Integer.parseInt(nu.getEnergy());
            }else {
                DinnerTotal2 = DinnerTotal2+Integer.parseInt(nu.getEnergy());
            }
        }
        map.put("BreakTotal2",BreakTotal2);
        map.put("lunchTotal2",lunchTotal2);
        map.put("DinnerTotal2",DinnerTotal2);
       return map;

    }

    /*
    排餐能量分布分析
     */
    public List distributedEnergyAnalysis(String param){
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String date = paramData.getString("date");
      //Map<String,Map> map = new HashMap();
        List list = new ArrayList();
        List<NutritionalAnalysisVo> nutritionalAnalysisVos = stNutritionalAnalysisMapper.totalEnergyAnalysis(restaurant,date);
        for(NutritionalAnalysisVo nu : nutritionalAnalysisVos){
            Map<String,String> stringMap = new HashMap<>();
            stringMap.put("name",nu.getName());
            stringMap.put("energy",nu.getEnergy());
            list.add(stringMap);
        }
       // map.put("能量",stringMap);
          return list;
    }

    /*
    排餐营养素分析
     */
    @Override
    public Map NutrientAnalysis(String param) {
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String date = paramData.getString("date");
        //蛋白质总量
       int proteinTotal = 0;
       //脂肪总量
       int fatTotal = 0;
       //饱和脂肪酸总量
        int saturatedTotal = 0;
        //胆固醇总量
        int cholesterolTotal = 0;
        //碳水化合物总量
        int carbohydrateTotal = 0;
        //膳食纤维总量
        int dietaryfiberTotal = 0;
        //维生素A总量
        int vitaminATotal = 0;
        //维生素D总量
        int vitaminDTotal = 0;
        //维生素E总量
        int vitaminETotal = 0;
        //维生素K总量
        int vitaminKTotal = 0;
        //维生素B1总量
        int vitaminB1Total = 0;
        //维生素B2总量
        int vitaminB2Total = 0;
        //维生素B6总量
        int vitaminB6Total = 0;
        //维生素B16总量
        int vitamin16Total = 0;
        //维生素C总量
        int vitaminCTotal = 0;
        //烟酸总量
        int nicotinicacidTotal = 0;
        //叶酸总量
        int folicacidTotal = 0;
        //泛酸总量
        int pantothenicacidTotal = 0;
        //生物素总量
        int biotinTotal = 0;
        //胆碱总量
        int cholineTotal = 0;
        //钙总量
        int calciumTotal = 0;
        //磷总量
        int phosphorusTotal = 0;
        //钾总量
        int potassiumTotal = 0;
        //钠总量
        int sodiumTotal = 0;
        //镁总量
        int magnesiumTotal = 0;
        //铁总量
        int ironTotal = 0;
        // 锌总量
        int zincTotal = 0;
        // 碘总量
        int iodineTotal = 0;
        //硒总量
        int seleniumTotal = 0;
        //铜总量
        int copperTotal = 0;
        //氟总量
        int fluorineTotal = 0;
        // 铬总量
        int chromiumTotal = 0;
        // 锰总量
        int manganeseTotal = 0;
        //钼总量
        int molybdenumTotal = 0;
        List<NutrientAnalysisVo> nutrientAnalysisVos = stNutritionalAnalysisMapper.NutrientAnalysis(restaurant,date);
        for(NutrientAnalysisVo nu : nutrientAnalysisVos){
         proteinTotal = proteinTotal+ Integer.parseInt(nu.getProtein());
         fatTotal = fatTotal + Integer.parseInt(nu.getFat());
         saturatedTotal = saturatedTotal + Integer.parseInt(nu.getSaturated());
         cholesterolTotal = cholesterolTotal + Integer.parseInt(nu.getCholesterol());
         carbohydrateTotal = carbohydrateTotal + Integer.parseInt(nu.getCarbohydrate());
         dietaryfiberTotal = dietaryfiberTotal+Integer.parseInt(nu.getDietaryfiber());
         vitaminATotal = vitaminATotal + Integer.parseInt(nu.getVitaminA());
         vitaminDTotal = vitaminDTotal+ Integer.parseInt(nu.getVitaminD());
         vitaminETotal = vitaminETotal + Integer.parseInt(nu.getVitaminE());
         vitaminKTotal = vitaminKTotal + Integer.parseInt(nu.getVitaminK());
         vitaminB1Total = vitaminB1Total + Integer.parseInt(nu.getVitaminB1());
         vitaminB2Total = vitaminB2Total + Integer.parseInt(nu.getVitaminB2());
         vitaminB6Total = vitaminB6Total + Integer.parseInt(nu.getVitaminB6());
         vitamin16Total = vitamin16Total + Integer.parseInt(nu.getVitamin16());
         vitaminCTotal = vitaminCTotal + Integer.parseInt(nu.getVitaminC());
         nicotinicacidTotal = nicotinicacidTotal + Integer.parseInt(nu.getNicotinicacid());
         folicacidTotal = folicacidTotal + Integer.parseInt(nu.getFolicacid());
         pantothenicacidTotal = pantothenicacidTotal + Integer.parseInt(nu.getPantothenicacid());
         biotinTotal = biotinTotal + Integer.parseInt(nu.getBiotin());
         cholineTotal = cholineTotal + Integer.parseInt(nu.getCholine());
         calciumTotal = calciumTotal + Integer.parseInt(nu.getCalcium());
         phosphorusTotal = phosphorusTotal + Integer.parseInt(nu.getPhosphorus());
         potassiumTotal = potassiumTotal + Integer.parseInt(nu.getPotassium());
         sodiumTotal = sodiumTotal + Integer.parseInt(nu.getSodium());
         magnesiumTotal = magnesiumTotal + Integer.parseInt(nu.getMagnesium());
         ironTotal = ironTotal + Integer.parseInt(nu.getIron());
         zincTotal = zincTotal + Integer.parseInt(nu.getZinc());
         iodineTotal = iodineTotal + Integer.parseInt(nu.getIodine());
         seleniumTotal = seleniumTotal + Integer.parseInt(nu.getSelenium());
         copperTotal = copperTotal + Integer.parseInt(nu.getCopper());
         fluorineTotal = fluorineTotal + Integer.parseInt(nu.getFluorine());
         chromiumTotal = chromiumTotal + Integer.parseInt(nu.getChromium());
         manganeseTotal = manganeseTotal + Integer.parseInt(nu.getManganese());
         molybdenumTotal = molybdenumTotal + Integer.parseInt(nu.getMolybdenum());
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("proteinTotal",proteinTotal);
        map.put("fatTotal",fatTotal);
        map.put("saturatedTotal",saturatedTotal);
        map.put("cholesterolTotal",cholesterolTotal);
        map.put("carbohydrateTotal",carbohydrateTotal);
        map.put("dietaryfiberTotal",dietaryfiberTotal);
        map.put("vitaminATotal",vitaminATotal);
        map.put("vitaminDTotal",vitaminDTotal);
        map.put("vitaminETotal",vitaminETotal);
        map.put("vitaminKTotal",vitaminKTotal);
        map.put("vitaminB1Total",vitaminB1Total);
        map.put("vitaminB2Total",vitaminB2Total);
        map.put("vitaminB6Total",vitaminB6Total);
        map.put("vitamin16Total",vitamin16Total);
        map.put("vitaminCTotal",vitaminCTotal);
        map.put("nicotinicacidTotal",nicotinicacidTotal);
        map.put("folicacidTotal",folicacidTotal);
        map.put("pantothenicacidTotal",pantothenicacidTotal);
        map.put("biotinTotal",biotinTotal);
        map.put("cholineTotal",cholineTotal);
        map.put("calciumTotal",calciumTotal);
        map.put("phosphorusTotal",phosphorusTotal);
        map.put("potassiumTotal",potassiumTotal);
        map.put("sodiumTotal",sodiumTotal);
        map.put("magnesiumTotal",magnesiumTotal);
        map.put("ironTotal",ironTotal);
        map.put("zincTotal",zincTotal);
        map.put("iodineTotal",iodineTotal);
        map.put("seleniumTotal",seleniumTotal);
        map.put("copperTotal",copperTotal);
        map.put("fluorineTotal",fluorineTotal);
        map.put("chromiumTotal",chromiumTotal);
        map.put("manganeseTotal",manganeseTotal);
        map.put("molybdenumTotal ",molybdenumTotal);
        return map;
    }

    /*
    查询餐厅经纬度
     */

    public Map selectLngByRestaurant(String param){
        JSONObject paramDate = JSONObject.parseObject(param);
        String restaurant = paramDate.getString("restaurant");
        Map map = stNutritionalAnalysisMapper.selectLngByRestaurant(restaurant);
        Map<String,String> LngMap = new HashMap<>();
        LngMap.put("lng", (String) map.get("lng"));
        LngMap.put("lat",(String)map.get("lat"));
        return LngMap;
    }

    /*
    菜品膳食结构查询
     */
    @Override
    public Map selectDietaryStructure(String param) {
        JSONObject paramData = JSONObject.parseObject(param);
        String restaurant = paramData.getString("restaurant");
        String date = paramData.getString("date");
        List<StDietaryStructureVo> list = stNutritionalAnalysisMapper.selectDietaryStructure(restaurant,date);
        Map<String,Integer> stringMap = new HashMap<>();
        //谷类食物
        int cerealTotal = 0;
        //蔬菜水果
        int vegetableTotal = 0;
        //鱼禽肉蛋
        int fishmeatToatl = 0;
        //奶类和豆类
        int milkTotal = 0;
        //油和食盐
        int oilsaltTotal = 0;

        for (StDietaryStructureVo structureVo : list){
           cerealTotal = cerealTotal + Integer.parseInt(structureVo.getCereal());
           vegetableTotal = vegetableTotal + Integer.parseInt(structureVo.getVegetable());
           fishmeatToatl = fishmeatToatl + Integer.parseInt(structureVo.getFishmeat());
           milkTotal = milkTotal + Integer.parseInt(structureVo.getMilk());
           oilsaltTotal = oilsaltTotal + Integer.parseInt(structureVo.getOilsalt());
        }
        stringMap.put("cerealTotal",cerealTotal);
        stringMap.put("vegetableTotal",vegetableTotal);
        stringMap.put("fishmeatToatl",fishmeatToatl);
        stringMap.put("milkTotal",milkTotal);
        stringMap.put("oilsaltTotal",oilsaltTotal);
        return stringMap;
    }
}
