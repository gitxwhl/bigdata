package com.mashibing.service.impl;

import com.mashibing.internalcommon.constent.AmapConfigConstents;
import com.mashibing.internalcommon.constent.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.mapper.DicDistrictMapper;
import com.mashibing.remote.MapDicDistrictServiceClint;
import com.mashibing.service.DicDistrictService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DicDistrictServiceImpl implements DicDistrictService
{
    @Autowired
    private MapDicDistrictServiceClint mapDicDistrictServiceClint;
    @Autowired
    private DicDistrictMapper dicDistrictMapper;
    
    
    @Override
    public ResponseResult initDistrict(String keywords) {
        //请求地图
        String responseResult = mapDicDistrictServiceClint.initDistrict(keywords);
        System.out.println(responseResult);
        //解析结果
        //字符串转化为json
        JSONObject districtJson = JSONObject.fromObject(responseResult);
        if(districtJson.has(AmapConfigConstents.STATUS)){
            int status = districtJson.getInt(AmapConfigConstents.STATUS);
            if(status !=1){
                ResponseResult.fail(CommonStatusEnum.DISTRICT_ERROR.getCode(), CommonStatusEnum.DISTRICT_ERROR.getValue());
            }
            JSONArray districtsJsonArray = districtJson.getJSONArray(AmapConfigConstents.DISTRICTS);
            for(int i=0 ;i < districtsJsonArray.size();i++){
                JSONObject countryJson = districtsJsonArray.getJSONObject(i);
                String countryAddressName = countryJson.getString(AmapConfigConstents.NAME);
                String countryAddressadCode = countryJson.getString(AmapConfigConstents.ADCODE);
                String countryParentCode="0";
                String countryAddressLevel = countryJson.getString(AmapConfigConstents.LEVEL);
                //获取等级
                int level = findLevel(countryAddressLevel);
                DicDistrict countryDicDistrict = findDicDistrict(countryAddressadCode, countryAddressName, countryParentCode, level);
                //入库 国家
                dicDistrictMapper.insert(countryDicDistrict);
                JSONArray provinceJsonArray = countryJson.getJSONArray(AmapConfigConstents.DISTRICTS);
                for (int a=0;a < provinceJsonArray.size();a++){
                    JSONObject provincejson = provinceJsonArray.getJSONObject(a);
                    String provinceName = provincejson.getString(AmapConfigConstents.NAME);
                    String provinceAdcode = provincejson.getString(AmapConfigConstents.ADCODE);
                    String provinceLevel = provincejson.getString(AmapConfigConstents.LEVEL);
                    int provinceLevelInt = findLevel(provinceLevel);
                    String provinceParentCode= countryAddressadCode;
                    //获取省级信息
                    DicDistrict provinceDicDistrict = findDicDistrict(provinceAdcode, provinceName, provinceParentCode, provinceLevelInt);
                    //入库 省
                    dicDistrictMapper.insert(provinceDicDistrict);
                    //获取市
                    JSONArray cityjsonArray = provincejson.getJSONArray(AmapConfigConstents.DISTRICTS);
                    for (int p=0;p < cityjsonArray.size();p++){
                        JSONObject cityJson = cityjsonArray.getJSONObject(p);
                        String cityName = cityJson.getString(AmapConfigConstents.NAME);
                        String cityAdcode = cityJson.getString(AmapConfigConstents.ADCODE);
                        String cityLevel = cityJson.getString(AmapConfigConstents.LEVEL);
                        //获取等级数字
                        int cityLevelInt = findLevel(cityLevel);
                        String cityParentCode = provinceAdcode;
                        //获取市级对象
                        DicDistrict cityDicDistrict = findDicDistrict(cityAdcode, cityName, cityParentCode, cityLevelInt);

                        //入库
                        dicDistrictMapper.insert(cityDicDistrict);
                        JSONArray areaJsonArray = cityJson.getJSONArray(AmapConfigConstents.DISTRICTS);
                        for (int ar=0;ar < areaJsonArray.size();ar++){
                            JSONObject areaJson = areaJsonArray.getJSONObject(ar);
                            String areaName = areaJson.getString(AmapConfigConstents.NAME);
                            String areaAdcode = areaJson.getString(AmapConfigConstents.ADCODE);
                            String areaLevel = areaJson.getString(AmapConfigConstents.LEVEL);
                            //获取等级数字
                            int areaLevelInt = findLevel(areaLevel);
                            String areaParentCode = cityAdcode;
                            //获取市级对象
                            DicDistrict areaDicDistrict = findDicDistrict(areaAdcode, areaName, areaParentCode, areaLevelInt);
                            if(AmapConfigConstents.STREET.equals(areaLevel)){
                                continue;
                            }
                            //入库
                            dicDistrictMapper.insert(areaDicDistrict);

                        }

                    }


                }


            }



        }

        //入库



        return null;
    }

    /**
     * 获取地区对象
     * @return
     */
    public DicDistrict findDicDistrict(String addressCode,String addressName,String parentAddressCode,Integer level){
        DicDistrict dicdistrict=new DicDistrict();
        dicdistrict.setAddressCode(addressCode);
        dicdistrict.setAddressName(addressName);
        dicdistrict.setParentAddressCode(parentAddressCode);
        dicdistrict.setLevel(level);
        return dicdistrict;
    }


    //获取level
    public int findLevel(String level){
        int levelInt=0;
        if("country".equals(level.trim())){
            return 0;
        }
        if("province".equals(level.trim())){
            return 1;
        }
        if("city".equals(level.trim())){
            return 2;
        }
        if("district".equals(level.trim())){
            return 3;
        }
        return levelInt;
    }







}
