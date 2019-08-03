package com.zjx.demo.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengjiexiang on 2018/11/1
 */
@RestController
public class TestController {

    @Resource(name = "redisClusterTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public void set() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        final List<String> keys = new ArrayList<String>();
        keys.add("1");
        keys.add("2");
        keys.add("3");
        keys.add("4");
        keys.add("5");
        keys.add("6");
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                try {
                    StringRedisConnection stringRedisConn = (StringRedisConnection)redisConnection;
                    for(String key : keys) {
                        stringRedisConn.set(key, key, Expiration.from(5, TimeUnit.MINUTES), RedisStringCommands.SetOption.SET_IF_ABSENT);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Object> get() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        final List<String> keys = new ArrayList<String>();
        keys.add("1");
        keys.add("2");
        keys.add("3");
        keys.add("4");
        keys.add("5");
        keys.add("6");
        List<Object> result = redisTemplate.executePipelined(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                try {
                    StringRedisConnection stringRedisConn = (StringRedisConnection)redisConnection;
                    for(String key : keys) {
                        stringRedisConn.get(key);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });
        return result;
    }

}
