package com.lvboaa.service.impl;

import com.lvboaa.mapper.MovieMapper;
import com.lvboaa.pojo.Movie;
import com.lvboaa.service.MovieService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.util.MovieRecommend;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 22:03:49
 */
@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRecommend movieRecommend;

    public Result paging(HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, movieMapper, (a,b)-> b.getAllMovie(a), (a,b)->b.getMovieCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    public Movie getMovieById(Integer movieId) {
        return movieMapper.getMovieById(movieId);
    }

    public int insertMovie(HashMap hashMap) {
        log.debug("添加电影信息："+hashMap.toString());
        return movieMapper.insertMovie(hashMap);
    }

    public int updateMovie(HashMap hashMap) {
        log.debug("更新电影："+hashMap.toString());
        return movieMapper.updateMovie(hashMap);
    }

    public int deleteMovie(Integer movieId) {
        log.debug("删除用户："+movieId);
        return movieMapper.deleteMovie(movieId);
    }

    public int deleteMovies(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个电影:"+list.toString());
        return movieMapper.deleteMovies(list);

    }

    public int updatePhoto(HttpServletRequest request, MultipartFile file) {
        //D:\JavaStudy\SSM\film_api\out\artifacts\film_api_war_exploded
        //项目根目录
        File rootPath = new File(request.getServletContext().getRealPath("/"));
        File uploadFile = new File(rootPath.getPath()+"/images/movie/");
        String fileName = file.getOriginalFilename();
        log.debug("上传电影海报："+fileName);
        File dir = new File(uploadFile,fileName);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        try {
            file.transferTo(dir);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public Result getIngMovieList(HashMap hashMap) {
        log.debug("查找热映电影参数："+hashMap.toString());
        Result result = PageUtils.page(hashMap, movieMapper, (a,b)-> b.getAllIngMovie(a), (a,b)->b.getAllIngMovieCount(a));
        log.debug("热映电影："+result.getData().toString());
        return result;
    }

    @Override
    public Result getWillMovieList(HashMap hashMap) {
        log.debug("查找即将上映电影参数："+hashMap.toString());
        Result result = PageUtils.page(hashMap, movieMapper, (a,b)-> b.getAllWillMovie(a), (a,b)->b.getAllWillMovieCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    @Override
    public Result getClassicMovieList(HashMap hashMap) {
        log.debug("查找经典电影参数："+hashMap.toString());
        if(hashMap.get("movieAge") != null){
            String movieAge = hashMap.get("movieAge").toString();
            if(movieAge.equals("2000-2010")){
                hashMap.put("startDate","2000");
                hashMap.put("endDate","2010");
            }else if (movieAge.equals("90年代")){
                hashMap.put("startDate","1990");
                hashMap.put("endDate","1999");
            }else if (movieAge.equals("80年代")){
                hashMap.put("startDate","1980");
                hashMap.put("endDate","1989");
            }else if (movieAge.equals("70年代")){
                hashMap.put("startDate","1970");
                hashMap.put("endDate","1979");
            }else if (movieAge.equals("更早")){
                hashMap.put("startDate","1900");
                hashMap.put("endDate","1969");
            }
        }
        Result result = PageUtils.page(hashMap, movieMapper, (a,b)-> b.getClassicMovie(a), (a,b)->b.getClassicMovieCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    @Override
    public Result getTopMovieList(HashMap hashMap) {
        log.debug("查找榜单电影参数："+hashMap.toString());
        Result result = PageUtils.page(hashMap, movieMapper, (a,b)-> b.getTopMovie(a), (a,b)->10);
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    @Override
    public List<Movie> getMovieByName(HashMap hashMap) {
        log.debug("根据电影名模糊查询电影："+hashMap,toString());
        return movieMapper.getMovieByName(hashMap);
    }

    @Override
    public List<Movie> getRecommendMovie(Integer userId) {
        List<Integer> list = movieRecommend.getMovieById(userId, 3);
        if (list == null){
            return null;
        }
        return movieMapper.getMovieByList(list);
    }

}
