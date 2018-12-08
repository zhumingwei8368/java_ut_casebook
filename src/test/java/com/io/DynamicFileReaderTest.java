package com.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by David on 2018/1/29.
 */
public class DynamicFileReaderTest {
  @Test public void test_get_use_inputstream() throws Exception {
    StringBuffer buf = new StringBuffer();
    buf.append("key1=value1\n").append("key2=value2\n");
    InputStream input = new ByteArrayInputStream(buf.toString().getBytes());

    DynamicFileReader reader = new DynamicFileReader(input);

    Assert.assertEquals(2, reader.getCacheSize());
    Assert.assertEquals("value1", reader.get("key1"));
    Assert.assertEquals("value2", reader.get("key2"));
    Assert.assertNull(reader.get("key3"));
  }

  @Test public void test_get_use_file() throws Exception {
    File in = writeTemporaryFile(new String[][]{{"key1","value1"},{"key2","value2"}});
    try {
      DynamicFileReader reader = new DynamicFileReader(in);

      Assert.assertEquals(2, reader.getCacheSize());
      Assert.assertEquals("value1", reader.get("key1"));
      Assert.assertEquals("value2", reader.get("key2"));
      Assert.assertNull(reader.get("key3"));
    }catch (Exception ex){
      throw ex;
    }finally {
      in.delete();
    }
  }

  private File writeTemporaryFile(String[][] lines) throws IOException {
    File f = File.createTempFile( String.valueOf(System.currentTimeMillis()), ".properties");
    Properties p = new Properties();
    for (String[] line : lines) {
      p.setProperty(line[0], line[1]);
    }
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
    p.store(writer, "ut temporary file");
    writer.close();
    return f;
  }

  @Test public void test_get_use_file_notexist() throws Exception {
    DynamicFileReader reader = new DynamicFileReader(new File("./opt/notExistCfg.properties"));
    Assert.assertEquals(0, reader.getCacheSize());
    Assert.assertNull(reader.get("key1"));
  }
}