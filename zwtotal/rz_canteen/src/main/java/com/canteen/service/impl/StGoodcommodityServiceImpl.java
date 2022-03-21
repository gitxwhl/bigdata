package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.constant.BaseCode;
import com.canteen.entity.*;
import com.canteen.entity.vo.OnlineSupermarketVo;
import com.canteen.entity.vo.OrderDetailVo;
import com.canteen.mapper.*;
import com.canteen.service.DocumentnumberService;
import com.canteen.service.StGoodcommodityService;
import com.canteen.util.DateUtil;
import com.canteen.util.ExcelException;
import com.canteen.util.ExcelUtil;
import com.canteen.util.UUIDUtil;
import com.canteen.utils.DateUtils;
import com.canteen.utils.MySFTP;
import com.canteen.utils.PageBean;
import com.canteen.utils.Results;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class StGoodcommodityServiceImpl implements StGoodcommodityService {
    @Autowired
    private StGoodcommodityMapper stGoodcommodityMapper;
    @Autowired
    private DocumentnumberService documentnumberService;
    @Autowired
    private StOrderingMapper stOrderingMapper;
    @Autowired
    private StCabinetMapper stCabinetMapper;
    @Autowired
    private StMydishstockMapper stMydishstockMapper;
    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;
    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    /**
     * 已上架商品查询
     *
     * @param
     * @param
     * @return
     */
    @Override
    public PageBean<StGoodcommodity> getStGoodcommodityPage(Map<String, Object> map) {
        String id = map.get("id") == null ? "" : map.get("id").toString();
        Integer typeid = (map.get("typeid") == null || map.get("typeid").equals("")) ? 0 : Integer.parseInt(map.get("typeid").toString());
        Integer pageNum = (map.get("pageNum") == null || map.get("pageNum").equals("")) ? 0 : Integer.parseInt(map.get("pageNum").toString());
        Integer pageSize = (map.get("pageSize") == null || map.get("pageSize").equals("")) ? 0 : Integer.parseInt(map.get("pageSize").toString());
        String name = map.get("name") == null ? "" : map.get("name").toString();
        String branchName = map.get("branchName") == null ? "" : map.get("branchName").toString();
        String price = map.get("price") == null ? "" : map.get("price").toString();
        String shelfLife = map.get("shelfLife") == null ? "" : map.get("shelfLife").toString();
        String inventory = map.get("inventory") == null ? "" : map.get("inventory").toString();
        //获取子类id
        String restaurant = stOperationrestaurantService.getIds(id);
        //获取子类typeId
        String typeIds = getTypeIds(typeid);
        //公司超市商品
        PageBean<StGoodcommodity> page = new PageBean<>();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        int count = stGoodcommodityMapper.getGoodcommodityPageCount(inventory, shelfLife, price, branchName, name, typeIds, restaurant);
        page.setTotalRecord(count);
        /**
         * 查询数量为空不用在走数据库查询
         */
        if (count > 0) {
            List<StGoodcommodity> list = stGoodcommodityMapper.getStGoodcommodityPage(inventory, shelfLife, price, branchName, name, (pageNum - 1) * pageSize, pageSize, restaurant, typeIds);
            page.setList(list);
        } else {
            page.setList(new ArrayList<>());
        }
        return page;
    }


    /**
     * 售卖商品列表查询
     * @param
     * @param
     * @return
     */
    @Override
    public PageBean<StGoodcommodity> findGoodListPage(Map<String, Object> map) {
        String name= map.get("name")==null ? "":map.get("name").toString();
        String branchName =map.get("branchName")==null?"":map.get("branchName").toString();
        String shelfLife =map.get("shelfLife")==null?"":map.get("shelfLife").toString();
        String price =map.get("price")==null?"":map.get("price").toString();
        String inventory =map.get("inventory")==null?"":map.get("inventory").toString();
        Integer pageSize=map.get("pageSize")==null? 0 : Integer.parseInt(map.get("pageSize").toString());
        Integer pageNum=map.get("pageNum")==null ? 0 : Integer.parseInt(map.get("pageNum").toString());
        Integer count= stGoodcommodityMapper.findgoodCount(name,branchName,shelfLife,price,inventory);
        PageBean<StGoodcommodity> page = new PageBean<>();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalRecord(count);
        if(count > 0){
            List<StGoodcommodity> StGoodcommodityList= stGoodcommodityMapper.findGoodListPage((pageNum - 1) * pageSize,pageSize,name,branchName,shelfLife,price,inventory);
            page.setList(StGoodcommodityList);
        }else {
            page.setList(new ArrayList<>());
        }
        return page;
    }





    @Override
    public Results<Boolean> insertOnlineSupermarketOrder(String userId, OnlineSupermarketVo order) {
        List<StOrderDishes> list = order.getList();
        if (list == null || list.size() == 0) {
            return new Results<>(BaseCode.BaseResultCode.FAILE, "订单错误", false);
        }
        OrderDetailVo detailVo = order.getOrder();
        //------------ 此处代码后面需要题公共方法----------------------//
        //生成订单编号
        String orderNo = documentnumberService.getOrderNo("DD");
        StOrdering stOrdering = new StOrdering();
        stOrdering.setOrderNum(orderNo);
        stOrdering.setAddr(detailVo.getAddress());
        stOrdering.setPersonnel(userId);
        stOrdering.setAddress(detailVo.getAddress());
        boolean addOrdering = stOrderingMapper.addOrdering(stOrdering);
        if (!addOrdering) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Results<>(BaseCode.BaseResultCode.FAILE, "系统异常稍后重试", false);
        }
        BigDecimal costTotal = new BigDecimal(0);
        //计算总额
        for (StOrderDishes dish : list) {
            Double price = stOrderingMapper.getPrice(dish.getId());
            String dishNumStr = dish.getDishNum();
            int dishNum = Integer.parseInt(dishNumStr);
            //检查库存，库存不足直接返回
            StMydishstock stMydishstock = stMydishstockMapper.getStMydishstockByDisId(dish.getDishId());
            //如果库存不足，不能够提交
            if (stMydishstock.getAvaStock() < dishNum) {
                /**
                 * 此处返回成功的失败，即 需要提示客户，00000返回 datafalse
                 */
                return new Results<>(BaseCode.BaseResultCode.SUCSESS, "库存不足，请重新下单", false);
            }
            costTotal = costTotal.add(new BigDecimal(price).multiply(new BigDecimal(dishNum)));
            boolean orderDishes = stOrderingMapper.orderDishes(orderNo, dish.getDishId(), dishNumStr);
            if (!orderDishes) {
                /**
                 * 上面已经出现提交，需要回滚
                 */
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new Results<>(BaseCode.BaseResultCode.FAILE, "系统异常稍后重试", false);
            }
            //确定库存，先在数据库锁定当前单库存， 关联逻辑（订单失效前未支付，释放库存。支付修改库存）
            boolean updateStock = stMydishstockMapper.updateStock(stMydishstock.getId(), dishNum);
            /**
             * 更新方法应该在最后，更加容易解决并发。放此处更容易理解
             */
            if (!updateStock) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new Results<>(BaseCode.BaseResultCode.SUCSESS, "库存不足，请重新下单", false);
            }

        }

        StOrderManagement stOrderManagement = new StOrderManagement();
        stOrderManagement.setOrderNumber(orderNo);
        stOrderManagement.setReservePersonnel(userId);
        stOrderManagement.setTelephone(detailVo.getTelephone());
        stOrderManagement.setRestaurant(detailVo.getRestaurant());
        stOrderManagement.setScheduled(detailVo.getScheduled());
        stOrderManagement.setDictionary("");
        //获取当前时间作为下单时间
        stOrderManagement.setPickTime(DateUtils.now());
        stOrderManagement.setCostTotal(costTotal.toString());
        stOrderManagement.setOrdertype("1");
        /**
         *  调用线下 公共map
         */
        boolean addOrderManagement = stOrderingMapper.addOrderManagement(stOrderManagement);
        if (!addOrderManagement) {
            /**
             * 上面已经出现提交，需要回滚
             */
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Results<>(BaseCode.BaseResultCode.FAILE, "系统异常稍后重试", false);
        }

        return new Results<>(BaseCode.BaseResultCode.SUCSESS, "订单提交成功，请支付", true);
    }

    /**
     * 商品类别(一级)
     *
     * @param
     * @param
     * @return
     */
    @Override
    public List<Map> findStGoodcommodity() {
        List<Map> stGoodcommodity = stGoodcommodityMapper.findStGoodcommodity();
        Map map = new LinkedHashMap();
        map.put("id", 0);
        map.put("branchname", "不限");
        stGoodcommodity.add(0, map);
        return stGoodcommodity;
    }

    /**
     * 商品类别(二级)
     *
     * @param param {"id":""}
     * @return
     */
    @Override
    public Object findStGoodcommodityTwo(String param) {
        JSONObject rowData = JSONObject.parseObject(param);
        String id = rowData.getString("id");
        List<Map> stGoodcommodityTwo = new ArrayList<>();
        if (id != null && !id.equals("0")) {
            stGoodcommodityTwo = stGoodcommodityMapper.findStGoodcommodityTwo(id);
            Map map = new LinkedHashMap();
            map.put("id", Integer.parseInt(id));
            map.put("branchname", "不限");
            stGoodcommodityTwo.add(0, map);
        }
        return stGoodcommodityTwo;
    }

    /**
     * 添加商品
     *
     * @param stGoodcommodity
     * @return
     */
    @Override
    public Results<Boolean> saveGood(StGoodcommodity stGoodcommodity) {
        //时区转换
        String dateTime = stGoodcommodity.getProductionDate();
        dateTime = dateTime.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date time = format.parse(dateTime);
            String result = defaultFormat.format(time);
            stGoodcommodity.setProductionDate(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!stGoodcommodity.getPictureurl().isEmpty()) {
            byte[] inputStream = MySFTP.getInputStream(stGoodcommodity.getPictureurl());
            stGoodcommodity.setPicture(inputStream);
        }
        return new Results<Boolean>(BaseCode.BaseResultCode.SUCSESS,
                "保存数据", stGoodcommodityMapper.saveGood(stGoodcommodity));
    }

    /**
     * 商品上下架
     */
    @Override
    public Results<Boolean> updateEnable(StGoodcommodity stGoodcommodity) {
        if (stGoodcommodity.getIsshelf().equals("0")) {
            stGoodcommodity.setIsshelf("1");
        } else {
            stGoodcommodity.setIsshelf("0");
        }
        boolean result = stGoodcommodityMapper.updateEnable(stGoodcommodity);
        if (result) {
            return new Results<Boolean>(BaseCode.BaseResultCode.SUCSESS,
                    "更新成功", result);
        } else {
            return new Results<Boolean>(BaseCode.BaseResultCode.FAILE,
                    "数据异常", result);
        }
    }

    public String getTypeIds(Integer id) {
        String ids = "0";
        if (id != 0) {
            String level = stGoodcommodityMapper.getLevel(id);
            if (level.equals("1")) {
                List<Map> stGoodcommodityTwo = stGoodcommodityMapper.findStGoodcommodityTwo(id.toString());
                StringBuilder sb = new StringBuilder();
                for (Map map : stGoodcommodityTwo) {
                    sb.append(map.get("id")).append(",");
                }
                if (sb.length() > 0) {
                    ids = sb.substring(0, sb.length() - 1);
                } else {
                    ids = id.toString();
                }
            } else {
                ids = id.toString();
            }
        }
        return ids;
    }

