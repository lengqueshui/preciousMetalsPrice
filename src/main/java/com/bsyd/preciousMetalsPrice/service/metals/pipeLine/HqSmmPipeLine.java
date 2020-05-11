package com.bsyd.preciousMetalsPrice.service.metals.pipeLine;

import com.bsyd.preciousMetalsPrice.mapper.MetalPriceHqsmmMapper;
import com.bsyd.preciousMetalsPrice.model.MetalPriceHqsmm;
import com.bsyd.preciousMetalsPrice.service.metals.HqSmmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Slf4j
@Component
public class HqSmmPipeLine implements Pipeline {

    @Autowired
    private MetalPriceHqsmmMapper mapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<MetalPriceHqsmm> list = resultItems.get("list");
        if (CollectionUtils.isEmpty(list)) {
            log.info("List<MetalPriceHqsmm> empty");
            return;
        }

        for (MetalPriceHqsmm hqsmm: list){
            log.info("hqsmm = " + hqsmm);
            mapper.insert(hqsmm);
        }


    }

}
