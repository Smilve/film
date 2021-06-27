package com.lvboaa.service.impl;

import com.lvboaa.mapper.CinemaMapper;
import com.lvboaa.vo.HallVo;
import com.lvboaa.vo.MovieVo;
import com.lvboaa.mapper.HallMapper;
import com.lvboaa.mapper.MovieMapper;
import com.lvboaa.mapper.ScheduleMapper;
import com.lvboaa.pojo.Schedule;
import com.lvboaa.service.ScheduleService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import com.lvboaa.vo.ScheduleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 21:38:50
 */

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private CinemaMapper cinemaMapper;

    public Result paging(HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, scheduleMapper, (a,b)-> b.getAllSchedule(a), (a,b)->b.getScheduleCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    public int insertSchedule(HashMap hashMap) {
        log.debug("新增排片："+hashMap.toString());
        return scheduleMapper.insertSchedule(hashMap);
    }

    @Override
    public int updateSchedule(HashMap hashMap) {
        log.debug("更新排片"+hashMap.toString());
        return scheduleMapper.updateSchedule(hashMap);
    }

    @Override
    public int updateSeats(HashMap hashMap) {
        log.debug("更新座位信息："+hashMap.toString());
        return scheduleMapper.updateSeats(hashMap);
    }

    public int deleteSchedule(Integer scheduleId) {
        log.debug("删除排片："+scheduleId);
        return scheduleMapper.deleteSchedule(scheduleId);
    }

    public int deleteSchedules(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个排片:"+list.toString());
        return scheduleMapper.deleteSchedules(list);
    }

    public List<MovieVo> getAllMovieVo() {
        log.debug("得到所有MovieVoid");
        return movieMapper.getAllMovieVo();
    }

    public List<HallVo> getHallByCinemaId(Integer cinemaId) {
        log.debug("得到电影院的所有影厅："+cinemaId);
        return hallMapper.getHallVoByCinemaId(cinemaId);
    }

    @Override
    public Schedule getScheduleById(Integer scheduleId) {
        log.debug("根据排片Id得到排片信息："+scheduleId);
        return scheduleMapper.getScheduleById(scheduleId);
    }

    @Override
    public HashMap getScheduleInfo(Integer scheduleId) {
        log.debug("根据排片Id得到选座页面信息："+scheduleId);
        HashMap<Object, Object> hashMap = new HashMap<>();
        Schedule schedule = scheduleMapper.getScheduleById(scheduleId);
        hashMap.put("schedule",schedule);
        hashMap.put("movieInfo",movieMapper.getMovieById(schedule.getMovieId()));
        hashMap.put("hallInfo",hallMapper.getHallById(schedule.getHallId()));
        hashMap.put("cinemaInfo",cinemaMapper.getCinemaById(schedule.getCinemaId()));
        List<ScheduleVo> scheduleVos = cinemaMapper.getScheduleByMovieId(schedule.getCinemaId(), schedule.getMovieId());
        for (ScheduleVo scheduleVo:scheduleVos){
            if(scheduleVo.getScheduleId() == scheduleId){
                hashMap.put("scheduleVo",scheduleVo);
                break;
            }
        }
        return hashMap;
    }
}
