package com.lvboaa.service;

import com.lvboaa.vo.HallVo;
import com.lvboaa.vo.MovieVo;
import com.lvboaa.pojo.Schedule;
import com.lvboaa.util.Result;

import java.util.HashMap;
import java.util.List;

public interface ScheduleService {

    /**
     * 排片列表分页
     * @param hashMap
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 添加排片
     * @param hashMap
     * @return
     */
    int insertSchedule(HashMap hashMap);

    /**
     * 更新排片
     * @param hashMap
     * @return
     */
    int updateSchedule(HashMap hashMap);

    /**
     * 更新座位信息
     * @param hashMap
     * @return
     */
    int updateSeats(HashMap hashMap);

    /**
     * 删除排片
     * @param scheduleId
     * @return
     */
    int deleteSchedule(Integer scheduleId);

    /**
     * 删除多个排片
     * @param ids
     * @return
     */
    int deleteSchedules(String ids);

    /**
     * 得到所有电影id和名字
     * @return
     */
    List<MovieVo> getAllMovieVo();

    /**
     * 根据电影院id得到影厅信息
     * @param cinemaId
     * @return
     */
    List<HallVo> getHallByCinemaId(Integer cinemaId);

    /**
     * 根据排片id得到排片信息
     */
    Schedule getScheduleById(Integer scheduleId);


    /**
     * 根据排片id得到选座页面的信息
     * @param scheduleId
     */
    HashMap getScheduleInfo(Integer scheduleId);
}
