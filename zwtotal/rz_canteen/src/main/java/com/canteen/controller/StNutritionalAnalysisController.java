package com.canteen.controller;

import com.canteen.service.StNutritionalAnalysisService;
import com.canteen.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/StNutritionalAnalysis.do")
@Slf4j
@CrossOrigin
public class StNutritionalAnalysisController {
    @Autowired
    StNutritionalAnalysisService stNutritionalAnalysisService;

    /**
     * 食 谱 营养分析
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/totalEnergyAnalysis" ,method = RequestMethod.POST)
    private ResultMessage totalEnergyAnalysis(HttpServletRequest request, @RequestBody String param) throws ParseException {
        ResultMessage resultMessage = new ResultMessage();
        Map map = new HashMap();
        map.put("totalEnergy",stNutritionalAnalysisService.totalEnergyAnalysis(param));
        map.put("distributed",stNutritionalAnalysisService.distributedEnergyAnalysis(param));
        map.put("Nutrient",stNutritionalAnalysisService.NutrientAnalysis(param));
        map.put("selectLng",stNutritionalAnalysisService.selectLngByRestaurant(param));
        map.put("DietaryStructure",stNutritionalAnalysisService.selectDietaryStructure(param));
        resultMessage.setMap(map);
        return resultMessage;
    }

}
