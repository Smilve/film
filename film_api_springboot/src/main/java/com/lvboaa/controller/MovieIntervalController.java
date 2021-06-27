package com.lvboaa.controller;

import com.lvboaa.service.MovieIntervalService;
import com.lvboaa.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author lvbo
 * @date 2021年 04月19日 17:15:55
 */
@RestController
@RequestMapping("/movieInterval")
@CrossOrigin
@Api(tags = "MovieInterval", description = "场次")
public class MovieIntervalController {
    
    @Autowired
    MovieIntervalService movieIntervalService;

    @PostMapping("/movieIntervals")
    @ApiOperation("得到所有场次(分页)")
    public Result getAllMovieIntervalByPage(@RequestBody HashMap hashMap){
        return movieIntervalService.paging(hashMap);
    }

    @GetMapping
    @ApiOperation("得到所有场次")
    public Result getAllMovieInterval(){
        return Result.success(movieIntervalService.getAllMovieInterval());
    }

    @GetMapping("/{movieIntervalId}")
    @ApiOperation("根据id得到场次")
    public Result getAllMovieIntervalById(@PathVariable("movieIntervalId") Integer movieIntervalId){
        return Result.success(movieIntervalService.getMovieIntervalById(movieIntervalId));
    }

    @PostMapping
    @ApiOperation("添加场次")
    public Result addMovieInterval(@RequestBody HashMap hashMap){
        if (movieIntervalService.insertIntervalMovie(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PutMapping
    @ApiOperation("更新场次")
    public Result updateMovieInterval(@RequestBody HashMap hashMap){
        if(movieIntervalService.updateMovieInterval(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }


    @DeleteMapping("/{movieIntervalId}")
    @ApiOperation("删除场次")
    public Result deleteMovieInterval(@PathVariable("movieIntervalId") Integer movieIntervalId){
        if(movieIntervalService.deleteMovieInterval(movieIntervalId) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/delAll")
    @ApiOperation("删除多个场次")
    public Result deleteMovieIntervals(@RequestBody String ids){
        if(movieIntervalService.deleteMovieIntervals(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }
}
