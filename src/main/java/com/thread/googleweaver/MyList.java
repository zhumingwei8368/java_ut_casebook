package com.thread.googleweaver;

import java.util.ArrayList;

/**
 * Created by David on 2018/2/3.
 */
public class MyList extends ArrayList {
  public boolean putIfAbsent(Object o) {
    boolean absent = !super.contains(o);
    if (absent) {
      super.add(o);
    } return absent;
  }
}
