package com.user.service.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NatsClient {
  private final Connection nc;

  private static final String NATS_SERVER = "nats://localhost:4222";
  private static final String USER_CREATE = "user.create";

  public NatsClient() throws RuntimeException {
    try {
      nc = Nats.connect(NATS_SERVER);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void listen() {
    Dispatcher dispatcher =
        nc.createDispatcher(
            (msg) -> {
              String request = new String(msg.getData(), StandardCharsets.UTF_8);
              System.out.println("Received: " + request);

              String reply = "Processed: " + request;
              nc.publish(msg.getReplyTo(), reply.getBytes(StandardCharsets.UTF_8));
            });

    dispatcher.subscribe(USER_CREATE);
  }

  public void boo() {
    byte[] messageBytes = "hello".getBytes(StandardCharsets.UTF_8);
    nc.publish("greet.joe", messageBytes);
  }
}
