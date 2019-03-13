package com.xwszt.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    @Cacheable(value = "getValue", keyGenerator = "keyGenerator")
    public String get(String key) {
        logger.info("第一次从DB读取");
        return redisTemplate.opsForValue().get(key).toString();
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
