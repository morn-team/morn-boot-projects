package site.morn.boot.websocket;

import site.morn.boot.websocket.client.TestStompClient;

/**
 * site.morn.boot.websocket
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/26
 */
public class StompClientTest {

  public static void main(String[] args) {
    TestStompClient client = new TestStompClient();
    client.startup();
  }
}
