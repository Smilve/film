package com.lvboaa.controller;

import com.lvboaa.service.ChartService;
import com.lvboaa.util.Result;
import com.lvboaa.vo.CinemaVo;
import com.lvboaa.vo.MovieVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 05月11日 11:05:03
 */
@RestController
@RequestMapping("/chart")
@CrossOrigin
@Api(tags = "Chart", description = "图标")
public class ChartController {

    @Autowired
    ChartService chartService;

    @GetMapping("/topCinema")
    @ApiOperation("获取前三电影院票房")
    public Result getTop3Cinema(){
        List<HashMap> list = chartService.getTop3CinemaInfo();
        if (list == null){
            return Result.failure();
        }
        return Result.success(list);
    }

    @GetMapping("/cinema/{cinemaId}")
    @ApiOperation("获取指定电影院票房")
    public Result getCinema(@PathVariable("cinemaId")Integer cinemaId){
        HashMap hashMap=new HashMap();
        hashMap.put("cinemaId",cinemaId);
        List<HashMap> list = chartService.getChartByCinemaId(hashMap);
        if (list == null){
            return Result.failure();
        }
        return Result.success(list);
    }

    @GetMapping("/cinemaInfo")
    @ApiOperation("得到所有影院信息")
    public Result getCinemaInfo(){
        List<CinemaVo> info = chartService.getCinemaInfo();
        if(info == null){
            return Result.failure();
        }
        return Result.success(info);
    }

    @GetMapping("/topMovie")
    @ApiOperation("获取前三电影票房")
    public Result getTop3Movie(){
        List<HashMap> list = chartService.getTop3MovieInfo();
        if (list == null){
            return Result.failure();
        }
        return Result.success(list);
    }

    @GetMapping("/movie/{movieId}")
    @ApiOperation("获取指定电影票房")
    public Result getMovie(@PathVariable("movieId")Integer movieId){
        HashMap hashMap=new HashMap();
        hashMap.put("movieId",movieId);
        List<HashMap> list = chartService.getChartByMovieId(hashMap);
        if (list == null){
            return Result.failure();
        }
        return Result.success(list);
    }

    @GetMapping("/movieInfo")
    @ApiOperation("得到所有电影信息")
    public Result getMovieInfo(){
        List<MovieVo> info = chartService.getMovieInfo();
        if(info == null){
            return Result.failure();
        }
        return Result.success(info);
    }
}
