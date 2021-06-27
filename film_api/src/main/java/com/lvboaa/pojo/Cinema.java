package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 18:21:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cinema implements Serializable {
    private Integer cinemaId;
    private String cinemaName;
    private String phone;
    private String provice;
    private String city;
    private String country;
    private String address;
    //private List<Hall> hallList;
}
