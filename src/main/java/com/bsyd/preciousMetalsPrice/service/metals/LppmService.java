package com.bsyd.preciousMetalsPrice.service.metals;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

@Slf4j
@Service
public class LppmService implements PageProcessor {

    private static String url = "https://www.lppm.com/__hist/";

    private Site site = Site
            .me()
            .setSleepTime(500);


    @Override
    public void process(Page page) {
        log.info("===============" + page.getHtml().get());
        //London
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new LppmService()).addUrl(url)
                .run();
    }
}
