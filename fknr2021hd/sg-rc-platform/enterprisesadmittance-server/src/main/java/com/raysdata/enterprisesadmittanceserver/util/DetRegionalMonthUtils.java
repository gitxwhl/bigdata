package com.raysdata.enterprisesadmittanceserver.util;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.enterprisesadmittanceserver.controller.personnelInformationController;
import com.raysdata.enterprisesadmittanceserver.servrce.personnelInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component   //定时任务加载类
public class DetRegionalMonthUtils {
    private final Logger log = LoggerFactory.getLogger(personnelInformationController.class);

    @Autowired
    private personnelInformationService personInService;

    //定时日任务加载方法 每隔一个小时执行一次
    @Scheduled(cron = "0 0 0/1 * * ?")
    //测试 一分钟执行一次
//    @Scheduled(cron = "0 */1 * * * ?")
    public void getRegionalMonthAdd() {
        WrappedResult.successWrapedResult(this.personInService.getRegionalMonthAdd());
    }

}
