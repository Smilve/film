package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lvbo
 * @date 2021年 05月03日 19:21:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo implements Serializable {
    private Integer orderId;
    private String date;
    private String cinemaName;
    private String hallName;
    private String movieName;
    private String photo;
    private String beginTime;
    private String seat;
    private Integer state;
    private BigDecimal price;
    private String code;
}
