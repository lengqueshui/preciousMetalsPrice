package com.bsyd.preciousMetalsPrice.service.metals.pipeLine;

import com.bsyd.preciousMetalsPrice.mapper.MetalPriceShanghaiSgeMapper;
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
public class SgePipeLine implements Pipeline {

    @Autowired
    private MetalPriceShanghaiSgeMapper sgeMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<MetalPriceShanghaiSge> list = resultItems.get("list");
        if (CollectionUtils.isEmpty(list)) {
            log.info("List<MetalPriceShanghaiSge> empty");
            return;
        }

        for (MetalPriceShanghaiSge sge: list) {
            sgeMapper.insert(sge);
        }
    }
}
