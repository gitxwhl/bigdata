package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel {
    private Integer v;      //版本标识，传固定值 3
    private String d;       //发起请求的时间 如：2018-02-24 11:47:01，精确到秒
    private String format;  //应答数据格式，传固定值 json
    private Integer shop;   //餐厅编号，雄伟提供
    private Integer term;   //终端编号，雄伟提供
    private String oper;    //终端操作员
    private Integer t;      //请求类型
    private Integer pi;     //页码，取值必须大于 0 页码从 1 开始
    private Integer ps;     //页大小：取值范围 1-1000，默认为 20
    private String sd;      //开始时间格式：2018-01-01 06:00:00
    private String ed;      //结束时间格式：2018-01-01 21:00:00
    private Long seq;       //业务流水号
    private String sig;     //签名数据
    private String date;    //日期 eg: 2017-12-01
    private String shopname;    //餐厅名称
    private Integer id;     //菜品id

    public TokenModel(Integer v, String d, String format, Integer shop, Integer term, String oper, Integer t, Integer pi, Integer ps, String sd, String ed) {
        this.v = v;
        this.d = d;
        this.format = format;
        this.shop = shop;
        this.term = term;
        this.oper = oper;
        this.t = t;
        this.pi = pi;
        this.ps = ps;
        this.sd = sd;
        this.ed = ed;
    }
}
