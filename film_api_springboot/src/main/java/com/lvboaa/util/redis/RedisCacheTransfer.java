package com.lvboaa.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheTransfer {

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        RedisCache.setRedisUtil(redisUtil);
    }
}