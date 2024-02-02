package com.msb.controller;
import com.msb.pojo.User;
import com.msb.service.UserService;
import com.msb.util.ExportExcelUtil;
import com.msb.util.PageBean;
import com.msb.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Slf4j
@RestController
@RequestMapping("/UserController.do")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findUser")
    private ResponseResult findUser(
            @RequestBody User user,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return ResponseResult.success(userService.findUser(user, pageNum, pageSize));
    }

    /**
     * 导出：全部用户信息
     */
    @RequestMapping(value = "/exportEmps",method = RequestMethod.GET)
    public void exportEmps(
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                           HttpServletResponse req
    ) {
        User user=new User();
        user.setName("张三");
        try {
            PageBean<User> pageBean = userService.findUser(user,pageNum, pageSize);
            //工作簿对象
            Workbook workbook = new XSSFWorkbook(
                    Objects.requireNonNull(this.getClass().getClassLoader()
                            .getResourceAsStream("excle/ygb.xlsx")));
            //sheet页
            Sheet sheet = workbook.getSheetAt(0);
            //获取第二行的样式
            Row row1 = sheet.getRow(2);
            CellStyle [] cellStyles = new CellStyle[row1.getLastCellNum()];
            for (int i=0;i < cellStyles.length;i++){
                cellStyles[i] = row1.getCell(i).getCellStyle();
            }

            List<User> users = pageBean.getList();
            for (int i = 0; i < users.size(); i++) {
                row1 = sheet.createRow(i + 1);
                User use = users.get(i);

                Cell useNamecell = row1.createCell(0);
                useNamecell.setCellValue(use.getName());
                useNamecell.setCellStyle(cellStyles[0]);

                Cell cellType = row1.createCell(1);
                cellType.setCellValue(use.getGsType());
                cellType.setCellStyle(cellStyles[1]);

                Cell cellLoginName = row1.createCell(2);
                cellLoginName.setCellValue(use.getLoginName());
                cellLoginName.setCellStyle(cellStyles[2]);


                Cell cellPassword = row1.createCell(3);
                cellPassword.setCellValue(use.getLoginPassword());
                cellPassword.setCellStyle(cellStyles[3]);

                Cell cellSex = row1.createCell(4);
                cellSex.setCellValue(use.getSex());
                cellSex.setCellStyle(cellStyles[4]);


            }
            String fileName = URLEncoder.encode("员工表.xlsx", "UTF-8");
            req.setContentType("applibation/octet-stream");
            req.setHeader("content-disposition", "attachment;filename=" + fileName);
            req.setHeader("fileName", fileName);
            workbook.write(req.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 工具类导出
     */
    @RequestMapping("/export")
    public void export(User user,HttpServletResponse response) throws Exception {
        ExportExcelUtil<User> exportExcelUtil =new ExportExcelUtil();
        user.setName("李四");
        PageBean<User> pageBean  = userService.findUser(user,1,5);
        List<User> userList = pageBean.getList();
        Map<String,Object> params=new HashMap<>();
        params.put("objs",userList);
        params.put("path", "excle/ygb.xlsx");
        params.put("response",response);
        params.put("rowIndex",2);
        //导出
        exportExcelUtil.exportExcel(params);
    }
}
