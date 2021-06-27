package com.lvboaa.util;

import com.lvboaa.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author lvbo
 * @date 2021年 02月19日 12:56:57
 */
@Slf4j
public class JwtUtils {
    //过期时间设置为一个小时
    private static final long EXPTRE_TIME= 60 * 60 * 1000;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET="8ae0d24822ef59d9e75745449b3501bc";
    /**
     * 生成签名
     */
    public static String Sign(int userID, String userName, int role){

        //当前时间
        long now = System.currentTimeMillis();
        //过期时间
        Date date = new Date(now + EXPTRE_TIME);
        String token="";
        try{
            token = Jwts.builder()
                    .setIssuedAt(new Date(now))
                    .setExpiration(date)
                    .signWith(SignatureAlgorithm.HS256,TOKEN_SECRET)
                    .claim("loginName",userName)
                    .claim("userID",userID)
                    .claim("role",role)
                    .compact();
        }catch (Exception e){
            log.debug("token生成有问题");
        }
        return token;
    }

    /**
     * token解码+提取内容
     */
    public static User parseJwt(String token){
        try{
            Claims claims = (Claims) Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parse(token)
                    .getBody();
            User user=new User();
            user.setUserName(claims.get("loginName").toString());
            user.setUserId(Integer.valueOf(claims.get("userID").toString()));
            user.setRole(Integer.valueOf(claims.get("role").toString()));
            return user;
        }catch (Exception e){
            log.debug("token有问题");
            return null;
        }
    }
}
