package com.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by David on 2018/1/29.
 */
public class BizStringsWriter {
  private List<String> stringLst = new ArrayList<>();
  protected final String LOCAL_PATH = "out/bizString.txt";

  public BizStringsWriter add(String str){
    stringLst.add(str);
    return this;
  }

  public void write(OutputStream outputStream) throws IOException {
    processData();
    writeToOutStream(outputStream);
  }

  public boolean writeToLocalFile() {
    processData();

    File localFile = new File(Utils.getEtcBaseDir(), LOCAL_PATH);
    FileOutputStream outputStream = null;
    try {
      if (!localFile.getParentFile().exists()){
        localFile.getParentFile().mkdirs();
      }

      outputStream = new FileOutputStream(localFile);
      writeToOutStream(outputStream);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }finally {
      if (outputStream != null){
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return localFile.exists();
  }

  private void processData() {
    //do some business operation...
    Collections.sort(stringLst);
  }

  private void writeToOutStream(OutputStream outputStream) throws IOException {
    for (int i = 0; i < stringLst.size(); i++) {
      if (i > 0){
        outputStream.write(';');
      }
      outputStream.write(stringLst.get(i).getBytes());
      outputStream.flush();
    }
  }
}
