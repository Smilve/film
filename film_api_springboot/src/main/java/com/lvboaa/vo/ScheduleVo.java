package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lvbo
 * @date 2021年 05月02日 17:23:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleVo implements Serializable {
    private Integer scheduleId;
    private String date;
    private String language;
    private BigDecimal price;
    private String beginTime;
    private String endTime;
    private String hallName;
}
