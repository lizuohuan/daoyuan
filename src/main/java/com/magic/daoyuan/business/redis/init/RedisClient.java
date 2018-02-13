package com.magic.daoyuan.business.redis.init;

import com.magic.daoyuan.business.redis.config.RedisConfig;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Initializes the Redis Server
 * Created by Eric Xie on 2017/8/15 0015.
 */
public class RedisClient {

    private JedisPool jedisPool;//非切片连接池

    private ShardedJedisPool shardedJedisPool;//切片连接池

    private static RedisClient redisClient;

    private RedisClient() {
        if ("true".equals(RedisConfig.getValue("startServer"))) {
            initialPool();
        }
        if ("true".equals(RedisConfig.getValue("startCluster"))) {
            initialShardedPool();
        }
    }

    /**
     * 初始化 非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(RedisConfig.getValue("maxTotal")));
        config.setMaxIdle(Integer.valueOf(RedisConfig.getValue("maxIdle")));
        config.setMaxWaitMillis(Integer.valueOf(RedisConfig.getValue("maxWaitMillis")));
        config.setTestOnBorrow("true".equals(RedisConfig.getValue("testOnBorrow")));
        jedisPool = new JedisPool(config, RedisConfig.getValue("ip"), Integer.valueOf(RedisConfig.getValue("port")));
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 获取节点数据
        List<Map<String, String>> nodes = RedisConfig.getNodes();
        if (null == nodes) {
            return;
        }
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(RedisConfig.getValue("maxTotal")));
        config.setMaxIdle(Integer.valueOf(RedisConfig.getValue("maxIdle")));
        config.setMaxWaitMillis(Integer.valueOf(RedisConfig.getValue("maxWaitMillis")));
        config.setTestOnBorrow("true".equals(RedisConfig.getValue("testOnBorrow")));
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        for (Map<String, String> node : nodes) {
            if(shards.size() > 0){
                shards.add(new JedisShardInfo(node.get("ip"), Integer.valueOf(node.get("port")), "slaveof"));
                continue;
            }
            shards.add(new JedisShardInfo(node.get("ip"), Integer.valueOf(node.get("port")), "master"));
        }
        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public void Close() {
        jedisPool.close();
        shardedJedisPool.close();
    }


    public static RedisClient getRedisClient() {
        if (redisClient == null) {
            synchronized (RedisClient.class) {
                if (redisClient == null) redisClient = new RedisClient();
            }
        }
        return redisClient;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public ShardedJedis getShardedJedis() {
        return shardedJedisPool.getResource();
    }


}
