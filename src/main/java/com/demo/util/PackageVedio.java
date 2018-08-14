package com.demo.util;

import com.demo.common.CommonUtil;
import com.demo.domain.Drama;
import com.demo.domain.Testvedio;
import com.demo.modal.constant.RegionEnum;
import com.demo.modal.constant.StatusEnum;
import com.demo.modal.constant.TypeEnum;
import com.demo.modal.constant.YearEnum;
import jdk.net.SocketFlow;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class PackageVedio {
    private static final String DETAIL_SITE_MAIN = "http://www.shipindaquan.com";

    private static final String DEFAULT_FROM_WHERE = "视频大全";

    private static final Integer SINGLE_MOVUE_EMPISODE = 1;

    private static final Integer DEFAULT_SUBSCOUNT = 0;

    private static final String NOFOUND = "暂未上映";

    private static final String LIANZAI = "连载";


    private static final String DEFAULT_PICTURE_PATH = "/Users/anonymous/frontProject/vedioshareweb/src/assets/imgs/home1.jpg";

    private static Drama packageSingleDrama(Testvedio testvedio) {
        Drama drama = new Drama();
        drama.setFromwhere(DEFAULT_FROM_WHERE);
        drama.setIswatched(0);
        drama.setSubvedioname(testvedio.getVedioname());
        drama.setUpdtime(testvedio.getUpdtime());


        return drama;


    }

    public static List<Drama> diffTypePackLink(Page page, String type, Testvedio testvedio) {
        List<Drama> dramas = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        List<String> epis = new ArrayList<>();

        if (StringUtils.pathEquals(type, TypeEnum.MOVIE.getType())) {
            urls.add(page.getHtml().xpath("//*[@class='player_list']/a/@href").get());
        } else {
            urls = page.getHtml().xpath("//*[@class='vod-play']/div[2]/ul/a/@href").all();
//            epis = page.getHtml().xpath("//*[@class='tab-a play-box w745']/ul/a/text()").all();

        }
        if (!CommonUtil.isEmptyArray(urls)) {
            for (int n = urls.size(); n > 0; n--) {

                Drama drama = packageSingleDrama(testvedio);
                drama.setVediolink(DETAIL_SITE_MAIN + urls.get(n - 1));
                drama.setEpisodenum(urls.size() - n + 1);
                dramas.add(drama);
            }
        }

        if (testvedio.getIsover() == StatusEnum.OVER.getStatus())
            testvedio.setEpisode(dramas.size());

        testvedio.setNowepisode(dramas.size());


        log.info(testvedio + "");

        return dramas;
    }

    //组装vedioname
    public static Testvedio packageVedio(Page page, String cate) {


        Testvedio vedio = new Testvedio();


        String vedioname = page.getHtml().xpath("//*[@class='vod_neirong_l']/div[4]/h1/text()").get();

        String actor = page.getHtml().xpath("//*[@class='v-zy clear']/storng/a/text()").all().toString();

        String catagory = cate;

        String director = page.getHtml().xpath("//*[@class='vod-n-l w630']/p[4]/storng/a/text()").get();

        String time = page.getHtml().xpath("//*[@class='vw38 fn-left']/text()").get();

        if (time.contains(YearEnum._2018.getYear())) {
            vedio.setIsnew(1);
        } else
            vedio.setIsnew(0);


        String region = page.getHtml().xpath("//*[@class='vw20 fn-left']/a/text()").get();

        if (StringUtils.pathEquals(region, "内地") || StringUtils.pathEquals(region, "大陆"))
            region = RegionEnum.CHINA_MAINLAND.getRegion();

        String summary = page.getHtml().xpath("//*[@class='vod-jianjie']/p[2]/text()").get();


        String picture = page.getHtml().xpath("//*[@class='vod-n-img']/img/@data-original").get();

        if (!picture.contains("http://"))
            picture = DETAIL_SITE_MAIN + picture;


        String type = page.getHtml().xpath("//*[@class='vw60 fn-left']/a/text()").all().toString();


        String updtime = page.getHtml().xpath("//*[@class='vod-n-l w630']/p[9]/text()").get();

        if (StringUtils.isEmpty(updtime))
            updtime = page.getHtml().xpath("//*[@class='vod-n-l w630']/p[10]/text()").get();


        vedio = packEpisode(page, vedio, type);

        log.info(vedio + "");

        //一定要指定id?
//        vedio.setVedioid();
        vedio.setVedioname(vedioname);
        vedio.setActor(actor);
        vedio.setCatagory(catagory);
        vedio.setDirector(director);
        vedio.setTime(time);
        vedio.setRegion(region);
        vedio.setSummary(summary);
        vedio.setPicture(picture);
        vedio.setType(type);
        vedio.setLatestupd(vedio.getUpdtime());
        vedio.setUpdtime(updtime);
        vedio.setLatestupd(updtime);
        vedio.setSubcribecount(DEFAULT_SUBSCOUNT);

        return vedio;


    }

    private static Testvedio packEpisode(Page page, Testvedio vedio, String type) {
        Integer episode = SINGLE_MOVUE_EMPISODE;
        Integer episodenow = SINGLE_MOVUE_EMPISODE;


        String isover = page.getHtml().xpath("//*[@class='v-zy clear']/text()").get();
        log.info(isover);


        String get = page.getHtml().xpath("//*[@class='redd']/text()").get();
        String nowget = page.getHtml().xpath("//*[@class='sDes']/text()").get();

        log.info(get);

        log.info(nowget);
        if (!StringUtils.pathEquals(type, TypeEnum.MOVIE.getType())) {
            if (!StringUtils.isEmpty(get) && !StringUtils.isEmpty(nowget)) {

                nowget = Pattern.compile("[^0-9]").matcher(nowget).replaceAll("");

                try {
                    episodenow = Integer.parseInt(get);
                    episode = Integer.parseInt(nowget);

                    if (episode > episodenow)
                        vedio.setIsover(StatusEnum.CONTINUE.getStatus());
                } catch (Exception e) {
                    e.getMessage();
                }

                vedio.setNowepisode(episodenow);
                vedio.setEpisode(episode > episodenow ? episode : null);
                return vedio;

            }
        }
        if (isover.contains(NOFOUND))
            vedio.setIsover(StatusEnum.NO_OPEN.getStatus());
        else if (isover.contains(LIANZAI))
            vedio.setIsover(StatusEnum.CONTINUE.getStatus());
        else
            vedio.setIsover(StatusEnum.OVER.getStatus());

        vedio.setEpisode(episode);
        vedio.setNowepisode(episodenow);

        log.info("dd" + vedio);

        return vedio;

    }
}
