package com.lvboaa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 02月18日 21:57:11
 */
@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto implements Serializable {
    private String username;
    private String password;
}
