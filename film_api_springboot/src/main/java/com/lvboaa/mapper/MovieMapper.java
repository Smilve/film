package com.lvboaa.mapper;

import com.lvboaa.pojo.Movie;
import com.lvboaa.vo.MovieVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface MovieMapper {

    /**
     * 得到所有电影
     * @return
     */
    List<Movie> getAllMovie(HashMap hashMap);

    /**
     * 根据电影id得到电影
     * @param movieId
     * @return
     */
    Movie getMovieById(Integer movieId);
    /**
     * 电影总数
     * @return
     */
    int getMovieCount(HashMap hashMap);

    /**
     * 新增电影
     * @param hashMap
     * @return
     */
    int insertMovie(HashMap hashMap);

    /**
     * 更新电影信息
     * @param hashMap
     * @return
     */
    int updateMovie(HashMap hashMap);

    /**
     * 更新分数
     * @param hashMap
     * @return
     */
    int updateScore(HashMap hashMap);

    /**
     * 删除电影
     * @param movieId
     * @return
     */
    int deleteMovie(Integer movieId);

    /**
     * 删除多个电影
     * @param list
     * @return
     */
    int deleteMovies(List list);

    /**
     * 得到所有的电影id和名字
     * @return
     */
    List<MovieVo> getAllMovieVo();


    /**
     * 得到所有正在上映的电影
     * @param hashMap
     * @return
     */
    List<Movie> getAllIngMovie(HashMap hashMap);

    /**
     * 上映的电影总数
     * @param hashMap
     * @return
     */
    int getAllIngMovieCount(HashMap hashMap);

    /**
     * 得到所有即将上映的电影
     * @param hashMap
     * @return
     */
    List<Movie> getAllWillMovie(HashMap hashMap);

    /**
     * 即将上映的电影总数
     * @param hashMap
     * @return
     */
    int getAllWillMovieCount(HashMap hashMap);

    /**
     * 得到经典的电影
     * @param hashMap
     * @return
     */
    List<Movie> getClassicMovie(HashMap hashMap);

    /**
     * 经典的电影总数
     * @param hashMap
     * @return
     */
    int getClassicMovieCount(HashMap hashMap);

    /**
     * 获取榜单电影
     * @param hashMap
     * @return
     */
    List<Movie> getTopMovie(HashMap hashMap);

    /**
     * 根据电影名模糊查询电影
     * @param hashMap
     * @return
     */
    List<Movie> getMovieByName(HashMap hashMap);

    /**
     * 根据id的list 得到多个电影
     * @param list
     * @return
     */
    List<Movie> getMovieByList(List list);
}
