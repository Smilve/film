package com.lvboaa.controller;

import com.lvboaa.service.CommetService;
import com.lvboaa.util.Result;
import com.lvboaa.vo.CommetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月24日 21:52:25
 */
@RestController
@RequestMapping("/commet")
@CrossOrigin
@Api(tags = "Commet", description = "评论")
public class CommetController {
    @Autowired
    public CommetService commetService;

    @PostMapping("/commets")
    @ApiOperation("获取评论列表")
    public Result getAllCommet(@RequestBody HashMap hashMap){
        return commetService.paging(hashMap);
    }

    @DeleteMapping("/del/{commetId}")
    @ApiOperation("删除评论")
    public Result deleteCommet(@PathVariable("commetId") Integer commetId){
        if(commetService.deleteCommet(commetId) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/delAll")
    @ApiOperation("删除多个评论")
    public Result deleteCommets(@RequestBody String ids){
        if(commetService.deleteCommets(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/add")
    @ApiOperation("新增一条评论")
    public Result addCommet(@RequestBody HashMap hashMap){
        if(commetService.addCommet(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @GetMapping("/commetByMovieId/{movieId}")
    @ApiOperation("根据电影id得到电影所有评论")
    public Result getCommetByMovieId(@PathVariable("movieId")Integer movieId){
        List<CommetVo> list = commetService.getCommetByMovieId(movieId);
        if( list == null){
            return Result.failure();
        }
        return Result.success(list);
    }

    @GetMapping("/score/{movieId}")
    @ApiOperation("根据电影id获得电影评分")
    public Result getAvgScoreByMovieId(@PathVariable("movieId")Integer movieId){
        HashMap scoreInfo = commetService.getAvgScoreByMovieId(movieId);
        if(scoreInfo == null || scoreInfo.isEmpty()){
            return Result.failure();
        }
        return Result.success(scoreInfo);
    }
}
