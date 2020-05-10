package com.bsyd.preciousMetalsPrice.service.metals;

import com.bsyd.preciousMetalsPrice.model.MetalPriceI001;
import com.bsyd.preciousMetalsPrice.service.metals.pipeLine.I001PipeLine;
import com.bsyd.preciousMetalsPrice.utils.DigitalUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class I001ComService implements PageProcessor {

    @Autowired
    private I001PipeLine i001PipeLine;

    private static final String ROOT_URL = "http://www.i001.com";

    //http://www.i001.com/main1.shtml
    //http://www.i001.com/main2.shtml
    //http://www.i001.com/main3.shtml
    //http://www.i001.com/main4.shtml
    public static final String URL_POST = "http://www\\.i001\\.com/main(1|2|3)\\.shtml";
    //public static final String URL_POST = "http://www\\.i001\\.com/main1\\.shtml";

    private Site site = Site
            .me()
            .setDomain("www.i001.com")
            .setSleepTime(100)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        log.info("url = " + page.getUrl() + " = " + page.getUrl().regex(URL_POST).match());
        if (!page.getUrl().regex(URL_POST).match()) {
            List<String> list = page.getHtml().xpath("iframe/@src").all();
            for (String srcValue : list) {
                page.addTargetRequest(ROOT_URL + srcValue);
            }

            return;
        }

        log.info("url = " + page.getUrl() + "\r\n" + page.getHtml());
        Document document = Jsoup.parse(page.getHtml().get());
        Elements trElements = document.select("table").select("tr");
        List<MetalPriceI001> list = new ArrayList<>();
        boolean isFirst = true;
        String name = "";
        for (int i = 0; i < trElements.size(); i++) {
            Elements tds = trElements.get(i).select("td");
            if (tds.size() == 2 && tds.get(0).select("table").size() != 0) {
                continue;
            }

            if (tds.size() == 1) {
                name = tds.get(0).text();
                continue;
            }

            if (tds.size() != 5) {
                continue;
            }

            MetalPriceI001 metalPriceI001 = new MetalPriceI001();
            metalPriceI001.setDate(new Date());
            metalPriceI001.setName(name);
            for (int j = 0; j < tds.size(); j++) {
                Element element = tds.get(j);
                String text = element.text();
                if (j == 0) {
                    metalPriceI001.setType(text);
                }

                if (j == 1) {
                    metalPriceI001.setMinQuotationPrice(DigitalUtil.getBigDecimal(text));
                }

                if (j == 2) {
                    metalPriceI001.setMaxQuotationPrice(DigitalUtil.getBigDecimal(text));
                }

                if (j == 3) {
                    if (!text.contains("-")) {
                        text = text.replace("+", "");
                        metalPriceI001.setUpdown(DigitalUtil.getBigDecimal(text));
                    } else {
                        text = text.replace("-", "");
                        metalPriceI001.setUpdown(DigitalUtil.getNegativeBigDecimal(text));
                    }
                }

                if (j == 4) {
                    metalPriceI001.setDayPrice(DigitalUtil.getBigDecimal(text));
                }
            }

            list.add(metalPriceI001);

        }

        page.putField("list", list);
    }

    @Override
    public Site getSite() {
        return site;
    }

    @PostConstruct
    public void init() {
        Spider.create(new I001ComService()).addUrl(ROOT_URL + "/index.shtml")
                .addPipeline(i001PipeLine)
                .run();
    }
}
