package com.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.security.AccessController;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import sun.security.action.GetPropertyAction;

/**
 * Created by David on 2018/1/29.
 */
@RunWith(PowerMockRunner.class) @PrepareForTest({Utils.class}) public class BizStringsWriterTest {
  @Test public void test_write_use_outputstream() throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    BizStringsWriter writer = new BizStringsWriter();
    writer.add("a1").add("c3").add("b2");
    writer.write(output);
    output.close();

    Assert.assertEquals("a1;b2;c3", output.toString());
  }

  @Test public void test_writeToLocalFile() throws Exception {
    File baseDir = mockBaseDir();

    try {
      BizStringsWriter writer = new BizStringsWriter();
      writer.add("a1").add("c3").add("b2");
      writer.writeToLocalFile();

      Assert.assertEquals("a1;b2;c3", readFileContent(new File(baseDir, writer.LOCAL_PATH)));
    } catch (Exception ex) {
      throw ex;
    } finally {
      FileUtils.forceDelete(baseDir);
    }
  }

  @Test public void test_writeToLocalFile_failed_whenLock() throws Exception {
    File baseDir = mockBaseDir();

    BizStringsWriter writer = new BizStringsWriter();
    writer.add("a1"); // make file exist first
    writer.writeToLocalFile();

    writer.add("a1").add("c3").add("b2");
    FileLock lock = writeLock(new File(baseDir, writer.LOCAL_PATH));
    Assert.assertFalse(writer.writeToLocalFile());
    lock.release();
    lock.channel().close();
  }

  private FileLock writeLock(File file) throws IOException {
    return new RandomAccessFile(file, "rw").getChannel().lock();
  }

  private String readFileContent(File file) throws IOException {
    FileInputStream input = null;
    try {
      input = new FileInputStream(file);
      return IOUtils.toString(input);
    } catch (Exception ex) {
    } finally {
      IOUtils.closeQuietly(input);
    }
    return null;
  }

  private File mockBaseDir() throws IOException {
    PowerMockito.mockStatic(Utils.class);
    File baseDir = new File(AccessController.doPrivileged(new GetPropertyAction("java.io.tmpdir")), "etc");

    PowerMockito.when(Utils.getEtcBaseDir()).thenReturn(baseDir);
    return baseDir;
  }
}