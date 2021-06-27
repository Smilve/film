package com.lvboaa.service.impl;

import com.lvboaa.vo.CinemaVo;
import com.lvboaa.mapper.CinemaMapper;
import com.lvboaa.mapper.HallMapper;
import com.lvboaa.pojo.Hall;
import com.lvboaa.service.HallService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 22:12:49
 */
@Service
@Slf4j
public class HallServiceImpl implements HallService {
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private CinemaMapper cinemaMapper;

    public Result paging(HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, hallMapper, (a,b)-> b.getAllHall(a), (a,b)->b.getHallCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    public int insertHall(HashMap hashMap) {
        log.debug("新增影厅："+hashMap.toString());
        return hallMapper.insertHall(hashMap);
    }

    public int updateHall(HashMap hashMap) {
        log.debug("更新影厅："+hashMap.toString());
        return hallMapper.updateHall(hashMap);
    }

    public int deleteHall(Integer hallId) {
        log.debug("删除影厅："+hallId);
        return hallMapper.deleteHall(hallId);
    }

    public int deleteHalls(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个影厅:"+list.toString());
        return hallMapper.deleteHalls(list);
    }

    public Hall getHallById(Integer hallId) {
        log.debug("根据id得到信息："+hallId);
        return hallMapper.getHallById(hallId);
    }

    public List<CinemaVo> getAllCinemaVo() {
        log.debug("得到CinemaVo");
        return cinemaMapper.getAllCinemaVo();
    }

    @Override
    public int updateSeat(HashMap hashMap) {
        log.debug("更新座位信息："+hashMap.toString());
        return hallMapper.updateSeat(hashMap);
    }
}
