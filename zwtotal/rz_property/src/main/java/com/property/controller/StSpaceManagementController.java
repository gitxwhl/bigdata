package com.property.controller;

import com.property.service.WySpaceManagementService;
import com.property.utils.PageBean;
import com.property.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/StSpaceManagement.do")
@Slf4j
@CrossOrigin
public class StSpaceManagementController {
    @Autowired
    WySpaceManagementService stSpaceManagementService;

    /**
     * 空间查询
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectSpace",method = RequestMethod.POST)
    private PageBean selectSpace(HttpServletRequest request, @RequestBody String param){
          return stSpaceManagementService.selectSpace(param);
    }

    /**
     * 通过id查询需要修改的空间信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateSpaceById",method = RequestMethod.POST)
    private Map updateSpaceById(HttpServletRequest request,@RequestBody String param){
        return stSpaceManagementService.updateSpaceById(param);
    }

    /**
     * 修改空间信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateSpace",method = RequestMethod.POST)
    private Result updateSpace(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            stSpaceManagementService.updateSpace(param);
            result.setMessage("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("修改失败");
        }
       return result;
    }

    /**
     * 删除空间信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteSpace",method = RequestMethod.POST)
    private Result deleteSpace(HttpServletRequest request,@RequestBody String param){
        Result result = new Result();
        try {
            stSpaceManagementService.deleteSpace(param);
            result.setMessage("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 新增空间信息
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/insertSpace",method = RequestMethod.POST)
    private Result insertSpace(HttpServletRequest request, @RequestBody String param){
        Result result = new Result();
        try {
            stSpaceManagementService.insertSpace(param);
            result.setMessage("新增成功");
        }catch (Exception e){
           e.printStackTrace();
           result.setMessage("新增失败");
        }
        return result;
    }

    /**
     * 查看附件
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/getSpaceFile", method = RequestMethod.POST)
    private Map getSpaceFile(HttpServletRequest request, @RequestBody String param){
        return  stSpaceManagementService.getSpaceFile(param);
    }
}
