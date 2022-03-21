package com.canteen.entity;

/**
 * 健康分析
 */
public class StHealthAnalysis {


    private Integer id;
    /**
     * 当日菜品分析
     */
    private String samedish;
    /**
     * 日期
     */
    private String days;
    /**
     * 订单表外键
     */
    private String order;
    /**
     * 含多少营养物质是否建议使用
     */
    private String inclusion;
    /**
     * 早餐（建议食谱：根据当天的菜单，和身体指标，用餐建议推荐食谱）',
     */
    private String cpbreakfast;
    /**
     * 午餐
     */
    private String cplunch;
    /**
     * 晚餐
     */
    private String cpdinner;
    /**
     * 早餐
     */
    private String breakfasted;
    /**
     * 午餐
     */
    private String lunched;
    /**
     * 晚餐
     */
    private String dinnered;
    /**
     * 主食
     */
    private String proposalfood;
    /**
         * 凉菜
     */
    private String colddishes;
    /**
     * 热菜
     */
    private String hotdishes;
    /**
     * 水果
     */
    private String Fruits;
    /**
     * 汤类
     */
    private String sup;
    /**
     * 'mydis(我的菜品外键)（联表查询找到当天的菜品的营养成分
     */
    private String mydis;





}
