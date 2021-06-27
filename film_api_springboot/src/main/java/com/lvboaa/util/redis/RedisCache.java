package com.lvboaa.util.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class RedisCache implements Cache {

    private static RedisUtil redisUtil;

    private final String id;

    public static void setRedisUtil(RedisUtil redisUtil) {
        RedisCache.redisUtil = redisUtil;
    }

    /**
     * The {@code ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public ReadWriteLock getReadWriteLock()
    {
        return this.readWriteLock;
    }

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.debug("RedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try{
            log.info("redis >>>>>>>>putObject: key="+key+",value="+value);
            if(null!=value)
                redisUtil.set(key.toString(),value,60);
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis保存数据异常！");
        }
    }

    @Override
    public Object getObject(Object key) {
        try{
            log.info("redis >>>>>>>>>>>>>>>>>>getObject: key="+key);
            if(null!=key)
                return redisUtil.get(key.toString());
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis获取数据异常！");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try{
            if(null!=key)
               return redisUtil.del(key.toString());
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis删除数据异常！");
        }
        return null;
    }

    @Override
    public void clear() {
        try{
            Long size=redisUtil.clear();
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>clear: 清除了" + size + "个对象");
        }catch (Exception e){
            e.printStackTrace();
            log.error("清空redis数据库异常");
        }
    }

    @Override
    public int getSize() {
        try{
            Long size = redisUtil.getSize();
            return size.intValue();
        }catch (Exception e){
            e.printStackTrace();
            log.error("得到数据条数异常");
        }
        return 0;
    }
      
}  