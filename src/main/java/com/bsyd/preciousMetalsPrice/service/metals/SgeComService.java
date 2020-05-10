package com.bsyd.preciousMetalsPrice.service.metals;

import com.bsyd.preciousMetalsPrice.model.MetalPriceShanghaiSge;
import com.bsyd.preciousMetalsPrice.service.metals.pipeLine.SgePipeLine;
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
public class SgeComService implements PageProcessor {

    @Autowired
    private SgePipeLine dataPipeLine;

    private Site site = Site
            .me()
            .setDomain("www.sge.com.cn")
            .setSleepTime(500)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        Document document = Jsoup.parse(page.getHtml().get());
        Elements trElements = document.getElementsByClass("ininfo");
        List<MetalPriceShanghaiSge> list = new ArrayList<>();
        for (Element trElement: trElements) {
            if (trElement == null || trElement.children().size() != 5) {
                continue;
            }

            MetalPriceShanghaiSge metalPriceShanghaiSge = new MetalPriceShanghaiSge();
            for (int i = 0; i < trElement.children().size(); i++) {
                Element tdElement = trElement.children().get(i);
                metalPriceShanghaiSge.setDate(new Date());
                if (i == 0) {
                    metalPriceShanghaiSge.setName(tdElement.text());
                }

                if (i == 1) {
                    metalPriceShanghaiSge.setOpeningQuotationPrice(DigitalUtil.getLongAmount(tdElement.text()));
                }

                if (i == 2) {
                    metalPriceShanghaiSge.setClosingQuotationPrice(DigitalUtil.getLongAmount(tdElement.text()));
                }

                if (i == 3) {
                    metalPriceShanghaiSge.setMaxQuotationPrice(DigitalUtil.getLongAmount(tdElement.text()));
                }

                if (i == 4) {
                    metalPriceShanghaiSge.setMinQuotationPrice(DigitalUtil.getLongAmount(tdElement.text()));
                }
            }

            list.add(metalPriceShanghaiSge);

        }

        page.putField("list", list);
//        <tr class="ininfo">
//      <td width="85" height="40" class="insid">NYAuTN12</td>
//      <td width="85" class="colorGreen">390.45</td>
//      <td width="85" class="colorGreen">-</td>
//      <td width="85" class="colorGreen">392.1</td>
//      <td width="85" class="colorGreen">388.05</td>
//     </tr>
    }

    @Override
    public Site getSite() {
        return site;
    }

    //@PostConstruct
    public void init(){
        Spider.create(new SgeComService()).addUrl("https://www.sge.com.cn/hqsj")
                .addPipeline(dataPipeLine)
                .run();
    }

}
