package com.silent.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhao
 * @date 2021/11/12 17:40
 */

@Configuration
@ConditionalOnClass({JedisCluster.class})
@EnableConfigurationProperties(RedisProperties.class)
public class JedisClusterConfig {


    @Resource
    private RedisProperties redisProperties;

    @Bean
    public JedisCluster getJedisCluster() {
        List<String> listNodes = redisProperties.getCluster().getNodes();
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort: listNodes) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.parseInt(ipPortPair[1].trim())));
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisProperties.getJedis().getPool().getMaxActive());
        return new JedisCluster(nodes, config);
    }

}
