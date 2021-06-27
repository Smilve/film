package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 03月04日 23:39:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieVo implements Serializable {
    private Integer movieId;
    private String movieName;
}
