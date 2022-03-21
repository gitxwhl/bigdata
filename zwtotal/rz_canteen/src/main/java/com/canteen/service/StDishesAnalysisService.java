package com.canteen.service;

import com.canteen.utils.PageBean;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StDishesAnalysisService {
    //菜品整体评价
    PageBean selectAnalysisOfMyFoods(String param);

    //回复用户评论
    void replay(String replay,int id,String replaydate);

    //修改用户评论
   void updateReplay(String param);

   //删除用户评论
    void deleteReplay(int id);

    //根据关键字筛选评论
    List FilterComments(String param);

    //获取排餐信息
    List selectIntelligenceMeals(String param);

    //查询用户评价
    PageBean selectUserComment(String param);

    //用户发表评论
    void postComment(String param) throws IOException;
}
