package com.zjx.demo;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.MapPropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengjiexiang on 2018/11/1
 */
@SpringBootApplication(exclude={
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
@EnableAutoConfiguration
@ComponentScan
public class RedisTest {

    public static void main(String[] args) {
        SpringApplication.run(RedisTest.class, args);
    }



    public static void set() {
        List<String> nodes = new ArrayList<String>();
        nodes.add("192.168.136.96:6385");
        nodes.add("192.168.136.96:6386");
        nodes.add("192.168.136.97:6385");
        nodes.add("192.168.136.97:6386");
        nodes.add("192.168.136.98:6381");
        nodes.add("192.168.136.98:6382");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxWaitMillis(-1);
        Map<String, Object> resource = new HashMap<String, Object>();
        resource.put("spring.redis.cluster.nodes", "192.168.136.96:6385,192.168.136.96:6386,192.168.136.97:6385,192.168.136.97:6386,192.168.136.98:6381,192.168.136.98:6382");
        resource.put("spring.redis.cluster.max-redirects", 5);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
                new RedisClusterConfiguration(new MapPropertySource("redis-cluster-config", resource)), jedisPoolConfig);
        RedisTemplate redisTemplate = new StringRedisTemplate(jedisConnectionFactory);
        redisTemplate.afterPropertiesSet();
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

//    public static void get() {
//        RedisTemplate redisTemplate = new StringRedisTemplate(new JedisConnectionFactory(
//                new RedisClusterConfiguration(configurationProperties.getNodes())));
//
//        final List<String> keys = new ArrayList<String>();
//        keys.add("1");
//        keys.add("2");
//        keys.add("3");
//        keys.add("4");
//        keys.add("5");
//        keys.add("6");
//        final Map<String, String> map = new HashMap<String, String>();
//        List<Object> result = redisTemplate.executePipelined(new RedisCallback<Object>() {
//
//            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                try {
//                    StringRedisConnection stringRedisConn = (StringRedisConnection)redisConnection;
//                    for(String key : keys) {
//                        stringRedisConn.get(key);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
//        System.out.println(result);
//    }


}
