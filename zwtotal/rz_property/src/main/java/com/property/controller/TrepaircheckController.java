package com.property.controller;

import com.property.entity.Trepaircheck;
import com.property.service.TrepaircheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Trepaircheck.do")
@Slf4j
@CrossOrigin
public class TrepaircheckController implements TrepaircheckService {

    @Autowired
   private TrepaircheckService trepaircheckService;

    @RequestMapping("/Trepaircheck")
    public Trepaircheck getTrepaircheckMapper() {
        return trepaircheckService.getTrepaircheckMapper();
    }
}