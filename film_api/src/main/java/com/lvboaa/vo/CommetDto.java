package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lvbo
 * @date 2021年 05月10日 10:24:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommetDto {
    private Integer userId;
    private Integer movieId;
    private Double score;
}
