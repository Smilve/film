package com.lvboaa.mapper;


import com.lvboaa.pojo.MovieInterval;

import java.util.HashMap;
import java.util.List;

public interface MovieIntervalMapper {
    /**
     * 得到所有电影场次
     * @return
     */
    List<MovieInterval> getAllMovieInterval();

    /**
     * 分页电影场次
     * @return
     */
    List<MovieInterval> getAllMovieIntervalByPage(HashMap hashMap);

    /**
     * 根据id得到电影场次
     * @param movieIntervalId
     * @return
     */
    MovieInterval getMovieIntervalById(Integer movieIntervalId);

    /**
     * 电影场次总数
     * @return
     */
    int getMovieIntervalCount(HashMap hashMap);

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
    int deleteMovieIntervals(List list);

}
