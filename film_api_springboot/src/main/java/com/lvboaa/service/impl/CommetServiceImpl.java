package com.lvboaa.service.impl;

import com.lvboaa.mapper.CommetMapper;
import com.lvboaa.mapper.MovieMapper;
import com.lvboaa.mapper.OrderMapper;
import com.lvboaa.pojo.Commet;
import com.lvboaa.service.CommetService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import com.lvboaa.vo.CommetVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月24日 21:46:52
 */

@Service
@Slf4j
public class CommetServiceImpl implements CommetService {

    @Autowired
    CommetMapper commetMapper;

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    OrderMapper orderMapper;

    @Transactional
    public Result paging(HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, commetMapper, (a, b)-> b.getAllCommet(a), (a, b)->b.getCommetCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    public int deleteCommet(Integer commetId) {
        log.debug("删除评论："+commetId);
        return commetMapper.deleteCommet(commetId);
    }

    public int deleteCommets(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个用户:"+list.toString());
        return commetMapper.deleteCommets(list);
    }

    @Override
    public int addCommet(HashMap hashMap) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hashMap.put("date",simpleDateFormat.format(new Date()));
        Commet commet = commetMapper.getCommetByUserAndMovieId(hashMap);
        int n = 0;
        if (commet == null){
            log.debug("新增评论:"+hashMap.toString());
            n = commetMapper.addCommet(hashMap);
        }else{
            log.debug("更新评论:"+hashMap.toString());
            n = commetMapper.updateCommet(hashMap);
        }
        return n;
    }

    @Override
    public List<CommetVo> getCommetByMovieId(Integer movieId) {
        log.debug("得到电影的所有评论:"+movieId);
        return commetMapper.getCommetByMovieId(movieId);
    }

    @Override
    public HashMap getAvgScoreByMovieId(Integer movieId) {
        log.debug("查找电影平均评分："+movieId);
        HashMap hashMap = commetMapper.getAvgScoreByMovieId(movieId);
        hashMap.put("price",orderMapper.getAllPriceByMovieId(movieId));
        HashMap map = new HashMap<>();
        map.put("movieId",movieId);
        map.put("score",hashMap.get("score"));
        if (map.get("score") == null){
            map.put("score",0);
            hashMap.put("score",0);
        }
        log.info("更新电影评分:"+map.toString());
        movieMapper.updateScore(map);
        return hashMap;
    }
}
