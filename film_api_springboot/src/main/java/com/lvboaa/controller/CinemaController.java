package com.lvboaa.controller;

import com.lvboaa.pojo.Cinema;
import com.lvboaa.pojo.Movie;
import com.lvboaa.service.CinemaService;
import com.lvboaa.util.Result;
import com.lvboaa.vo.ScheduleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 03月03日 23:53:37
 */
@RestController
@RequestMapping("/cinema")
@CrossOrigin
@Api(tags = "Cinema", description = "电影院")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @PostMapping("/cinemas")
    @ApiOperation("获取影院列表")
    public Result getAllCinema(@RequestBody HashMap<String,Object> hashMap){
        return cinemaService.paging(hashMap);
    }

    @GetMapping("/cinema/{cinemaId}")
    @ApiOperation("根据id得到影院")
    public Result getCinemaById(@PathVariable("cinemaId") Integer cinemaId){

        return Result.success(cinemaService.getCinemaById(cinemaId));
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Result updateCinema(@RequestBody HashMap<String,Object> hashMap){

        if(cinemaService.updateCinema(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @DeleteMapping("/del/{cinemaId}")
    @ApiOperation("删除影院")
    public Result deleteCinema(@PathVariable("cinemaId") Integer cinemaId){
        if(cinemaService.deleteCinema(cinemaId) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/delAll")
    @ApiOperation("删除多个电影院")
    public Result deleteCinemas(@RequestBody String ids){
        if(cinemaService.deleteCinemas(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/insert")
    @ApiOperation("添加用户")
    public Result insertCinema(@RequestBody HashMap<String,Object> hashMap){
        if(cinemaService.insertCinema(hashMap)==0){
            return Result.failure();
        }
        return Result.success();
    }


    @PostMapping("/getCinemaByUser")
    @ApiOperation("用户获取所有影院")
    public Result getAllCinemaByUser(@RequestBody HashMap<String,Object> hashMap){
        List<Cinema> cinemaList = cinemaService.getAllCinemaByUser(hashMap);
        if (cinemaList == null){
            return Result.failure();
        }
        return Result.success(cinemaList);
    }

    @GetMapping("/movieList/{cinemaId}")
    @ApiOperation("用户获取影院上映的所有电影")
    public Result getAllMovieByCinemaId(@PathVariable("cinemaId") Integer cinemaId){
        List<Movie> movieList = cinemaService.getAllMovieByCinemaId(cinemaId);
        if (movieList == null){
            return Result.failure();
        }
        return Result.success(movieList);
    }

    @GetMapping("/schedule/{cinemaId}/{movieId}")
    @ApiOperation("根据影院id和电影id得到排片信息")
    public Result getScheduleByMovieId(@PathVariable("cinemaId") Integer cinemaId, @PathVariable("movieId") Integer movieId){
        List<ScheduleVo> scheduleVoList = cinemaService.getScheduleByMovieId(cinemaId,movieId);
        if (scheduleVoList == null){
            return Result.failure();
        }
        return Result.success(scheduleVoList);
    }

    @PostMapping("/like")
    @ApiOperation("根据影院名得到影院信息")
    public Result getCinemaByName(@RequestBody HashMap hashMap){
        List<Cinema> cinemas = cinemaService.getCinemaByName(hashMap);
        if (cinemas == null){
            return Result.failure();
        }
        return Result.success(cinemas);
    }
}
