package com.bsyd.preciousMetalsPrice.service.metals;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

@Slf4j
@Service
public class PlatinumMattheyService implements PageProcessor {

    private static String url = "http://www.platinum.matthey.com/prices/price-tables";

    private Site site = Site
            .me()
            .setSleepTime(500);

    @Override
    public void process(Page page) {
        Document document = Jsoup.parse(page.getHtml().get());
        Element priceTablesFrame = document.select("[id=priceTablesFrame]").first();
        for (Element element: priceTablesFrame.children()) {
            log.info(element.attr("class") + "------->" + element.text());
            for (Element contentElement: element.children()) {
                if (!"tbody".equals(contentElement.nodeName())) {
                    continue;
                }

                log.info("=========");
                for (Element trElement: contentElement.children()) {
                    for (Element thElement: trElement.children()) {
                        log.info("====" +thElement.text());
                    }
                }

            }
        }
//        for (Element element: frameElements) {
//            element.getElementsB
//            log.info("=========" + element.text());
//        }
        //London
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new PlatinumMattheyService()).addUrl(url)
                .run();
    }

}
