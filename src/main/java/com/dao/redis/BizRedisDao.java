package com.dao.redis;

import com.google.common.base.Strings;

import redis.clients.jedis.Jedis;

/**
 * Created by David on 2018/2/2.
 */
public class BizRedisDao {
  private static final String AGENT_KEY = "BizHostStatus";
  private static final Jedis REDIS = RedisProxy.getRedis();

  public static String getIpStatus(String ip) {
    if (Strings.isNullOrEmpty(ip)) {
      return "";
    }
    try {
      return REDIS.hget(AGENT_KEY, ip);
    } catch (Exception ex) {
      return "";
    }
  }

  public static void setStatus(String ip, String status) {
    try {
      REDIS.hset(AGENT_KEY, ip, status);
    } catch (Exception ex) {
    }
  }
}
