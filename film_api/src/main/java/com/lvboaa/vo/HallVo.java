package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 03月05日 00:12:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallVo implements Serializable {
    private Integer hallId;
    private String hallName;
    private String seat;
}
