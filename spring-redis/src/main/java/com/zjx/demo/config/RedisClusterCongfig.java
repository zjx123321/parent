package com.zjx.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengjiexiang on 2018/2/28
 */
@Component
public class RedisClusterCongfig {

    @Bean("redisClusterTemplate")
    @Primary
    public RedisTemplate redisTemplate(@Qualifier("cluster_jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }

    @Bean("cluster_jedisConnectionFactory")
    @Primary
    private JedisConnectionFactory jedisConnectionFactory(@Qualifier("redisClusterConfiguration") RedisClusterConfiguration redisClusterConfiguration,
                                                          @Qualifier("cluster_jedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
    }

    @Bean("redisClusterConfiguration")
    private RedisClusterConfiguration redisClusterConfiguration(@Qualifier("mapPropertySource") MapPropertySource mapPropertySource) {
        return new RedisClusterConfiguration(mapPropertySource);
    }

    @Bean("cluster_jedisPoolConfig")
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxWaitMillis(-1);
        return jedisPoolConfig;
    }

    @Bean("mapPropertySource")
    private MapPropertySource mapPropertySource() {
        Map<String, Object> resource = new HashMap<String, Object>();
        resource.put("spring.redis.cluster.nodes", "192.168.136.96:6385,192.168.136.96:6386,192.168.136.97:6385,192.168.136.97:6386,192.168.136.98:6381,192.168.136.98:6382");
        resource.put("spring.redis.cluster.max-redirects", 5);
        return new MapPropertySource("redis-cluster-config", resource);
    }

}
