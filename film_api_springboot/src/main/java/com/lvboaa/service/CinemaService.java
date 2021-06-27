package com.lvboaa.service;

import com.lvboaa.pojo.Cinema;
import com.lvboaa.pojo.Movie;
import com.lvboaa.util.Result;
import com.lvboaa.vo.ScheduleVo;

import java.util.HashMap;
import java.util.List;

public interface CinemaService {
    /**
     * 影院列表分页
     * @param hashMap
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 新增影院
     * @param hashMap
     * @return
     */
    int insertCinema(HashMap hashMap);

    /**
     * 更新影院
     * @param hashMap
     * @return
     */
    int updateCinema(HashMap hashMap);

    /**
     * 删除影院
     * @param cinemaId
     * @return
     */
    int deleteCinema(Integer cinemaId);

    /**
     * 删除多个影院
     * @param ids
     * @return
     */
    int deleteCinemas(String ids);
    /**
     * 通过id得到影院信息
     * @param cinemaId
     * @return
     */
    Cinema getCinemaById(Integer cinemaId);

    /**
     * 用户得到影院列表
     * @param hashMap
     * @return
     */
    List<Cinema> getAllCinemaByUser(HashMap hashMap);

    /**
     * 根据电影院id得到该电影院放映的所有电影
     * @param cinemaId
     * @return
     */
    List<Movie> getAllMovieByCinemaId(Integer cinemaId);

    /**
     * 根据电影院id和电影id得到排片信息
     * @param cinemaId
     * @param movieId
     * @return
     */
    List<ScheduleVo> getScheduleByMovieId(Integer cinemaId, Integer movieId);

    /**
     * 根据影院名得到影院信息
     * @param hashMap
     * @return
     */
    List<Cinema> getCinemaByName(HashMap hashMap);
}
