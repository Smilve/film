package com.lvboaa.controller;

import com.lvboaa.service.ScheduleService;
import com.lvboaa.util.Result;
import com.lvboaa.vo.HallVo;
import com.lvboaa.vo.MovieVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 21:41:08
 */

@RestController
@RequestMapping("/schedule")
@CrossOrigin
@Api(tags = "Schedule", description = "排片")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/schedules")
    @ApiOperation("获取排片列表")
    public Result getAllschedule(@RequestBody HashMap<String,Object> hashMap){
        return scheduleService.paging(hashMap);
    }

//    @GetMapping("/schedule/{scheduleId}")
//    @ApiOperation("根据id获得影厅")
//    Result getscheduleById(@PathVariable("scheduleId")Integer scheduleId){
//        return Result.success(scheduleService.getScheduleById(scheduleId));
//    }

    @GetMapping("/{scheduleId}")
    public Result getScheduleById(@PathVariable("scheduleId")Integer scheduleId){
        return Result.success(scheduleService.getScheduleById(scheduleId));
    }

    @PostMapping("/insert")
    @ApiOperation("添加排片")
    public Result insertschedule(@RequestBody HashMap<String,Object> hashMap){
        if(scheduleService.insertSchedule(hashMap)==0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新排片")
    public Result updateSchedule(@RequestBody HashMap<String,Object> hashMap){
        if(scheduleService.updateSchedule(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/seats")
    @ApiOperation("更新座位信息")
    public Result updateSeats(@RequestBody HashMap<String,Object> hashMap){
        if(scheduleService.updateSeats(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }


    @DeleteMapping("/del/{scheduleId}")
    @ApiOperation("删除排片")
    public Result deleteschedule(@PathVariable("scheduleId") Integer scheduleId){
        if(scheduleService.deleteSchedule(scheduleId) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/delAll")
    @ApiOperation("删除多个排片")
    public Result deleteschedules(@RequestBody String ids){
        if(scheduleService.deleteSchedules(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @GetMapping("/getMovieInfo")
    @ApiOperation("得到所有电影id和名字")
    public Result getAllMovieVo(){
        List<MovieVo> allMovieVo = scheduleService.getAllMovieVo();
        if(allMovieVo == null){
            return Result.failure();
        }
        return Result.success(allMovieVo);
    }
    @GetMapping("/getHallInfo/{cinemaId}")
    @ApiOperation("得到所有电影id和名字")
    public Result getAllHallInfo(@PathVariable("cinemaId") Integer cinemaId){
        List<HallVo> allHallVo = scheduleService.getHallByCinemaId(cinemaId);
        if(allHallVo == null){
            return Result.failure();
        }
        return Result.success(allHallVo);
    }

    @GetMapping("/scheduleInfo/{scheduleId}")
    @ApiOperation("根据排片id得到排片信息")
    public Result getScheduleInfo(@PathVariable("scheduleId") Integer scheduleId){
        HashMap hashMap = scheduleService.getScheduleInfo(scheduleId);
        if(hashMap == null){
            return Result.failure();
        }
        return Result.success(hashMap);
    }
}
