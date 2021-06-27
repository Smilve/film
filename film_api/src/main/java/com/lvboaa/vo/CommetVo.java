package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvbo
 * @date 2021年 04月28日 21:34:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommetVo implements Serializable {
    private Integer commetId;
    private Integer userId;
    private Integer movieId;
    private BigDecimal score;
    private String content;
    private String date;
    private String userName;
    private String photo;
}
