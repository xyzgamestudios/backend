package com.xwszt.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author xwszt
 */
@Service
public class DemoService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static String PONG = "PONG";

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    private static Logger logger = LoggerFactory.getLogger(DemoService.class);

    @Cacheable(value = "getValue", key = "#key")
    public String get(String key) {
        logger.info("第一次从DB读取");
        return redisTemplate.opsForValue().get(key).toString();
    }

    public String benchmark() {
        Runtime runtime = Runtime.getRuntime();
        String line;
        StringBuffer sb = new StringBuffer();
        try {
            Process process = runtime.exec(" redis-benchmark -c 60 -n 1000  -h 172.16.172.128 -p 7001 -a passwd123");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public Properties info(String section) {
        Properties info;
        if (StringUtils.isEmpty(section)) {
            info = lettuceConnectionFactory.getClusterConnection().info();
        } else {
            info = lettuceConnectionFactory.getClusterConnection().info(section);
        }
        return info;
    }

    public void meet(String ip, Integer port) {
        RedisClusterNode newNode = new RedisClusterNode(ip, port);
        redisTemplate.opsForCluster().meet(newNode);
    }

    public void forget(String ip, Integer port) {
        RedisClusterNode node = new RedisClusterNode(ip, port);
        redisTemplate.opsForCluster().forget(node);
    }

    public void shutdown(String ip, Integer port) throws Exception {
        RedisClusterNode node = new RedisClusterNode(ip, port);
        String ping = redisTemplate.opsForCluster().ping(node);

        if (PONG.equals(ping)) {
            redisTemplate.opsForCluster().shutdown(node);
        } else {
            throw new Exception("节点不存在");
        }
    }

    public Set<String> keys(){
        Set<byte[]> keys = lettuceConnectionFactory.getClusterConnection().keys("*".getBytes());
        Set<String> result = new HashSet<>();
        for (byte[] byteKey : keys) {
            result.add(new String(byteKey));
        }
        return result;
    }

    public void checkNodes() {

        Iterable<RedisClusterNode> clusterNodes = lettuceConnectionFactory.getClusterConnection().clusterGetNodes();
        for (RedisClusterNode clusterNode : clusterNodes) {
            String ping = null;
            try {
                ping = redisTemplate.opsForCluster().ping(clusterNode);
            } catch (Exception e){
                e.printStackTrace();
            }

            logger.debug("ping result ==>>>>>  " + ping);
            if (!PONG.equals(ping)) {
                logger.debug("clusternode ip: " + clusterNode.getHost() + ", port : " + clusterNode.getPort() + " disconnected.");
                redisTemplate.opsForCluster().forget(clusterNode);
                logger.debug("clusternode ip: " + clusterNode.getHost() + ", port : " + clusterNode.getPort() + " node has removed.");
            } else {
                logger.debug("clusternode ip: " + clusterNode.getHost() + ", port : " + clusterNode.getPort() + " connected.");
            }
        }
    }
}
