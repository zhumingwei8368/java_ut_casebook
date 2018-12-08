package com.network.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by David on 2018/2/5.
 */
public class SimpleServer {
  protected final String SERVER_RESPONSE_PREFIX = "server has received msg:";
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private boolean stopFlag = false;

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port, 50, InetAddress.getByAddress(new byte[] {0x7f,0x00,0x00,0x01}));
    System.out.println("Started server on port " + port);

    stopFlag = false;
    while (!stopFlag) {
      clientSocket = serverSocket.accept();
      System.out.println("Accepted connection from client: "  + clientSocket.getRemoteSocketAddress() );

      BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

      String msg;
      while ((msg = in.readLine()) != null) {
        if ("bye".equalsIgnoreCase(msg)){
          out.println(genReceivedMsgToClient(msg));
          break;
        }
        out.println(genReceivedMsgToClient(msg));
      }

      System.out.println("Closing connection with client: " + clientSocket.getInetAddress());
      out.close();
      in.close();
      clientSocket.close();
    }
  }

  private String genReceivedMsgToClient(String msg) {
    return SERVER_RESPONSE_PREFIX + msg;
  }

  public void stop() throws IOException {
    stopFlag = true;
    if (clientSocket != null){
      clientSocket.close();
    }
    if (serverSocket != null){
      serverSocket.close();
    }
  }
}