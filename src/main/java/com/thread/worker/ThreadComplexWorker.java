package com.thread.worker;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Created by David on 2018/1/26.
 */
public class ThreadComplexWorker implements Runnable {
  private ItfDataInput input;
  private ItfDataOutput output;

  public ThreadComplexWorker(ItfDataInput input, ItfDataOutput output) {
    this.input = input;
    this.output = output;
  }

  @Override
  public void run() {
    boolean sendFlag = false;
    String key = input.getKey();
    List<String> data = input.pollData(key);

    while (!sendFlag) {
      if (data.isEmpty()) {
        data = input.pollData(key);
      } else {
        List<String> processedData = process(data);

        sendFlag = output.send(processedData);
        if (sendFlag) {
          DataRecorder.record(key, processedData.size());
        } else {
          DataRecorder.recordFail(key);
        }
      }

      sleep(1000 * 10);
    }
  }

  private void sleep(long sleepMillions) {
    try {
      Thread.sleep(sleepMillions);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private List<String> process(List<String> data) {
    return Lists.reverse(data);
  }
}
