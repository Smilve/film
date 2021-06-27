package com.lvboaa.mapper;


import com.lvboaa.vo.HallVo;
import com.lvboaa.pojo.Hall;

import java.util.HashMap;
import java.util.List;


public interface HallMapper {

    /**
     * 得到所有影厅
     * @return
     */
    List<Hall> getAllHall(HashMap hashMap);


    /**
     * 影厅总数
     * @return
     */
    int getHallCount(HashMap hashMap);

    /**
     * 新增影厅
     * @param hashMap
     * @return
     */
    int insertHall(HashMap hashMap);

    /**
     * 更新影厅信息
     * @param hashMap
     * @return
     */
    int updateHall(HashMap hashMap);

    /**
     * 删除影厅
     * @param hallId
     * @return
     */
    int deleteHall(Integer hallId);

    /**
     * 删除多个影厅
     * @param list
     * @return
     */
    int deleteHalls(List list);
    
    /**
     * 根据影厅id得到影厅
     * @param hallId
     * @return
     */
    Hall getHallById(Integer hallId);

    /**
     * 得到指定电影院的所有影厅
     * @param cinemaId
     * @return
     */
    List<HallVo> getHallVoByCinemaId(Integer cinemaId);

    /**
     * 更新座位信息
     * @param hashMap
     * @return
     */
    int updateSeat(HashMap hashMap);
}
