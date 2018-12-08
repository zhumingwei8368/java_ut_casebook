package com.network.socket;

import java.io.IOException;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by David on 2018/2/5.
 */
public class SimpleServerTest {
  private SimpleServer server;
  private final Integer PORT = 9999;

  private SimpleClient client = new SimpleClient();

  @Before
  public void setup() throws Exception {
    Executors.newSingleThreadExecutor()
        .submit(() -> {
          try {
            server = new SimpleServer();
            server.start(PORT);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    Thread.sleep(200);
    client.startConnection("127.0.0.1", PORT);
  }

  @After
  public void tearDown() throws Exception {
    client.stopConnection();
    server.stop();
  }

  @Test
  public void givenClient_whenServerEchosMessage_thenCorrect() throws IOException {
    String preStr = server.SERVER_RESPONSE_PREFIX;

    String resp1 = client.sendMessage("hello");
    String resp2 = client.sendMessage("world");
    String resp3 = client.sendMessage("!");
    String resp4 = client.sendMessage("bye");

    assertEquals(preStr+"hello", resp1);
    assertEquals(preStr+"world", resp2);
    assertEquals(preStr+"!", resp3);
    assertEquals(preStr+"bye", resp4);
  }
}