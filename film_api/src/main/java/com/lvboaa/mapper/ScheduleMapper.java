package com.lvboaa.mapper;

import com.lvboaa.pojo.Schedule;

import java.util.HashMap;
import java.util.List;

public interface ScheduleMapper {

    /**
     * 获取所有排片
     * @return
     */
    List<Schedule> getAllSchedule(HashMap hashMap);

    /**
     * 排片总数
     * @return
     */
    int getScheduleCount(HashMap hashMap);

    /**
     * 新增排片
     * @param hashMap
     * @return
     */
    int insertSchedule(HashMap hashMap);

    /**
     * 新增排片
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
     * 撤销排片
     * @param scheduleId
     * @return
     */
    int deleteSchedule(Integer scheduleId);

    /**
     * 撤销多个排片
     * @param list
     * @return
     */
    int deleteSchedules(List list);
    /**
     * 通过id得到排片信息
     * @param scheduleId
     * @return
     */
    Schedule getScheduleById(Integer scheduleId);
}
