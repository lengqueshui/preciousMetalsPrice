package com.bsyd.preciousMetalsPrice.controller;

import com.bsyd.preciousMetalsPrice.mapper.MetalPriceShanghaiSgeMapper;
import com.bsyd.preciousMetalsPrice.model.MetalPriceShanghaiSge;
import com.bsyd.preciousMetalsPrice.service.metals.SgeComService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/main")
@Slf4j
public class MainController {

    @Autowired
    private SgeComService sgeComService;

    @Autowired
    private MetalPriceShanghaiSgeMapper sgeMapper;

    @RequestMapping("/first")
    @ResponseBody
    public String first(@RequestParam(required = false) String amount) {
        log.info("first");
        MetalPriceShanghaiSge sge = new MetalPriceShanghaiSge();
        sge.setDate(new Date());
        sge.setName("TEST");
        sgeMapper.insert(sge);
        sgeComService.init();
        return "success";
    }
}
