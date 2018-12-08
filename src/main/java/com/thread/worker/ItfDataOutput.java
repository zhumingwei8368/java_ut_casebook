package com.thread.worker;

import java.util.List;

/**
 * Created by David on 2018/1/26.
 */
public interface ItfDataOutput {
  boolean send(List<String> data);
}
