package com.lvboaa.service.impl;

import com.lvboaa.mapper.MovieIntervalMapper;
import com.lvboaa.pojo.MovieInterval;
import com.lvboaa.service.MovieIntervalService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 04月19日 17:09:52
 */

@Service
@Slf4j
public class MovieIntervalServiceImpl implements MovieIntervalService {

    @Autowired
    MovieIntervalMapper movieIntervalMapper;

    @Override
    public List<MovieInterval> getAllMovieInterval() {
        log.debug("得到所有场次信息");
        return movieIntervalMapper.getAllMovieInterval();
    }

    @Override
    public Result paging(HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, movieIntervalMapper, (a,b)-> b.getAllMovieIntervalByPage(a), (a,b)->b.getMovieIntervalCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    @Override
    public MovieInterval getMovieIntervalById(Integer movieIntervalId) {
        log.debug("得到场次信息："+movieIntervalId);
        return movieIntervalMapper.getMovieIntervalById(movieIntervalId);
    }

    @Override
    public int insertIntervalMovie(HashMap hashMap) {
        log.debug("添加场次："+hashMap.toString());
        return movieIntervalMapper.insertIntervalMovie(hashMap);
    }

    @Override
    public int updateMovieInterval(HashMap hashMap) {
        log.debug("更新场次："+hashMap.toString());
        return movieIntervalMapper.updateMovieInterval(hashMap);
    }

    @Override
    public int deleteMovieInterval(Integer movieIntervalId) {
        log.debug("删除场次："+movieIntervalId);
        return movieIntervalMapper.deleteMovieInterval(movieIntervalId);
    }

    @Override
    public int deleteMovieIntervals(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个用户:"+list.toString());
        return movieIntervalMapper.deleteMovieIntervals(list);
    }
}
