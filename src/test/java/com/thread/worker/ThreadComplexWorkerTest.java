package com.thread.worker;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Lists;

/**
 * Created by David on 2018/1/26.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ThreadComplexWorker.class, DataRecorder.class, Thread.class})
public class ThreadComplexWorkerTest {

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(DataRecorder.class);
    PowerMockito.doNothing().when(DataRecorder.class);
    DataRecorder.record("key1", 2);
    mockThreadSleep();
  }

  @Test public void test_run_success() throws Exception {
    SimpleDataInput input = new SimpleDataInput("key1", Lists.newArrayList("v1", "v2"));
    SimpleDataOutput output = new SimpleDataOutput();
    ThreadComplexWorker worker = new ThreadComplexWorker(input, output);

    worker.run();

    Assert.assertEquals(Lists.newArrayList("v2","v1"), output.getData());
    PowerMockito.verifyStatic(DataRecorder.class);
    DataRecorder.record("key1", 2);
  }

  @Test public void test_run_poll_fail_10times() throws Exception {
    CountedDataInput input = new CountedDataInput("key1", Lists.newArrayList("v1", "v2"), 10);
    CountedDataOutput output = new CountedDataOutput(0);
    ThreadComplexWorker worker = new ThreadComplexWorker(input, output);

    worker.run();

    Assert.assertEquals(1+10, input.getCount());
    Assert.assertEquals(1, output.getCount());
    Assert.assertEquals(Lists.newArrayList("v2","v1"), output.getData());

    PowerMockito.verifyStatic(DataRecorder.class, Mockito.times(1));
    DataRecorder.record("key1", 2);
    PowerMockito.verifyStatic(DataRecorder.class, Mockito.times(0));
    DataRecorder.recordFail("key1");
  }

  @Test public void test_run_send_fail_10times() throws Exception {
    CountedDataInput input = new CountedDataInput("key1", Lists.newArrayList("v1", "v2"), 5);
    CountedDataOutput output = new CountedDataOutput(10);
    ThreadComplexWorker worker = new ThreadComplexWorker(input, output);

    worker.run();

    Assert.assertEquals(1+5, input.getCount());
    Assert.assertEquals(1+10, output.getCount());
    Assert.assertEquals(Lists.newArrayList("v2","v1"), output.getData());

    PowerMockito.verifyStatic(DataRecorder.class, Mockito.times(10));
    DataRecorder.recordFail("key1");
    PowerMockito.verifyStatic(DataRecorder.class, Mockito.times(1));
    DataRecorder.record("key1", 2);
  }

  private void mockThreadSleep() throws Exception {
    PowerMockito.mockStatic(Thread.class);
    PowerMockito.doNothing().when(Thread.class);
    Thread.sleep(Mockito.anyLong());
  }


  private class SimpleDataInput implements ItfDataInput {
    private String key;
    private List<String> data;

    public SimpleDataInput(String key, List<String> data) {
      this.key = key;
      this.data = data;
    }

    public List<String> pollData(String key) {
      return data;
    }

    public String getKey() {
      return key;
    }
  }
  private class SimpleDataOutput implements ItfDataOutput {
    private List<String> data;

    public boolean send(List<String> data) {
      this.data = data;
      return true;
    }
    public List<String> getData(){
      return this.data;
    }
  }

  private class CountedDataInput extends SimpleDataInput {
    private int failInvokeTimes;
    private int count;

    public int getCount() {
      return count;
    }
    public CountedDataInput(String key, List<String> data, int failInvokeTimes) {
      super(key, data);
      this.failInvokeTimes = failInvokeTimes;
    }
    @Override
    public List<String> pollData(String key) {
      if (count < failInvokeTimes){
        count++;
        return Lists.newArrayList();
      }
      count++;
      return super.pollData(key);
    }
  }
  private class CountedDataOutput extends SimpleDataOutput{
    private int failInvokeTimes;
    private int count;

    public int getCount() {
      return count;
    }

    public CountedDataOutput(int failInvokeTimes) {
      super();
      this.failInvokeTimes = failInvokeTimes;
    }

    @Override
    public boolean send(List<String> data) {
      if (count < failInvokeTimes){
        count++;
        return false;
      }
      count++;
      return super.send(data);
    }
  }
}