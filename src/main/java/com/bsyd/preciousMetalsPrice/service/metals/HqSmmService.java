package com.bsyd.preciousMetalsPrice.service.metals;


import com.bsyd.preciousMetalsPrice.model.MetalPriceHqsmm;
import com.bsyd.preciousMetalsPrice.model.MetalPriceI001;
import com.bsyd.preciousMetalsPrice.service.metals.pipeLine.HqSmmPipeLine;
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
public class HqSmmService implements PageProcessor {

    @Autowired
    private HqSmmPipeLine hqSmmPipeLine;

    private static final String ROOT_URL = "https://hq.smm.cn";

    //http://www.i001.com/main1.shtml
    //http://www.i001.com/main2.shtml
    //http://www.i001.com/main3.shtml
    //http://www.i001.com/main4.shtml
    public static final String URL_POST = "http://www\\.i001\\.com/main(1|2|3)\\.shtml";
    //public static final String URL_POST = "http://www\\.i001\\.com/main1\\.shtml";

    private Site site = Site
            .me()
            .setSleepTime(100)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {


        Document document = Jsoup.parse(page.getHtml().get());
        Elements tableElements = document.getElementsByClass("table-list");
        List<MetalPriceHqsmm> list = new ArrayList<>();
        for (int i = 0; i < tableElements.size(); i++) {
            Element tableElement = tableElements.get(i);
            for (Element trElement: tableElement.select("tr")) {
                Elements tdElements = trElement.select("td");
                log.info("tr =======  ");
                MetalPriceHqsmm hqsmm = new MetalPriceHqsmm();
                for (int j = 0; j < tdElements.size(); j++) {
                    Element tdElement = tdElements.get(j);
                    String text = tdElement.text();
                    if (text.contains("名称")) {
                        hqsmm = null;
                        break;
                    }

                    hqsmm.setDate(new Date());
                    if (j == 0) {
                        hqsmm.setName(text.replace("工厂自提指导价", "")
                                .replace("仓库自提指导价", "")
                                .replace("加", "").trim());
                    }

                    if (j == 1) {
                        hqsmm.setPriceScope(text);
                    }

                    if (j == 2) {
                        hqsmm.setPriceAverage(DigitalUtil.getBigDecimal(text));
                    }

                    if (j == 3) {
                        if (!text.contains("-")) {
                            text = text.replace("+", "");
                            hqsmm.setUpdown(DigitalUtil.getBigDecimal(text));
                        } else {
                            text = text.replace("-", "");
                            hqsmm.setUpdown(DigitalUtil.getNegativeBigDecimal(text));
                        }
                    }

                    if (j == 4) {
                        hqsmm.setUnits(text);
                    }

                    if (j == 5) {
                        hqsmm.setPriceDate(text);
                    }


                    log.info("td  " + tdElement.text());
                }

                if (hqsmm != null) {
                    list.add(hqsmm);
                }

            }
        }

        page.putField("list", list);
//        22:40:47.683  - trElement 名称 价格范围 均价 涨跌 单位 日期
//        22:40:47.683  - trElement 1#银 工厂自提指导价 3741-3745 3743 +97 元/千克 05-08
//        22:40:47.683  - trElement 2#银 工厂自提指导价 3726-3730 3728 +97 元/千克 05-08
//        22:40:47.683  - trElement 3#银 工厂自提指导价 3711-3715 3713 +97 元/千克 05-08
//        22:40:47.683  - trElement 金99 工厂自提指导价 382.51-382.51 382.51 +3.66 元/克 05-08
//        22:40:47.684  - trElement 金95 工厂自提指导价 382.80-382.80 382.80 +3.90 元/克 05-08
//        22:40:47.684  - tableElements 2
//        22:40:47.684  - trElement 名称 价格范围 均价 涨跌 单位 日期
//        22:40:47.685  - trElement 铂 仓库自提指导价 184-186 185 -1 元/克 05-08
//        22:40:47.685  - trElement 钯 仓库自提指导价 497-503 500 +16 元/克 05-08
//        22:40:47.685  - trElement 钌 仓库自提指导价 80-81 80.50 0 元/克 05-08
//        22:40:47.685  - trElement 铑 仓库自提指导价 1858-1859 1858.50 -4 元/克 05-08
//        22:40:47.685  - trElement 铱 仓库自提指导价 420-425 422.50 -1 元/克 05-08
//        22:40:47.685  - trElement 银粉 加工厂自提指导价 3940-4040 3990 +90 元/公斤 05-08
    }

    @Override
    public Site getSite() {
        return site;
    }

    //@PostConstruct
    public void init() {
        Spider.create(new HqSmmService()).addUrl(ROOT_URL + "/gjs")
                .addPipeline(hqSmmPipeLine)
                .run();
    }

    public static void main(String[] args) {
        Spider.create(new HqSmmService()).addUrl(ROOT_URL + "/gjs")
                .run();
    }
}
