package com.lvboaa.service.impl;

import com.lvboaa.mapper.ChartMapper;
import com.lvboaa.mapper.CinemaMapper;
import com.lvboaa.mapper.MovieMapper;
import com.lvboaa.service.ChartService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.vo.CinemaVo;
import com.lvboaa.vo.MovieVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 05月11日 11:12:57
 */
@Service
@Slf4j
public class ChartServiceImpl implements ChartService {

    @Autowired
    ChartMapper chartMapper;

    @Autowired
    CinemaMapper cinemaMapper;

    @Autowired
    MovieMapper movieMapper;

    @Override
    public List<HashMap> getTop3CinemaInfo() {
        List<Integer> ids = chartMapper.getTop3CinemaId();
        log.debug("获取前三电影院信息："+ids);
        List<HashMap> list=new ArrayList<>();
        for (int id:ids){
            HashMap hashMap=new HashMap();
            hashMap.put("date", CommonUtils.getNowMinusDayTime(6));
            hashMap.put("cinemaId",id);
            HashMap hashMap1=new HashMap();
            hashMap1.put("cinemaName",cinemaMapper.getCinemaById(id).getCinemaName());
            hashMap1.put("data",chartMapper.getChartByCinemaId(hashMap));
            list.add(hashMap1);
        }
        return list;
    }

    @Override
    public List<HashMap> getChartByCinemaId(HashMap hashMap) {
        List<HashMap> list=new ArrayList<>();
        HashMap hashMap1=new HashMap();
        hashMap1.put("cinemaName",cinemaMapper.getCinemaById((int)hashMap.get("cinemaId")).getCinemaName());
        hashMap.put("date", CommonUtils.getNowMinusDayTime(6));
        hashMap1.put("data",chartMapper.getChartByCinemaId(hashMap));
        list.add(hashMap1);
        return list;
    }

    @Override
    public List<CinemaVo> getCinemaInfo() {
        log.debug("得到所有影院信息");
        return cinemaMapper.getAllCinemaVo();
    }

    @Override
    public List<HashMap> getTop3MovieInfo() {
        List<Integer> ids = chartMapper.getTop3MovieId();
        log.debug("获取前三电影信息："+ids);
        List<HashMap> list=new ArrayList<>();
        for (int id:ids){
            HashMap hashMap=new HashMap();
            hashMap.put("date", CommonUtils.getNowMinusDayTime(6));
            hashMap.put("movieId",id);
            HashMap hashMap1=new HashMap();
            hashMap1.put("movieName",movieMapper.getMovieById(id).getMovieName());
            hashMap1.put("data",chartMapper.getChartByMovieId(hashMap));
            list.add(hashMap1);
        }
        return list;
    }

    @Override
    public List<HashMap> getChartByMovieId(HashMap hashMap) {
        List<HashMap> list=new ArrayList<>();
        HashMap hashMap1=new HashMap();
        hashMap1.put("movieName",movieMapper.getMovieById((int)hashMap.get("movieId")).getMovieName());
        hashMap.put("date", CommonUtils.getNowMinusDayTime(6));
        hashMap1.put("data",chartMapper.getChartByMovieId(hashMap));
        list.add(hashMap1);
        return list;
    }

    @Override
    public List<MovieVo> getMovieInfo() {
        log.debug("得到所有影院信息");
        return movieMapper.getAllMovieVo();
    }
}
