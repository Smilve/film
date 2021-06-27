package com.lvboaa.controller;

import com.lvboaa.pojo.Movie;
import com.lvboaa.service.MovieService;
import com.lvboaa.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 22:05:19
 */
@RestController
@RequestMapping("/movie")
@CrossOrigin
@Api(tags = "movie", description = "电影")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/movies")
    @ApiOperation("获取电影列表")
    public Result getAllMovie(@RequestBody HashMap<String,Object> hashMap){
        return movieService.paging(hashMap);
    }

    @GetMapping("/movie/{movieId}")
    @ApiOperation("根据id获得电影")
    public Result getMovieById(@PathVariable("movieId")Integer movieId){
        return Result.success(movieService.getMovieById(movieId));
    }


    @PostMapping("/insert")
    @ApiOperation("添加电影")
    public Result insertMovie(@RequestBody HashMap<String,Object> hashMap){
        if(movieService.insertMovie(hashMap)==0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新电影")
    public Result updateMovie(@RequestBody HashMap<String,Object> hashMap){

        if(movieService.updateMovie(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @DeleteMapping("/del/{movieId}")
    @ApiOperation("删除电影")
    public Result deleteMovie(@PathVariable("movieId") Integer movieId){
        if(movieService.deleteMovie(movieId) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/delAll")
    @ApiOperation("删除多个电影")
    public Result deleteMovies(@RequestBody String ids){
        if(movieService.deleteMovies(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/photo")
    @ApiOperation("上传电影海报")
    public Result updatePhoto(HttpServletRequest request, MultipartFile file) {
        if(movieService.updatePhoto(request,file) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/ingMovie")
    @ApiOperation("获取热映电影列表")
    public Result getIngMovieList(@RequestBody HashMap<String,Object> hashMap){
        return movieService.getIngMovieList(hashMap);
    }

    @PostMapping("/willMovie")
    @ApiOperation("获取即将上映电影列表")
    public Result getWillMovieList(@RequestBody HashMap<String,Object> hashMap){
        return movieService.getWillMovieList(hashMap);
    }

    @PostMapping("/classicMovie")
    @ApiOperation("获取经典电影列表")
    public Result getClassicMovieList(@RequestBody HashMap<String,Object> hashMap){
        return movieService.getClassicMovieList(hashMap);
    }

    @PostMapping("/topMovie")
    @ApiOperation("获取榜单电影")
    public Result getTopMovieList(@RequestBody HashMap<String,Object> hashMap){
        return movieService.getTopMovieList(hashMap);
    }

    @PostMapping("/like")
    @ApiOperation("模糊查询电影")
    public Result getMovieByName(@RequestBody HashMap<String,Object> hashMap){
        List<Movie> movies = movieService.getMovieByName(hashMap);
        if(movies == null){
            return Result.failure();
        }
        return Result.success(movies);
    }

    @GetMapping("/recommend/{movieId}")
    @ApiOperation("推荐电影")
    public Result getRecommendMovie(@PathVariable("movieId") Integer movieId){
        List<Movie> movies = movieService.getRecommendMovie(movieId);
        return Result.success(movies);
    }
}
