package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 05月10日 14:51:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    private Integer adminId;
    private String adminName;
    private String password;
    private Integer cinemaId;
    private String token;
}
