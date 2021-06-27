package com.lvboaa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ChartMapper {
    /**
     * 得到前三售票额的电影院id
     * @return
     */
    List<Integer> getTop3CinemaId();

    /**
     * 根据电影院id得到一周之内的售票额
     * @return
     */
    List<Double> getChartByCinemaId(HashMap hashMap);

    /**
     * 得到前三售票额的电影id
     * @return
     */
    List<Integer> getTop3MovieId();

    /**
     * 根据电影id得到一周之内的售票额
     * @return
     */
    List<Double> getChartByMovieId(HashMap hashMap);
}
