package com.demo.processor;

import com.demo.domain.Drama;
import com.demo.domain.Testvedio;
import com.demo.modal.constant.*;
import com.demo.modal.exception.GlobalException;
import com.demo.pipeline.VedioPipeline;
import com.demo.util.PackageVedio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

//@Component
@Slf4j
//@PropertySource("classpath:sourcepage.properties")
//@RestController
public class VedioProcessor implements PageProcessor {
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setCharset("utf-8");

    private static int count = 0;

    private static int total = 0;


    private static final String DETAIL_SITE_MAIN = "http://www.shipindaquan.com";


    private String first_url;

    private String type;


    public VedioProcessor(String first_url, String type) {
        this.first_url = first_url;
        this.type = type;
    }


    private static final ConcurrentHashMap<String, Boolean> retryMap = new ConcurrentHashMap<>();


    private List<Testvedio> vedio = new ArrayList<>();


    private static final AtomicBoolean wasFindPage = new AtomicBoolean(false);

    @Override
    public Site getSite() {
        return site;
    }

    //http://www.shipindaquan.com/index.php?s=vod-type-id-1-mcid--lz--area-%E5%A4%A7%E9%99%86-year--letter--order-addtime-picm-1-p-2.html
    //http://www.shipindaquan.com/index.php?s=vod-type-id-1-p-1-mcid--p-1-lz--p-1-area-%E5%A4%A7%E9%99%86-p-1-year--p-1-letter--p-1-order-addtime-p-1-picm-1-p-1-p-1-p-1.html


    //process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑


    /*
    * 页面元素的抽取

第二部分是爬虫的核心部分：对于下载到的Html页面，你如何从中抽取到你想要的信息？WebMagic里主要使用了三种抽取技术：XPath、正则表达式和CSS选择器。另外，对于JSON格式的内容，可使用JsonPath进行解析。
*/
    @Override
    public void process(Page page) {
        //http://www.shipindaquan.com/video/18479.html
        //获取总页数
        try {

            if (!page.getUrl().regex("http://www.shipindaquan.com/video/" + "[0-9]+" + ".html").match()) {

                log.info("当前第" + (++total) + "个地址 ");

                List<String> links = page.getHtml().xpath("//*[@class='list_module_img']/li/a/@href").all();

                String pag = page.getHtml().xpath("//*[@class='pagination']/span/text()").get();
                int end = pag.lastIndexOf("页");
                int start = pag.lastIndexOf("/");
                int total = Integer.parseInt("" + pag.substring(start + 1, end));


                log.info(pag + "");

                log.info(total + "");

                for (int n = 0; n < links.size(); n++) {
                    links.set(n, DETAIL_SITE_MAIN + links.get(n));
                }
                log.info(links.toString());

                //当还未插入所有页数页面时
                if (wasFindPage.compareAndSet(false, true)) {

                    log.info(wasFindPage.get() + "");

                    List<String> otherParentPage = dynmanicSetUrl(total);
                    log.info(otherParentPage.toString());
                    if (otherParentPage != null && otherParentPage.size() != 0)
                        //   加入满足条件的链接
                        page.addTargetRequests(otherParentPage);
                }

                page.addTargetRequests(links);

            } else {

                Testvedio result = PackageVedio.packageVedio(page, this.type);

                log.info(result.toString());

                List<Drama> drama = PackageVedio.diffTypePackLink(page, this.type, result);

                log.info(drama.toString());

                page.putField("vedio", result);
                page.putField("drama", drama);

                System.out.println("当前是第" + (++count) + "个视频");
//        }
            }
        } catch (Exception e) {

            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.setCode(SystemEnum.ERROR.getCode());
            resultWrapper.setMsg(SystemEnum.ERROR.getDetail());
            GlobalException ex = new GlobalException(page.getUrl().get(), e);
            //将错误页面放入map中retry
            retryMap.put(page.getUrl().get(), false);

            resultWrapper.setObj(ex);
            log.error(ex.toString());
        }
    }

    private List<String> dynmanicSetUrl(int total) {
//        String page = "http://shipindaquan.com/index.php?s=vod-type-id-1-mcid--lz--area-%E5%A4%A7%E9%99%86-year--letter--order-addtime-picm-1-p-1.html";
//        shipingdaquan.movie=http://www.shipindaquan.com/index.php?s=vod-type-id-1-mcid--lz--year--letter--order-addtime-picm-1-p-1.html

        String page = this.first_url;
        log.info(page);
        int dot = page.lastIndexOf('.');


        String front = page.substring(0, dot - 1);
        log.info(front);
        String end = page.substring(dot);

        log.info(end);

        List<String> pages = new ArrayList<>();
        int t = total;
        if (total <= 1)
            return null;
        else {
            while (t > 1) {

                String eachpage = front + t + end;
                log.info(eachpage);
                pages.add(eachpage);
                t--;
            }
        }


        return pages;


    }


}