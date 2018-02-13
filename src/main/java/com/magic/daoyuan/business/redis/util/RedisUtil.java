package com.magic.daoyuan.business.redis.util;

import com.magic.daoyuan.business.redis.init.RedisClient;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * Redis Util
 * Created by Eric Xie on 2017/9/1 0001.
 */
public class RedisUtil {



    private static Jedis redis = RedisClient.getRedisClient().getJedis();


    /**
     * 清空 Redis
     * @return true | false
     */
    public static boolean clearDB(){
        return "OK".equals(redis.flushDB());
    }

    /**
     * 判断 Key是否存在
     * @param key key
     * @return true false
     */
    public static boolean exists(String key){
        return redis.exists(key);
    }

    /**
     * 存储 对象
     * @param key key
     * @param obj 对象
     * @param expireTime 过期时间 (单位:秒)
     * @param <T> 实现序列化接口的对象
     */
    public static <T extends Serializable> void set(String key,T obj,int expireTime){
        if(CommonUtil.isEmpty(key)){
            return;
        }
        byte[] serialize = SerializeUtil.serialize(obj);
        redis.set(key.getBytes(), serialize);
        // 设置时长
        redis.expire(key.getBytes(),expireTime);
    }

    /**
     * 存储对象
     * @param key key
     * @param obj 对象
     * @param <T> 实现序列化接口的对象
     */
    public static <T extends Serializable> void set(String key,T obj){
        if(CommonUtil.isEmpty(key)){
            return;
        }
        byte[] serialize = SerializeUtil.serialize(obj);
        redis.set(key.getBytes(), serialize);
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void set(String key,String value){
        if(CommonUtil.isEmpty(key,value)){
            return;
        }
        redis.set(key,value);
    }

    /**
     *  获取对象
     * @param key key
     * @param clazz class
     * @param <T> 泛型
     * @return
     */
    public static <T> T get(String key,Class<T> clazz){
        if(CommonUtil.isEmpty(key)){
            return null;
        }
        byte[] bytes = redis.get(key.getBytes());
        if(null == bytes){
            return null;
        }
        Object obj = SerializeUtil.unserialize(bytes);
        return null == obj ? null : clazz.cast(obj);
    }


}
