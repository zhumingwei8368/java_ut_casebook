package com.thread.worker;

import java.util.List;

/**
 * Created by David on 2018/1/26.
 */
public interface ItfDataInput {
  List<String> pollData(String key);

  String getKey();
}
