package com.bwie.hm0422.task;

import com.bwie.hm0422.dao.TClickMapper;
import com.bwie.hm0422.model.TClick;
import com.bwie.hm0422.model.TClickExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <FileName> BookTask
 * <Author>   lqz
 * <Time>  2020/4/25 15:35
 * <Desc> 定时任务类
 **/
@Component
public class BookTask {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TClickMapper tClickMapper;

    @Scheduled(cron ="0 0/1 * * * ? ")
    public void updateTraffic(){
        System.out.println("进行同步");
        //获取所有点击量数据
        List<TClick> tClicks = tClickMapper.selectByExample(new TClickExample());
        //判断非空
        if(null!=tClicks&&tClicks.size()>0){
            //遍历循环
            for (TClick tClick : tClicks) {
                //判断缓存中是否有对应键
                if(redisTemplate.hasKey(tClick.getbId())){
                    //有则同步到数据库
                    TClick t = new TClick();
                    t.setbId(tClick.getbId());
                    Integer num = (Integer) redisTemplate.opsForValue().get(tClick.getbId());
                    t.setTraffic(new Short(String.valueOf(num)));
                    tClickMapper.updateByBookId(t);
                }
            }
        }
    }

}
