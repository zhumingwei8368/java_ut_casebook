package com.thread.concurrent;

/**
 * Created by David on 2018/2/3.
 */
public class Counter {
  private int count=0;

  public void addOne(){
    count++;
  }

  public int getCount(){
    return count;
  }
}
