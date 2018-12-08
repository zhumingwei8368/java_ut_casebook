package com.thread.worker;

/**
 * Created by David on 2018/1/26.
 */
public class ThreadWorker implements Runnable {
  @Override
  public void run() {
    AClass.register();
    new BClass().active();
  }
}
