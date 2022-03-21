package com.canteen.service;

import com.canteen.utils.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface StMeasurechoicedishesService {
    //获取排菜列表
    PageBean choiceDishList(String paramJson);

    //删除排菜
    String deleteChoiceDish(String paramJson);

    //查询可关联属性（计量设备，菜品名称，所属餐厅）
    Map getRelevance();

    //新增排菜
    String insertChoiceDish(String paramJson);

    //修改排菜
    String updateChoiceDish(String paramJson);

    //导出排菜列表
    Object choiceDishExport(HttpServletRequest request, HttpServletResponse response, String paramJson) throws UnsupportedEncodingException;

    //导入排菜列表
    Object choiceDishImport(HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<String> getfileName();
}
