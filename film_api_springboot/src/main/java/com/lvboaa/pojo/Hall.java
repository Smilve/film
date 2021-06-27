package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 02月17日 18:18:17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall implements Serializable {
    private Integer hallId;
    private String hallName;
    private Integer cinemaId;
    private Integer row;
    private Integer col;
    private String seat;
    private String cinemaName;
}
