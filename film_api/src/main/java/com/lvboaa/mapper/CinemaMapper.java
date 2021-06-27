package com.lvboaa.mapper;

import com.lvboaa.pojo.Movie;
import com.lvboaa.vo.CinemaVo;
import com.lvboaa.pojo.Cinema;
import com.lvboaa.vo.ScheduleVo;
import javassist.bytecode.LineNumberAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;


public interface CinemaMapper {

    /**
     * 获取所有影院
     * @return
     */
    List<Cinema> getAllCinema(HashMap hashMap);

    /**
     * 影院总数
     * @return
     */
    int getCinemaCount(HashMap hashMap);

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
     * @param list
     * @return
     */
    int deleteCinemas(List list);
    /**
     * 通过id得到影院信息
     * @param cinemaId
     * @return
     */
    Cinema getCinemaById(Integer cinemaId);

    /**
     * 得到影院id和影院名
     * @return
     */
    List<CinemaVo> getAllCinemaVo();

    /**
     * 用户得到影院列表
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
    List<ScheduleVo> getScheduleByMovieId(@Param("cinemaId") Integer cinemaId,@Param("movieId") Integer movieId);

    /**
     * 根据影院名得到影院信息
     * @param hashMap
     * @return
     */
    List<Cinema> getCinemaByName(HashMap hashMap);
}
