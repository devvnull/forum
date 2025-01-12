package user.adapter.messaging;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import user.domain.port.MessagingPort;

public class NatsClient implements MessagingPort {
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

  @Override
  public void publish(String subject, String message) {
    try {
      nc.publish(subject, message.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new RuntimeException("Failed to publish message to NATS", e);
    }
  }

  @Override
  public void subscribe(String subject, MessageHandler handler) {
    try {
      Dispatcher dispatcher =
          nc.createDispatcher(
              msg -> {
                String message = new String(msg.getData(), StandardCharsets.UTF_8);
                handler.handle(message);
              });
      dispatcher.subscribe(subject);
    } catch (Exception e) {
      throw new RuntimeException("Failed to subscribe to NATS subject", e);
    }
  }

  public void listen() {
    Dispatcher dispatcher =
        nc.createDispatcher(
            (msg) -> {
              String request = new String(msg.getData(), StandardCharsets.UTF_8);
              String reply = "Processed: " + request;
              nc.publish(msg.getReplyTo(), reply.getBytes(StandardCharsets.UTF_8));
            });

    dispatcher.subscribe(USER_CREATE);
  }

  /*public void boo() {
    byte[] messageBytes = "hello".getBytes(StandardCharsets.UTF_8);
    nc.publish("greet.joe", messageBytes);
  }*/
}
