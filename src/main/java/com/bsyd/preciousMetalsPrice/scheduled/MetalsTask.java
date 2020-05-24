package com.bsyd.preciousMetalsPrice.scheduled;

import com.bsyd.preciousMetalsPrice.service.metals.HqSmmService;
import com.bsyd.preciousMetalsPrice.service.metals.I001ComService;
import com.bsyd.preciousMetalsPrice.service.metals.SgeComService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetalsTask {

    @Autowired
    private HqSmmService hqSmmService;

    @Autowired
    private I001ComService i001ComService;

    @Autowired
    private SgeComService sgeComService;

    @Scheduled(cron = "0 30 20 * * ? ")
    protected void schedulesRequestPrice() {
        log.info("schedulesRequestPrice start");
        hqSmmService.init();
        i001ComService.init();
        sgeComService.init();
        log.info("schedulesRequestPrice over");
    }
}
