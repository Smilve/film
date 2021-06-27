package com.lvboaa.service.impl;

import com.lvboaa.mapper.CinemaMapper;
import com.lvboaa.pojo.Cinema;
import com.lvboaa.pojo.Movie;
import com.lvboaa.service.CinemaService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import com.lvboaa.vo.ScheduleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 03月03日 23:48:35
 */
@Service
@Slf4j
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaMapper cinemaMapper;
    public Result paging(HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, cinemaMapper, (a, b)-> b.getAllCinema(a), (a, b)->b.getCinemaCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    public int insertCinema(HashMap hashMap) {
        log.debug("新增电影院："+hashMap.toString());
        return cinemaMapper.insertCinema(hashMap);
    }

    public int updateCinema(HashMap hashMap) {
        log.debug("更新电影院："+hashMap.toString());
        return cinemaMapper.updateCinema(hashMap);
    }

    public int deleteCinema(Integer cinemaId) {
        log.debug("删除电影院："+cinemaId);
        return cinemaMapper.deleteCinema(cinemaId);
    }

    public int deleteCinemas(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个电影院:"+list.toString());
        return cinemaMapper.deleteCinemas(list);
    }

    public Cinema getCinemaById(Integer cinemaId) {
        log.debug("根据id得到信息："+cinemaId);
        return cinemaMapper.getCinemaById(cinemaId);
    }

    @Override
    public List<Cinema> getAllCinemaByUser(HashMap hashMap) {
        log.debug("用户得到影院列表："+hashMap.toString());
        return cinemaMapper.getAllCinemaByUser(hashMap);
    }

    @Override
    public List<Movie> getAllMovieByCinemaId(Integer cinemaId) {
        log.debug("得到电影院上映的所有电影："+cinemaId);
        return cinemaMapper.getAllMovieByCinemaId(cinemaId);
    }

    @Override
    public List<ScheduleVo> getScheduleByMovieId(Integer cinemaId, Integer movieId) {
        log.debug("根据电影院id和电影Id得到排片信息："+cinemaId+" "+movieId);
        return cinemaMapper.getScheduleByMovieId(cinemaId,movieId);
    }

    @Override
    public List<Cinema> getCinemaByName(HashMap hashMap) {
        log.debug("根据电影名得到电影信息："+hashMap.toString());
        return cinemaMapper.getCinemaByName(hashMap);
    }
}
