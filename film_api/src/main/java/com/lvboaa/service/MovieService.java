package com.lvboaa.service;

import com.lvboaa.pojo.Movie;
import com.lvboaa.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface MovieService {

    /**
     * 电影列表分页
     * @param hashMap
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 根据id得到电影
     * @param movieId
     * @return
     */
    Movie getMovieById(Integer movieId);
    /**
     * 添加电影
     * @param hashMap
     * @return
     */
    int insertMovie(HashMap hashMap);

    /**
     * 更新电影
     * @param hashMap
     * @return
     */
    int updateMovie(HashMap hashMap);

    /**
     * 删除电影
     * @param movieId
     * @return
     */
    int deleteMovie(Integer movieId);

    /**
     * 删除多个电影
     * @param ids
     * @return
     */
    int deleteMovies(String ids);

    /**
     * 上传电影海报
     * @param request
     * @param file
     * @return
     */
    int updatePhoto(HttpServletRequest request, MultipartFile file);

    /**
     * 得到热映电影
     * @param hashMap
     * @return
     */
    Result getIngMovieList(HashMap hashMap);

    /**
     * 得到即将上映的电影
     * @param hashMap
     * @return
     */
    Result getWillMovieList(HashMap hashMap);

    /**
     * 得到经典电影
     * @param hashMap
     * @return
     */
    Result getClassicMovieList(HashMap hashMap);

    /**
     * 得到榜单电影
     * @param hashMap
     * @return
     */
    Result getTopMovieList(HashMap hashMap);

    /**
     * 根据电影名模糊查询电影
     * @param hashMap
     * @return
     */
    List<Movie> getMovieByName(HashMap hashMap);

    /**
     * 根据用户id得到推荐电影
     * @param userId
     * @return
     */
    List<Movie> getRecommendMovie(Integer userId);
}
