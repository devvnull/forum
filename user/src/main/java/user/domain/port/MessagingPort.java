package user.domain.port;

public interface MessagingPort {

  void publish(String subject, String message);

  void subscribe(String subject, MessageHandler handler);

  @FunctionalInterface
  interface MessageHandler {
    void handle(String message);
  }
}
