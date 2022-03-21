package com.canteen.service.impl;


import com.canteen.entity.StIntelligenceMealsVo;
import com.canteen.entity.Stbodyproject;
import com.canteen.entity.Stproposal;
import com.canteen.entity.SystemUser;
import com.canteen.mapper.StIntelligenceMealMapper;
import com.canteen.mapper.StproposalMapper;
import com.canteen.mapper.SystemUserMapper;
import com.canteen.service.StproposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class StproposalServiceImpl implements StproposalService {

    @Autowired
   private SystemUserMapper systemUserMapper;
    @Autowired
   private StproposalMapper stproposalMapper;
    @Autowired
    private  StIntelligenceMealMapper stIntelligenceMealMapper;

    /**
     * 根据体检指标给出健康建议
     */
    public void getStproposal() {
        SystemUser systemUser= new SystemUser();
        /**
         * 需调用登录获取人员基本信息
         */
        systemUser.setName("李四");
        List<SystemUser> list = systemUserMapper.findSystemUser(systemUser);
        SystemUser  user = list.get(0);
        //获取指标状态
        List<Stbodyproject> stbodyprojectlist =user.getStbodyprojects();
        for(Stbodyproject stbodyproject:stbodyprojectlist){
            if(stbodyproject.getState().equals("0")){
                Stproposal stproposal = new Stproposal();
                stproposal.setBreakFast("根据膳食宝塔建议的各类食物摄入量，每人每天应该吃食物重量如下：谷类食物（淀粉）250g-400g，蔬菜300g-500g，水果200g-400g,畜、禽肉50g-75g，,鱼虾类 50g-100g，蛋类 25g-50g，相当于鲜奶 300g 的奶类及奶制品，相当于干豆 30g-50g的大豆及制品，烹调油不超过25g或 30g，食盐不超过6g。");
                //正常状态下摄入营养数据
                stproposalMapper.updateStproposal(stproposal);
            }else {
                //此方需要同步医院营养数据和员工相应的身体状况：具体分析数据未能提供

            }

        }
    }



    /**
     * 用餐建议
     */
    @Transactional
    public List<Stproposal> findStproposal() {
        getStproposal();
        return stproposalMapper.findStproposal();
    }

    /**
     * 当日菜品
     */
    public List<StIntelligenceMealsVo> findStIntelligenceMealsVo(String restaurant) {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String date= dateFormat.format(calendar.getTime());
        stIntelligenceMealMapper.SelectByRestaurant(restaurant,date);
        return stIntelligenceMealMapper.SelectByRestaurant(restaurant,date);
    }



}
