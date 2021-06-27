package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvbo
 * @date 2021年 02月17日 18:15:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {
    private Integer movieId;
    private String movieName;
    private String photo;
    private String director;
    private String actor;
    private String type;
    private String nation;
    private String language;
    private Integer time;
    private String date;
    private BigDecimal score;
    private String intro;
}
