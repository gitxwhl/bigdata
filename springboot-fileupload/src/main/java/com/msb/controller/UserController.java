package com.msb.controller;

import com.msb.pojo.User;
import com.msb.service.UserService;
import com.msb.util.PageBean;
import com.msb.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

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
            Workbook workbook = new XSSFWorkbook();
            //sheet页
            Sheet sheet = workbook.createSheet("一月");
            //表头
            String[] titles = {"员工编号", "姓名", "性别", "年龄", "职位", "部门编号"};
            //创建一行
            Row row = sheet.createRow(0);
            for (int i = 0; i < titles.length; i++) {
                //创建一列
                Cell cell = row.createCell(i);
                //将员工的标题设置进去
                cell.setCellValue(titles[i]);
            }
            List<User> users = pageBean.getList();
            for (int i = 0; i < users.size(); i++) {
                row = sheet.createRow(i + 1);
                User use = users.get(i);

                Cell useNamecell = row.createCell(0);
                useNamecell.setCellValue(use.getName());

                Cell Celltype = row.createCell(1);
                Celltype.setCellValue(use.getGsType());


                Cell cellLoginName = row.createCell(2);
                cellLoginName.setCellValue(use.getLoginName());

                Cell cellPassword = row.createCell(3);
                cellPassword.setCellValue(use.getLoginPassword());

                Cell cellSex = row.createCell(4);
                cellSex.setCellValue(use.getSex());

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


}
