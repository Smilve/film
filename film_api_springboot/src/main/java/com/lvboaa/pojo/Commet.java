package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 02月17日 18:19:10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commet implements Serializable {
    private Integer commetId;
    private Integer userId;
    private Integer movieId;
    private Double score;
    private String content;
    private String date;
    private String userName;
    private String movieName;
}
