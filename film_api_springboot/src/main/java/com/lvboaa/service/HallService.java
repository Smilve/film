package com.lvboaa.service;

import com.lvboaa.pojo.Hall;
import com.lvboaa.util.Result;
import com.lvboaa.vo.CinemaVo;

import java.util.HashMap;
import java.util.List;

public interface HallService {

    /**
     * 影厅列表分页
     * @param hashMap
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 新增影厅
     * @param hashMap
     * @return
     */
    int insertHall(HashMap hashMap);

    /**
     * 更新影厅
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
     * @param ids
     * @return
     */
    int deleteHalls(String ids);
    /**
     * 通过id得到影厅信息
     * @param hallId
     * @return
     */
    Hall getHallById(Integer hallId);

    /**
     * 得到影院id和名称
     * @return
     */
    List<CinemaVo> getAllCinemaVo();

    /**
     * 更新座位信息
     * @param hashMap
     * @return
     */
    int updateSeat(HashMap hashMap);
}
