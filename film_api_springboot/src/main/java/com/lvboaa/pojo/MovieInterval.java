package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 04月19日 16:40:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInterval implements Serializable {
    private Integer movieIntervalId;
    private String movieIntervalName;
    private String beginTime;
    private String endTime;
}
