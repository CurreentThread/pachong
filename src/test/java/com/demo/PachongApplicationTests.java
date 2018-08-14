package com.demo;

import com.demo.modal.config.SPDQprperties;
import com.demo.modal.constant.TypeEnum;
import com.demo.processor.VedioProcessor;
import com.demo.pipeline.VedioPipeline;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Spider;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback()
@Transactional
@Slf4j
//@PropertySource("classpath:config/sourcepage.properties")

public class PachongApplicationTests {

    @Autowired
    private VedioPipeline vedioPipeline;


    @Autowired
    private SPDQprperties spdQprperties;


    private String DETAIL_SITE_HEAD = "http://shipindaquan.com/index.php?s=vod-type-id-1-mcid--lz--area-%E5%A4%A7%E9%99%86-year--letter--order-addtime-picm-1-p-1.html";

//springboot单元测试操作数据库默认会回滚

    @Test
    public void contextLoads() {

        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();
//        Spider spider = new Spider();http://www.shipindaquan.com/video/54743.html

        //http://www.shipindaquan.com/index.php?s=vod-type-id-1-mcid--lz--area-%E5%A4%A7%E9%99%86-year--letter--order-addtime-picm-1-p-1.html
        Spider.create(new VedioProcessor(spdQprperties.getMovie(), TypeEnum.MOVIE.getType())).addUrl("http://www.shipindaquan.com/video/10124.html").addPipeline(vedioPipeline).thread(5).run();
//        OOSpider.create(Site.me(),
//               vedioPipeline, Vedio.class)
//                .addUrl("http://www.shipindaquan.com/video/18479.html")
//                .thread(5)
//                .run();
        endTime = System.currentTimeMillis();
        log.info("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了" + "条记录");
//        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了" + count + "条记录");

    }



}
