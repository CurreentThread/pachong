package com.demo.controller;

import com.demo.modal.config.SPDQprperties;
import com.demo.modal.constant.ResultWrapper;
import com.demo.modal.constant.TypeEnum;
import com.demo.pipeline.VedioPipeline;

import com.demo.processor.VedioProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/vediospider")
@Slf4j
public class VedioSpiderController {


    @Autowired
    private VedioPipeline vedioPipeline;


    @Autowired
    private SPDQprperties spdQprperties;

    @RequestMapping("/spide")
    public ResultWrapper spide() {


        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setObj(spiderMovie());
//        resultWrapper.setObj(spiderMovie() + "/" + sprideSeries() + "/" + sprideProgram() + "/" + sprideComic());
        return resultWrapper;

    }


    //    @RequestMapping("/sprideMovie")
    public String spiderMovie() {

//                log.info(spdQprperties.getMovie_dalu());

        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();

//        Spider.create(new VedioProcessor()).addUrl("http://www.shipindaquan.com/video/4034.html").addPipeline(vedioPipeline).thread(5).run();


        String[] eachtype = spdQprperties.getMovie().split(",");

        log.info(eachtype.toString());

        //对每种类型启用五个线程爬取
        for (int n = 0; n < eachtype.length; n++) {
            //电影
            Spider.create(new VedioProcessor(spdQprperties.getMovie(), TypeEnum.MOVIE.getType())).addUrl(eachtype).addPipeline(vedioPipeline).thread(10).start();
        }


        endTime = System.currentTimeMillis();

        return "spider movie finished,use" + (endTime - startTime) / 3600 + "s";


    }

    //    @RequestMapping("/sprideSeries")
    public String sprideSeries() {

//                log.info(spdQprperties.getMovie_dalu());

        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();

//        Spider.create(new VedioProcessor()).addUrl("http://www.shipindaquan.com/video/4034.html").addPipeline(vedioPipeline).thread(5).run();


        String[] eachtype = spdQprperties.getSeries().split(",");

        log.info(eachtype.toString());

        //对每种类型启用五个线程爬取
        for (int n = 0; n < eachtype.length; n++) {
            //电影
            Spider.create(new VedioProcessor(spdQprperties.getSeries(), TypeEnum.SERIES.getType())).addUrl(eachtype).addPipeline(vedioPipeline).thread(10).start();
        }


        endTime = System.currentTimeMillis();

        return "spider series finished,use" + (endTime - startTime) / 3600 + "s";


    }

    //    @RequestMapping("/sprideComic")
    public String sprideComic() {

//                log.info(spdQprperties.getMovie_dalu());

        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();

//        Spider.create(new VedioProcessor()).addUrl("http://www.shipindaquan.com/video/4034.html").addPipeline(vedioPipeline).thread(5).run();


        String[] eachtype = spdQprperties.getComdy().split(",");

        log.info(eachtype.toString());

        //对每种类型启用五个线程爬取
        for (int n = 0; n < eachtype.length; n++) {
            //电影
            Spider.create(new VedioProcessor(spdQprperties.getComdy(), TypeEnum.COMIC.getType())).addUrl(eachtype).addPipeline(vedioPipeline).thread(5).start();
        }


        endTime = System.currentTimeMillis();

        return "spider comic finished,use" + (endTime - startTime) / 3600 + "s";


    }

    //    @RequestMapping("/sprideProgram")
    public String sprideProgram() {

//                log.info(spdQprperties.getMovie_dalu());

        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();

//        Spider.create(new VedioProcessor()).addUrl("http://www.shipindaquan.com/video/4034.html").addPipeline(vedioPipeline).thread(5).run();


        String[] eachtype = spdQprperties.getProgram().split(",");

        log.info(eachtype.toString());

        //对每种类型启用五个线程爬取
        for (int n = 0; n < eachtype.length; n++) {
            Spider.create(new VedioProcessor(spdQprperties.getProgram(), TypeEnum.PROGRAM.getType())).addUrl(eachtype).addPipeline(vedioPipeline).thread(5).start();
        }


        endTime = System.currentTimeMillis();

        return "spider program finished,use" + (endTime - startTime) / 3600 + "s";


    }

}
