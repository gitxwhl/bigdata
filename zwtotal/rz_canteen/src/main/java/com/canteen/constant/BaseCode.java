package com.canteen.constant;
/**
 * @Date 2020/8/31
 * @Auther tiandejiang
 * @Description: todo 基础系统码， 建议后期挪入common包
 */
public class BaseCode {

    public static class    BaseResultCode{
        /**
         * 正确情况成果应该是200 ，但是为了编码统一 成功00000 失败00001
         */
        public  static final  String   SUCSESS ="00000";
        public  static final  String   FAILE ="00001";
    }

    /**
     * 各个专属错误码
     */
    public static class    Categorydishes{
        public  static final  String   EXIST_CHILD ="存在子类无法删除";
        public  static final  String   FAILE ="400";
    }


}
