package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *健康建议
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Stproposal {


    private Integer id;
    /**
     *每人每天应该吃食物重量(1.正常 2.不正常)
     */
    private String weight;
    /**
     *建议每日总能量摄入量（根据体检指标进行给出建议，根据身体状况给出营养物质的体检指标）建议每日总能量摄入量xxx，脂肪xx，蛋白质xx
     */
    private String energyTotal;
    /**
     *早餐
     */
    private String breakFast;
    /**
     *午餐      （分餐再给出建议是营养物质的）
     */
    private String lunch;
    /**
     *晚餐
     */
    private String dinner;
    /**
     *主食
     */
    private String staple;
    /**
     *蔬菜
     */
    private String vegeTables;
    /**
     *肉类
     */
    private String meat;
    /**
     *水果
     */
    private String fruits;
    /**
     *蛋奶
     */
    private String milk;
    /**
     *其它
     */
    private String other;
    /**
     *建议内容（根据身体指标给出的专业建议xxxxxxxxxxxxxxx）
     */
    private String proposed;








}
