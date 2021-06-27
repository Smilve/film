package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvbo
 * @date 2021年 02月17日 18:12:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private Integer orderId;
    private Integer userId;
    private Integer scheduleId;
    private Integer number;
    private BigDecimal price;
    private String seat;
    private Integer state;
    private String code;
    private String date;
    private String userName;
}
