package com.thread.concurrent;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.thread.googleweaver.MyList;

import static org.junit.Assert.assertEquals;

/**
 * Created by David on 2018/2/5.
 */
@RunWith(ConcurrentTestRunner.class)
public class MyListTest {
  MyList list = new MyList();;
  private final int threadsNum = 10;

  @ThreadCount(value = threadsNum)
  @Test
  public void testPutIfAbsent() {
    list.putIfAbsent("A");
  }

  @After public void testCount() {
    assertEquals( "sometimes this test will assert fail!",1, list.size());
  }
}