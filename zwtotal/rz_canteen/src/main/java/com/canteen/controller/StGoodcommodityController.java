package com.canteen.controller;
import com.canteen.constant.BaseCode;
import com.canteen.entity.StGoodcommodity;
import com.canteen.entity.vo.OnlineSupermarketVo;
import com.canteen.service.StGoodcommodityService;
import com.canteen.util.ExcelDataType;
import com.canteen.util.FileUtils;
import com.canteen.util.Result;
import com.canteen.utils.DateUtils;
import com.canteen.utils.PageBean;
import com.canteen.utils.Results;
import com.github.pagehelper.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @ClassName StOnlineSupermarketController
 * @Date 2020/9/7
 * @Auther tiandejiang
 * @Description: 网上超市；超市查询列表； 超市订单
 * @Vserion 1.0.0
 */
@Api(tags = {"StOnlineSupermarketController"}, description = "网上超市")
@RestController
@RequestMapping("/StOnlineSupermarket.do")
@Slf4j
@CrossOrigin
public class StGoodcommodityController {


    @Autowired
    StGoodcommodityService stGoodcommodityService;


    @ApiOperation(value = "在线购物超市类别查询", notes = "超市类别查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "品类/名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "餐厅id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "typeid", value = "类别id", dataType = "int", paramType = "query"),
            /*@ApiImplicitParam(name = "branchName", value = "商品名称", dataType = "String", paramType = "query"),*/
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页长度", dataType = "int", paramType = "query", defaultValue = "5")
    })
    @PostMapping("/getStOnlineSupermarket")
    public Results<PageBean<StGoodcommodity>> getStGoodcommodityPage(@RequestBody Map<String,Object> map) {
        PageBean<StGoodcommodity> StGoodcommodityList = stGoodcommodityService.getStGoodcommodityPage(map);
        return new Results<PageBean<StGoodcommodity>>(BaseCode.BaseResultCode.SUCSESS, "", StGoodcommodityList);
    }





    /**
     * 售卖商品查询
     * @param
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "售卖商品查询", notes = "售卖商品查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "branchName", value = "类别名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "shelfLife", value = "保质期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "价格", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inventory", value = "库存量", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页长度", dataType = "int", paramType = "query", defaultValue = "5")
    })
    @PostMapping("/findGoodListPage")
    public Results<PageBean<StGoodcommodity>> findGoodListPage(@RequestBody Map<String,Object> map) {
        PageBean<StGoodcommodity> StGoodcommodityList = stGoodcommodityService.findGoodListPage(map);
        return new Results<PageBean<StGoodcommodity>>(BaseCode.BaseResultCode.SUCSESS, "", StGoodcommodityList);
    }


    /**
     * 获取商品一级类别
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping("/getStOnlineSupermarketType")
    public Object getStOnlineSupermarketType(){
        try {
            return stGoodcommodityService.findStGoodcommodity();
        }catch (Exception e){
            e.printStackTrace();
            return "获取一级类别菜单失败";
        }
    }

    /**
     * 获取商品二级类别
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/getStOnlineSupermarketTypeTwo",method = RequestMethod.POST)
    public Object getStOnlineSupermarketTypeTwo(@RequestBody String param){
        try {
            return stGoodcommodityService.findStGoodcommodityTwo(param);
        }catch (Exception e){
            e.printStackTrace();
            return "获取二级类别菜单失败";
        }
    }
    @ApiOperation(value = "网上超市下单", notes = "超市食品查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessToken", value = "通过header的token获取登录用户id", dataType = "String", paramType = "header")
    })
    @RequestMapping(value = "/insertOnlineSupermarketOrder", method = RequestMethod.POST)
    public Results<Boolean> insertOnlineSupermarketOrder(@RequestHeader String accessToken,
                                                         @RequestBody OnlineSupermarketVo order, HttpServletRequest request) {
        /**
         *  TODO
         * 获取userId
         */
        String userId =  "1";//getUserIdbytoekn(accessToken) ;
        if(StringUtil.isEmpty(userId)){
            return  new Results<>(BaseCode.BaseResultCode.FAILE,"登录失效",false);
        }
        Results<Boolean>  results = stGoodcommodityService.insertOnlineSupermarketOrder(userId,order);
        //提交失败需要手动回滚
        if(!results.getData()){
            //系统没有配置整体事务，挪入service
           // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return results;
    }

    /**
     * 添加商品
     */
    @RequestMapping(value = "/saveGood", method = RequestMethod.POST)
    public Results<Boolean> saveGood(@RequestBody StGoodcommodity stGoodcommodity, HttpServletRequest request) {
                return stGoodcommodityService.saveGood(stGoodcommodity);
    }



    /**
     * 商品导出
     */
    @RequestMapping(value = "/StOnlineSupermarketExport", method = RequestMethod.POST)
    public void batchExportGood(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String,Object> map){
        try {
        PageBean<StGoodcommodity> stGoodcommodity= stGoodcommodityService.getStGoodcommodityPage(map);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        CreationHelper creationHelper = hssfWorkbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/m/d h:mm")); // 设置日期格式
        HSSFSheet sheet = hssfWorkbook.createSheet("商品列表");
        sheet.autoSizeColumn(0);// 宽度自适应
        sheet.setDefaultColumnWidth(15); // 设置表格默认列宽度为15个字节
        HSSFRow headRow = sheet.createRow(0);
        // 表头
        headRow.createCell(0).setCellValue("商品名称");
        headRow.createCell(1).setCellValue("商品类别");
        headRow.createCell(2).setCellValue("商品价格");
        headRow.createCell(3).setCellValue("货号");
        headRow.createCell(4).setCellValue("生产日期");
        headRow.createCell(5).setCellValue("保质期");
        headRow.createCell(6).setCellValue("生产厂商");
        headRow.createCell(7).setCellValue("生产许可证");
        headRow.createCell(8).setCellValue("产品编号");
        headRow.createCell(9).setCellValue("库存量");
        headRow.createCell(10).setCellValue("最后入库时间");
        headRow.createCell(11).setCellValue("设置是否上架");
           List<StGoodcommodity> stGoodcommodityList = stGoodcommodity.getList();
        for (int i = 0; i < stGoodcommodityList.size(); i++) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(i + 1);
            dataRow.createCell(0).setCellValue(stGoodcommodityList.get(i).getName());
            dataRow.createCell(1).setCellValue(stGoodcommodityList.get(i).getBranchName());
            dataRow.createCell(2).setCellValue(stGoodcommodityList.get(i).getPrice());
            dataRow.createCell(3).setCellValue(stGoodcommodityList.get(i).getGoodsNumber());
            dataRow.createCell(3).setCellValue(stGoodcommodityList.get(i).getProductionDate());
            dataRow.createCell(4).setCellValue(stGoodcommodityList.get(i).getShelfLife());
            dataRow.createCell(5).setCellValue(stGoodcommodityList.get(i).getManufacturer());
            dataRow.createCell(6).setCellValue(stGoodcommodityList.get(i).getLicenseNumber());
            dataRow.createCell(7).setCellValue(stGoodcommodityList.get(i).getInventory());
            dataRow.createCell(8).setCellValue(stGoodcommodityList.get(i).getWarehousingTime());
        }
        // 下载导出
        // 设置头信息
        response.setContentType("application/vnd.ms-excel");
        String agent = request.getHeader("user-agent");
        response.setHeader("Content-Disposition", "attachment;filename="
                + FileUtils.encodeDownloadFilename("商品" + DateUtils.now() + ".xml", agent));
        ServletOutputStream outputStream = response.getOutputStream();
        hssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    } catch (Exception e) {
        log.error("<===============导出商品列表异常>", e);
        e.printStackTrace();
    }
    }

    /**
     * 商品上下架设置
     */
    @RequestMapping(value = "/updateEnable",method = RequestMethod.POST)
    public Results<Boolean> updateEnable(@RequestBody StGoodcommodity stGoodcommodity){
        if(stGoodcommodity.getId() == null || stGoodcommodity.getIsshelf()==null){
            return   new  Results<Boolean>(BaseCode.BaseResultCode.FAILE,"数据异常",false);
        }
        return stGoodcommodityService.updateEnable(stGoodcommodity);
    }


    /**
     * 导入
     */
    @RequestMapping(value = "/upload")
    public Result<StGoodcommodity> upload(HttpServletRequest request, HttpServletResponse response){
        Result<StGoodcommodity> result = new Result<StGoodcommodity>();
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
            stGoodcommodityService.importTaskExcel(mf);
            result.success("任务导入成功");
        }catch(Exception e){
            e.printStackTrace();
            result.error500(e.getMessage());
        }
        return result;
    }


    /**
     * 导入模板：任务导入模板的输出，为避免导入的在代码获取各列值时出现数据格式问题，导出的模板给每列设置了默认格式。
     */
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("任务导入模板");
        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        //设置默认列宽和行高奇葩的宽和高的单位竟然不一样
        sheet.setDefaultColumnWidth(10);
        sheet.setDefaultRowHeight((short)(15*20));

        row.createCell(0).setCellValue("商品名称");
        //设置每列的默认格式，防止导入的时候需要处理很多数据格式问题
        //CellStyle这个对象的主要功能就是设置单元格的数据样式:填充色、字体大小、字体颜色、数据格式……
        CellStyle text = wb.createCellStyle();
        //设置数据格式，数据格式见BuiltinFormats类有说明。
        text.setDataFormat((short) ExcelDataType.TEXT);
        sheet.setDefaultColumnStyle(0,text);
        row.createCell(1).setCellValue("商品类别");
        sheet.setDefaultColumnStyle(1,text);
        row.createCell(2).setCellValue("商品价格");
        sheet.setDefaultColumnStyle(2,text);
        row.createCell(3).setCellValue("货号");
        sheet.setDefaultColumnStyle(3,text);
        row.createCell(4).setCellValue("生产日期");
        //因为中文的时间格式实在是在BuiltinFormats中找不到，so可以使用HSSFDataFormat这个类来直接获取
        CellStyle timeType = wb.createCellStyle();
        HSSFDataFormat format= wb.createDataFormat();
        timeType.setDataFormat(format.getFormat("yyyy/m/d"));
        sheet.setDefaultColumnStyle(4,timeType);

        sheet.setDefaultColumnStyle(4,text);
        row.createCell(5).setCellValue("保质期");
        //因为中文的时间格式实在是在BuiltinFormats中找不到，so可以使用HSSFDataFormat这个类来直接获取
        CellStyle timeType1 = wb.createCellStyle();
        HSSFDataFormat format1= wb.createDataFormat();
        timeType.setDataFormat(format.getFormat("yyyy/m/d"));
        sheet.setDefaultColumnStyle(5,timeType1);

        sheet.setDefaultColumnStyle(5,text);
        row.createCell(6).setCellValue("生产厂商");

        sheet.setDefaultColumnStyle(6,text);
        row.createCell(7).setCellValue("生产许可证");

        sheet.setDefaultColumnStyle(7,text);
        row.createCell(8).setCellValue("产品编号");

        sheet.setDefaultColumnStyle(8,text);
        row.createCell(9).setCellValue("库存量");

        sheet.setDefaultColumnStyle(9,text);
        row.createCell(10).setCellValue("最后入库时间");
        //因为中文的时间格式实在是在BuiltinFormats中找不到，so可以使用HSSFDataFormat这个类来直接获取
        CellStyle timeType2 = wb.createCellStyle();
        HSSFDataFormat format2= wb.createDataFormat();
        timeType.setDataFormat(format.getFormat("yyyy/m/d"));
        sheet.setDefaultColumnStyle(10,timeType2);

        sheet.setDefaultColumnStyle(10,text);
        row.createCell(11).setCellValue("是否上架");

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=任务导入模板.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        wb.close();
        os.close();
    }
    /**
     * 入库并上架
     */
    @RequestMapping(value = "/updateWarehousing")
    Results<Boolean> updateWarehousing(StGoodcommodity stGoodcommodity){
        if(stGoodcommodity.getId() == null ){
            return   new  Results<Boolean>(BaseCode.BaseResultCode.FAILE,"数据异常",false);
        }
        return  stGoodcommodityService.updateWarehousing(stGoodcommodity);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/updateStCategorydishes")
    public Results<Boolean>  updateStCategorydishes(@RequestBody StGoodcommodity stGoodcommodity, HttpServletRequest request, HttpServletResponse response){
        if(stGoodcommodity.getId() == null){
            return   new  Results<Boolean>(BaseCode.BaseResultCode.FAILE,"数据异常",false);
        }
        return stGoodcommodityService.updateWarehousing(stGoodcommodity);
    }




}
