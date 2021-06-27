package com.lvboaa.test;

import org.springframework.util.DigestUtils;

/**
 * @author lvbo
 * @date 2021年 04月21日 17:19:43
 */
public class Test {

    public static void main(String[] args) {
        String pwd="123456";//要加密的字符串

        String s = DigestUtils.md5DigestAsHex((pwd ).getBytes());
        System.out.println("MD5加密后的字符串为:encodeStr="+s);
    }
    void test(){

    }

}
