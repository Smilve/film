package com.lvboaa.mapper;


import com.lvboaa.pojo.Commet;
import com.lvboaa.vo.CommetDto;
import com.lvboaa.vo.CommetVo;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface CommetMapper {

    /**
     * 按条件获取所有评论
     * @return
     */
    List<Commet> getAllCommet(HashMap hashMap);

    /**
     * 获取评论总数
     * @param hashMap
     * @return
     */
    int getCommetCount(HashMap hashMap);

    /**
     * 删除评论
     * @param commetId
     * @return
     */
    int deleteCommet(Integer commetId);

    /**
     * 删除多个评论
     * @param list
     * @return
     */
    int deleteCommets(List list);

    /**
     * 新增评论
     * @param hashMap
     * @return
     */
    int addCommet(HashMap hashMap);

    /***
     * 得到指定电影id的所有评论
     * @param movieId
     * @return
     */
    List<CommetVo> getCommetByMovieId(Integer movieId);

    /**
     * 根据电影id得到平均评分
     * @param movieId
     * @return
     */
    HashMap getAvgScoreByMovieId(Integer movieId);

    /**
     * 根据电影id和用户id得到评论
     * @param hashMap
     * @return
     */
    Commet getCommetByUserAndMovieId(HashMap hashMap);

    /**
     * 更新评论
     * @param hashMap
     * @return
     */
    int updateCommet(HashMap hashMap);

    /**
     * 得到评论用户id，电影id和评分
     * @return
     */
    List<CommetDto> getAllCommetDto();
}
