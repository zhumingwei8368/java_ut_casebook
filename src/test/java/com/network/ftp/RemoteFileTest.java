package com.network.ftp;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import static org.junit.Assert.assertEquals;

/**
 * Created by David on 2018/2/5.
 */
public class RemoteFileTest {
  private static final String HOME_DIR = "/";
  private static final String FILE = "/dir/sample.txt";
  private static final String CONTENTS = "abcdef 1234567890";

  private RemoteFile remoteFile;
  private FakeFtpServer fakeFtpServer;

  @Before
  public void setUp() throws Exception {
    fakeFtpServer = new FakeFtpServer();
    fakeFtpServer.setServerControlPort(21);

    FileSystem fileSystem = new UnixFakeFileSystem();
    fileSystem.add(new FileEntry(FILE, CONTENTS));
    fakeFtpServer.setFileSystem(fileSystem);

    UserAccount userAccount = new UserAccount(RemoteFile.USERNAME, RemoteFile.PASSWORD, HOME_DIR);
    fakeFtpServer.addUserAccount(userAccount);

    fakeFtpServer.start();
    remoteFile = new RemoteFile("127.0.0.1", fakeFtpServer.getServerControlPort());
  }

  @After
  public void tearDown() throws Exception {
    fakeFtpServer.stop();
  }

  @Test
  public void testReadFile() throws Exception {
    String contents = remoteFile.readFile(FILE);
    assertEquals(CONTENTS, contents);
  }

  @Test(expected = IOException.class)
  public void testReadFileThrowsException() throws IOException {
    remoteFile.readFile("NoSuchFile.txt");
  }
}