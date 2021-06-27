package com.lvboaa.service;

import com.lvboaa.pojo.MovieInterval;
import com.lvboaa.util.Result;

import java.util.HashMap;
import java.util.List;

public interface MovieIntervalService {
    /**
     * 得到所有电影场次
     * @return
     */
    List<MovieInterval> getAllMovieInterval();

    /**
     * 分页电影场次
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 根据id得到电影场次
     * @param movieIntervalId
     * @return
     */
    MovieInterval getMovieIntervalById(Integer movieIntervalId);



    /**
     * 新增电影场次
     * @param hashMap
     * @return
     */
    int insertIntervalMovie(HashMap hashMap);

    /**
     * 更新电影场次信息
     * @param hashMap
     * @return
     */
    int updateMovieInterval(HashMap hashMap);

    /**
     * 删除电影场次
     * @param movieIntervalId
     * @return
     */
    int deleteMovieInterval(Integer movieIntervalId);

    /**
     * 删除多个电影场次
     * @param list
     * @return
     */
    int deleteMovieIntervals(String ids);
}
