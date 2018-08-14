package com.demo.pipeline;

import com.demo.domain.Drama;
import com.demo.domain.Testvedio;
import com.demo.mapper.DramaMapper;
import com.demo.mapper.TestvedioMapper;
import com.demo.modal.constant.ResultWrapper;
import com.demo.modal.constant.SystemEnum;
import com.demo.modal.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Service//@Service
@Slf4j
public class VedioPipeline implements Pipeline {


    @Autowired
    private TestvedioMapper vedioMapper;


    @Autowired
    private DramaMapper dramaMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {

            Testvedio result = (Testvedio) resultItems.get("vedio");

            Testvedio record = new Testvedio();
            record.setPicture(result.getPicture());



            Testvedio search = vedioMapper.selectOne(record);
            log.info(search+"");


            if (result == null)
                return;
            List<Drama> dramas = (List<Drama>) resultItems.get("drama");

            System.out.println(result);
            if (search == null) {
                log.info("新vedio");
                vedioMapper.insert(result);
            } else {
                log.info("已存在vedio");
                result = search;
                search.setEpisode(result.getEpisode());
                search.setNowepisode(result.getNowepisode());
                vedioMapper.updateByPrimaryKey(search);

            }
            for (Drama drama : dramas) {
                drama.setVedioid(result.getVedioid());

                Drama drecord = new Drama();
                drecord.setVediolink(drama.getVediolink());


                log.info(drama.getVediolink());

                if (dramaMapper.selectOne(drecord) != null) {
                    log.info("当前drama已存在");

                    log.info(dramaMapper.selectOne(drecord)+" ");

                    continue;
                }
                log.error(result.toString());
                int dramaFlag = dramaMapper.insert(drama);
                if (dramaFlag != 0)
                    System.out.println("插入" + drama + "成功");
            }


        } catch (
                Exception e)

        {
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.setCode(SystemEnum.ERROR.getCode());
            resultWrapper.setMsg(SystemEnum.ERROR.getDetail());
            GlobalException ex = new GlobalException("插入出错", e);
            resultWrapper.setObj(ex);
            log.error(ex.toString());
        }
    }
}