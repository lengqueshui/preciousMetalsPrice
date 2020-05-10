package com.bsyd.preciousMetalsPrice.service.metals;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Slf4j
@Service
public class Jzj9999Service implements PageProcessor {

    private Site site = Site
            .me()
            .setDomain("i.jzj9999.com")
            .setSleepTime(3000)
            .setCharset("UTF-8")
            .setUserAgent(
                    " Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Safari/605.1.15");

    @Override
    public void process(Page page) {
        log.info("==========" + page.getUrl());
        log.info("============================================================");
        log.info("==========" + page.getHtml().xpath("//div[@class='price-table-row']"));
        // 一 WebSocket通信方式，
        //无法解密
//        <div class="price-table-row">
// <div>
//  <div class="el-row is-justify-space-around el-row--flex">
//   <div class="el-col el-col-8">
//    <span class="symbol-name y-middle">黄 金</span>
//   </div>
//   <div class="el-col el-col-6">
//    <div class="symbole-price y-middle">
//     <span class="symbol-price-fall" style="font-size:17px">.</span>
//    </div>
//   </div>
//   <div class="el-col el-col-6">
//    <div class="symbole-price y-middle">
//     <span class="symbol-price-fall" style="font-size:17px">.</span>
//    </div>
//   </div>
//   <div class="el-col el-col-6">
//    <div class="symbole-price">
//     <span class="symbol-price-rise" style="font-size:17px">.</span>
//    </div>
//    <div class="symbole-price">
//     <span class="symbol-price-fall" style="font-size:17px">.</span>
//    </div>
//   </div>
//  </div>
// </div>
// <!---->
//</div>
    }

    //*[@id="__layout"]/div/div[4]/div/div[2]/div/div[1]
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new Jzj9999Service()).addUrl("http://i.jzj9999.com/quoteh5/")
                .addPipeline(new JsonFilePipeline("/Users/mah/Downloads"))
                .run();
    }

}
