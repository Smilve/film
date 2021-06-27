package com.lvboaa.service;

import com.lvboaa.vo.CinemaVo;
import com.lvboaa.vo.MovieVo;

import java.util.HashMap;
import java.util.List;

public interface ChartService {

    /**
     * 得到售票额前三的电影院信息
     * @return
     */
    List<HashMap> getTop3CinemaInfo();

    /**
     * 根据电影院id得到一周之内的售票额
     * @return
     */
    List<HashMap> getChartByCinemaId(HashMap hashMap);

    /**
     * 得到所有影院信息
     * @return
     */
    List<CinemaVo> getCinemaInfo();

    /**
     * 得到售票额前三的电影信息
     * @return
     */
    List<HashMap> getTop3MovieInfo();

    /**
     * 根据电影id得到一周之内的售票额
     * @return
     */
    List<HashMap> getChartByMovieId(HashMap hashMap);

    /**
     * 得到所有电影信息
     * @return
     */
    List<MovieVo> getMovieInfo();
}
