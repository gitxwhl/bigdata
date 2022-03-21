package com.property.controller;


import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.property.entity.Wyequipment;
import com.property.service.WyEquipmentService;
import com.property.utils.PageBean;
import com.property.utils.QRCodeUtils;
import com.property.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@RestController
@RequestMapping("/StEquipment.do")
@Slf4j
@CrossOrigin
public class WyEquipmentController {
    @Autowired
    WyEquipmentService stEquipmentService;

    //历史维护记录查询
    @RequestMapping(value = "/WySpacemanagementList", method = RequestMethod.POST)
    public Object WySpacemanagementList(HttpServletRequest request, @RequestBody String param) {
        try {
            return stEquipmentService.wyEquipmentList(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "历史维护记录查询失败";
        }
    }

    //历史维护记录工单及策略列表查询
    @RequestMapping(value = "/WySpacemanagementListDis", method = RequestMethod.POST)
    public Object WySpacemanagementListDis(HttpServletRequest request, @RequestBody String param) {
        try {
            return stEquipmentService.WySpacemanagementListDis(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "历史维护记录工单及策略列表查询失败";
        }
    }

    /**
     * 获取设备台账信息
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectEquipment", method = RequestMethod.POST)
    private PageBean selectEquipment(HttpServletRequest request, @RequestBody String param) {
        return stEquipmentService.selectEquipment(param);
    }

    /**
     * 添加设备台账信息
     *
     * @param request
     * @param stEquipment
     * @return
     */
    @RequestMapping(value = "/insertEquipment", method = RequestMethod.POST)
    private Result insertEquipment(HttpServletRequest request, @RequestBody Wyequipment stEquipment) {
        Result result = new Result();
        try {
            stEquipmentService.insertEquipment(stEquipment);
            result.setMessage("增添成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 查询修改设备信息:存在安全风险方法隐藏
     *
     * @param request
     * @param param
     * @return
     */
//    @RequestMapping(value = "/selectEquipmentById", method = RequestMethod.POST)
//    private Map selectEquipmentById(HttpServletRequest request, @RequestBody String param) {
//        return stEquipmentService.selectEquipmentById(param);
//    }

    /**
     * 修改设备
     *
     * @param request
     * @param stEquipment
     * @return
     */
    @RequestMapping(value = "/updateEquipment", method = RequestMethod.POST)
    private Result updateEquipment(HttpServletRequest request, @RequestBody Wyequipment stEquipment) {
        Result result = new Result();
        try {
            stEquipmentService.updateEquipment(stEquipment);
            result.setMessage("修改成功");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 删除设备台账信息
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteEquipment", method = RequestMethod.POST)
    private Result deleteEquipment(HttpServletRequest request, @RequestBody String param) {
        Result result = new Result();
        try {
            stEquipmentService.deleteEquipment(param);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 生成二维码:
     *存在安全风险方法隐藏或者删除
     * @param
     * @param response
     * @throws IOException
     */
//    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
//    public String getCode(HttpServletResponse response,@RequestBody String param) {
//        return stEquipmentService.getCode(param);
//    }
}
