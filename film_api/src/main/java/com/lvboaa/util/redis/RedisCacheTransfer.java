package com.lvboaa.util.redis;

import org.springframework.beans.factory.annotation.Autowired;

public class RedisCacheTransfer {

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        RedisCache.setRedisUtil(redisUtil);
    }
}