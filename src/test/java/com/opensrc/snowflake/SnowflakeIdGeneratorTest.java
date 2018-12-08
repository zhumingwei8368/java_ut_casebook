package com.opensrc.snowflake;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SnowflakeIdGeneratorTest {
  private SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1,1);
  @Test
  public void test_singleThread_notRepeat() throws Exception {
    Stopwatch stopwatch = Stopwatch.createUnstarted();

    Set<Long> setOne = new HashSet<>();

    stopwatch.start();
    int firstRound = 100000;
    for (int i = 0; i < firstRound; i++) {
      setOne.add(idGenerator.nextId());
    }
    stopwatch.stop();
    System.out.println("cost(ms):"+stopwatch.elapsed(TimeUnit.MILLISECONDS));

    assertEquals(firstRound, setOne.size());
  }

  @Test
  public void test_generator_one_second() throws Exception {
    long start =System.currentTimeMillis();
    int count = 0;
    for (int i = 0; (System.currentTimeMillis()-start)<1000; i++,count=i) {
      idGenerator.nextId();
    }
    System.out.println("generate id number in 1s:"+count);
    assertTrue(count>100000);
  }

  @Test
  public void test_multiThreads() throws Exception {
    Set<Long> setOne = new HashSet<>();
    int firstRound = 1000;

    long start =System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          for (int j = 0; j < firstRound; j++) {
            setOne.add(idGenerator.nextId());
          }
        }
      });
      thread.start();
      thread.join();
    }

    System.out.println("generate id number in 1s:" + (System.currentTimeMillis()-start));
    assertEquals(firstRound*100, setOne.size());
  }
}
