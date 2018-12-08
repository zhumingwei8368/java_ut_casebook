package com.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lenovo on 2018/1/28.
 */
public class DynamicFileReader {
  private Map<String, String> map = new HashMap<String, String>();

  public DynamicFileReader(InputStream inputStream) {
    loadKeyValues(inputStream);
  }

  public DynamicFileReader(File inFile) {
    if (inFile.exists()){
      FileInputStream in = null;
      try {
        in = new FileInputStream(inFile);
        loadKeyValues(in);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }finally {
        if (in != null){
          try {
            in.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private void loadKeyValues(InputStream inputStream) {
    Properties prop = new Properties();
    try {
      prop.load(inputStream);
      for (Object key : prop.keySet()) {
        map.put((String)key, prop.getProperty((String)key));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String get(String key){
    return map.get(key);
  }

  public int getCacheSize() {
    return map.size();
  }
}
