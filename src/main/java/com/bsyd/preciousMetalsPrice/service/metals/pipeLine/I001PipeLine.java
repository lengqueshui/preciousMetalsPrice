package com.bsyd.preciousMetalsPrice.service.metals.pipeLine;

import com.bsyd.preciousMetalsPrice.mapper.MetalPriceI001Mapper;
import com.bsyd.preciousMetalsPrice.mapper.MetalPriceShanghaiSgeMapper;
import com.bsyd.preciousMetalsPrice.model.MetalPriceI001;
import com.bsyd.preciousMetalsPrice.model.MetalPriceShanghaiSge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
@Slf4j
public class I001PipeLine implements Pipeline {

    @Autowired
    private MetalPriceI001Mapper i001Mapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<MetalPriceI001> list = resultItems.get("list");
        if (CollectionUtils.isEmpty(list)) {
            log.info("List<MetalPriceShanghaiSge> empty");
            return;
        }

        for (MetalPriceI001 date: list) {
            log.info("=======" + date);
            i001Mapper.insert(date);
        }
    }
}
