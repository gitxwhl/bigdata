package com.canteen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品表
 */
@Data
public class StGoodcommodity {
    private Integer id;
    private String parentId;
    private String parentIds;
    /**
     *分类编码
     */
    private String no;
    /**
     *分类名称
     */
    private String branchName;
    /**
     *名称
     */
    private String name;
    /**
     *重量
     */
    private String weight;
    /**
     *简介
     */
    private String introduction;
    /**
     *价格
     */
    private String price;
    /**
     *数量
     */
    private String number;
    /**
     *单位
     */
    private String company;

    /**
     *运维餐厅
     */
    private String restaurant;
    /**
     *是否上架(0:已上架，1未上架)
     */
    private String isshelf;
    /**
     * 图片流
     */
    private byte[] picture;
    /**
     * 图片地址
     */
    private String pictureurl;
    /**
     *购物车外键
     */
    private String shop;
    /**
     *图片
     */
    private String pictures;
    /**
     *(0:显示 1：隐藏)
     */
    private String delFlag;
    /**
     *等级
     */
    private String LEVEL;
    /**
     *物品过大（否：0是：1）
     */
    private String isOversize;
    /**
     *销量
     */
    private String saleNum;
    /**
     *保质期
     */
    private String shelfLife;
    /**
     *存放条件
     */
    private String storageCondition;
    /**
     *货号
     */
    private String goodsNumber;
    /**
     *生产日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String productionDate;

    /**
     *配料
     */
    private String ingredients;
    /**
     *地址
     */
    private String address;
    /**
     *产地
     */
    private String placeorigin;
    /**
     *许可证编号
     */
    private String licenseNumber;
    /**
     *生产厂商
     */
    private String manufacturer;
    /**
     *产品编号
     */
    private String productNumber;
    /**
     *库存量
     */
    private String inventory;
    /**
     *最后入库时间
     */
    private String warehousingTime;
    /**
     *是否入库（否：0是：1）
     */
    private String isWarehousing;

    List<StGoodcommodity> Subclass  = new ArrayList<>();

}
