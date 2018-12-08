package com.dao.redis;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

/**
 * Created by David on 2018/2/2.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BizRedisDao.class, RedisProxy.class})
public class BizRedisDaoTest {
  private static RedisServer redisServer;
  private static Jedis jedis;

  @BeforeClass
  public static void setup() throws IOException {
    redisServer = RedisServer.builder().port(6379).setting("maxmemory 64M").build();
    redisServer.start();
    jedis = new Jedis("127.0.0.1", 6379);

    PowerMockito.mockStatic(RedisProxy.class);
    PowerMockito.when(RedisProxy.getRedis()).thenReturn(jedis);
  }
  @AfterClass
  public static void teardown(){
    if (jedis != null){
      jedis.close();
    }
    if (redisServer != null){
      redisServer.stop();
    }
  }

  @Test public void test_get_and_set() throws Exception {
    BizRedisDao.setStatus("1.1.1.1","1");
    Assert.assertEquals("1", BizRedisDao.getIpStatus("1.1.1.1"));
  }

}