package com.lvboaa.service;

import com.lvboaa.util.Result;
import com.lvboaa.vo.CommetVo;

import java.util.HashMap;
import java.util.List;

public interface CommetService {
    /**
     * 得到评论分页
     * @param hashMap
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 删除一个评论
     * @param commetId
     * @return
     */
    int deleteCommet(Integer commetId);

    /**
     * 删除多个评论
     * @param ids
     * @return
     */
    int deleteCommets(String ids);

    /**
     *  添加评论
     * @param hashMap
     * @return
     */
    int addCommet(HashMap hashMap);

    /**
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
}
