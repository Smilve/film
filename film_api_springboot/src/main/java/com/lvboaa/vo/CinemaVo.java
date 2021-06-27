package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 03月04日 21:47:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaVo implements Serializable {
    private Integer cinemaId;
    private String cinemaName;
}
