package com.lvboaa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lvbo
 * @date 2021年 02月19日 13:42:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {
    private Integer userId;
    private String userName;
    private Integer sex;
    private Date birthday;
    private String photo;
    private String phone;
    private String email;
//    private Integer state;
//    private Integer role;
    private String token;
}
