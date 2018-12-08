package com.thread.concurrent;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

import static org.junit.Assert.assertEquals;

/**
 * Created by David on 2018/2/3.
 */
@RunWith(ConcurrentTestRunner.class) public class CounterTest {
  private final int threadsNum = 100;
  private Counter counter = new Counter();

  @ThreadCount(value = threadsNum)
  @Test public void addOne() {
    counter.addOne();
  }

  @After public void testCount() {
    assertEquals("100 Threads running addOne in parallel should lead to 100", threadsNum, counter.getCount());
  }

}