package com.lvboaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lvbo
 * @date 2021年 02月17日 17:51:52
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String password;
    private Integer sex;
    private Date birthday;
    private String photo;
    private String phone;
    private String email;
    private Integer state;
    private Integer role;
}
