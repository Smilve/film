package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lvbo
 * @date 2021年 02月17日 18:07:52
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {
    private Integer scheduleId;
    private Integer movieId;
    private Integer cinemaId;
    private Integer hallId;
    private Integer movieIntervalId;
    private String date;
    private String language;
    private BigDecimal price;
    private String seat;
    private String movieName;
    private String cinemaName;
    private String hallName;
    private String movieIntervalName;
}