/**
 * @desc: 任务导入excel操作
 */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void importTaskExcel(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        //任务列表用于批量插入
        List<StGoodcommodity> teList = new ArrayList<StGoodcommodity>();
        //任务和CRM工号的关系列表
        List<StGoodcommodity> tcnreList = new ArrayList<StGoodcommodity>();
        //excel导入操作的用户
//        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //获取当前时间字符串用于设置数据的创建时间
        String time = DateUtil.date2Str(new Date(), DateUtil.datetimeFormat);
        //用来放置excel中的数据和数据库中的数据重复的错误信息
        StringBuffer errMsg = new StringBuffer("");
        if (!ExcelUtil.isExcelFile(fileName)) {
            throw new ExcelException("上传文件格式不正确");
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        //根据excel版本的不同生成不同的操作对象
        if (ExcelUtil.isExcel2003(fileName)) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }

        Sheet sheet = wb.getSheetAt(0);

        if (null != sheet) {
            //i=0是excel里面的第一行，导入的excel第一行都是标题，所有i从1开始循环
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                //防止出现excel的空数据行问题。
                if (null == row || null == row.getCell(1) ||
                        "".equals(row.getCell(1)) || "".equals(row.getCell(1).getStringCellValue())) {
                    continue;
                }
                StGoodcommodity te = new StGoodcommodity();
                te.setId(Integer.parseInt(UUIDUtil.getUUID()));
                te.setName(te.getName());
                te.setBranchName(te.getBranchName());
                te.setGoodsNumber(te.getGoodsNumber());
                te.setProductionDate(te.getProductionDate());
                te.setShelfLife(te.getShelfLife());
                te.setManufacturer(te.getManufacturer());
                te.setLicenseNumber(te.getLicenseNumber());
                te.setProductNumber(te.getProductNumber());
                te.setInventory(te.getInventory());
                te.setWarehousingTime(te.getWarehousingTime());
                teList.add(te);
                if (!"".equals(errMsg.toString()) && errMsg.length() > 1) {
                    throw new ExcelException(errMsg.toString());
                }
            }
        } else {
            throw new ExcelException("上传文件是空的");
        }
}


    /**
     * 入库并上架
     * @param stGoodcommodity
     * @return
     */
    @Override
    public Results<Boolean> updateWarehousing(StGoodcommodity stGoodcommodity) {
        boolean result = stGoodcommodityMapper.updateWarehousing(stGoodcommodity);
        if (result) {
            return new Results<Boolean>(BaseCode.BaseResultCode.SUCSESS,
                    "操作成功", result);
        } else {
            return new Results<Boolean>(BaseCode.BaseResultCode.FAILE,
                    "数据异常", result);
        }

    }







}

