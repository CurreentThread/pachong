package com.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }
//
//
//    public static void main(String[] args) {
//        Spider.create(new GithubRepoPageProcessor())
//                //从https://github.com/code4craft开始抓
//                .addUrl("https://github.com/code4craft")
//                //设置Scheduler，使用Redis来管理URL队列
//                .setScheduler(new RedisScheduler("localhost"))
//                //设置Pipeline，将结果以json方式保存到文件
//                .addPipeline(new JsonFilePipeline("\\Users\\anonymous"))
//                //开启5个线程同时执行
//                .thread(5)
//                //启动爬虫
//                .run();
//    }

}
