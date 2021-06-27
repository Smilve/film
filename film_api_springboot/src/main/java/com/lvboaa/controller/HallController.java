package com.lvboaa.controller;

import com.lvboaa.service.HallService;
import com.lvboaa.util.Result;
import com.lvboaa.vo.CinemaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 22:14:46
 */
@RestController
@RequestMapping("/hall")
@CrossOrigin
@Api(tags = "hall", description = "影厅")
public class HallController {
    @Autowired
    private HallService hallService;
    @PostMapping("/halls")
    @ApiOperation("获取影厅列表")
    public Result getAllHall(@RequestBody HashMap<String,Object> hashMap){
        return hallService.paging(hashMap);
    }

    @GetMapping("/hall/{hallId}")
    @ApiOperation("根据id获得影厅")
    public Result getHallById(@PathVariable("hallId")Integer hallId){
        return Result.success(hallService.getHallById(hallId));
    }


    @PostMapping("/insert")
    @ApiOperation("添加影厅")
    public Result inserthall(@RequestBody HashMap<String,Object> hashMap){
        if(hallService.insertHall(hashMap)==0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新影厅")
    public Result updatehall(@RequestBody HashMap<String,Object> hashMap){

        if(hallService.updateHall(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @DeleteMapping("/del/{hallId}")
    @ApiOperation("删除影厅")
    public Result deletehall(@PathVariable("hallId") Integer hallId){
        if(hallService.deleteHall(hallId) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/delAll")
    @ApiOperation("删除多个影厅")
    public Result deletehalls(@RequestBody String ids){
        if(hallService.deleteHalls(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @GetMapping("/getCinemaInfo")
    @ApiOperation("得到所有影院id和名称")
    public Result getAllCinemaVo(){
        List<CinemaVo> cinemaVos = hallService.getAllCinemaVo();
        if(cinemaVos == null){
            return Result.failure();
        }
        return Result.success(cinemaVos);
    }

    @PostMapping("/updateSeat")
    @ApiOperation("更新座位信息")
    public Result updateSeat(@RequestBody HashMap hashMap){
        if(hallService.updateSeat(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }
}
