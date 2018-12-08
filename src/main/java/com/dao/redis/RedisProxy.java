package com.dao.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by David on 2018/2/2.
 */
public class RedisProxy {
  private final static String IP = "10.180.108.205"; //my real redis server
  private final static int PORT = 6379;

  public static Jedis getRedis(){
    return new Jedis(IP, PORT);
  }
}
