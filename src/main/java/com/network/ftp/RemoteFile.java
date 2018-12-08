package com.network.ftp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by David on 2018/2/5.
 */
public class RemoteFile {
  protected static final String USERNAME = "ftpuser";
  protected static final String PASSWORD = "ftpuser";

  private String server;
  private int port;

  public RemoteFile(String server, int port) {
    this.server = server;
    this.port = port;
  }

  public String readFile(String filename) throws IOException {
    FTPClient ftpClient = new FTPClient();
    ftpClient.connect(server, port);
    ftpClient.login(USERNAME, PASSWORD);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    boolean success = ftpClient.retrieveFile(filename, outputStream);
    ftpClient.disconnect();

    if (!success) {
      throw new IOException("Retrieve file failed: " + filename);
    }
    return outputStream.toString();
  }

}
