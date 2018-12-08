package com.thread.googleweaver;

import org.junit.Test;

import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedAfter;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;

import static org.junit.Assert.assertEquals;

/**
 * Created by David on 2018/2/5.
 */
public class MyListTest {
  MyList list;
  // This method is invoked by the regular JUnit test runner.
  @Test
  public void testPutIfAbsent_Threading() throws Exception{
    AnnotatedTestRunner runner = new AnnotatedTestRunner();
    // Run all Weaver tests in this class, using MyList as the Class Under Test.
    runner.runTests(this.getClass(), MyList.class);
  }

  @ThreadedBefore
  public void before() {
    list = new MyList();
  }

  @ThreadedMain
  public void mainThread() {
    list.putIfAbsent("A");
  }

  @ThreadedSecondary
  public void secondThread() {
    list.putIfAbsent("A");
  }

  @ThreadedAfter
  public void after() {
    assertEquals("this test expected assert fail!",1, list.size());
  }
}