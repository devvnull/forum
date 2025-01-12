package user.infrastructure;

import io.nats.client.Connection;
import io.nats.client.Nats;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import user.domain.model.User;
import user.domain.port.messaging.UserEventsPort;

@Component
public class NatsUserEventsAdapter implements UserEventsPort {
  private final Connection natsConnection;

  public NatsUserEventsAdapter(@Value("${nats.server}") String natsServerUrl)
      throws IOException, InterruptedException {
    this.natsConnection = Nats.connect(natsServerUrl);
  }

  @Override
  public void publishUserCreatedEvent(User user) {
    natsConnection.publish("user.created", user.toString().getBytes());
  }

  @Override
  public void publishUserFollowedEvent(User follower, User influencer) {
    String message = convertFollowToJson(follower, influencer);
    natsConnection.publish("user.followed", message.getBytes());
  }

  @Override
  public void publishUserUnfollowedEvent(User follower, User influencer) {
    String message = convertUnfollowToJson(follower, influencer);
    natsConnection.publish("users.unfollowed", message.getBytes());
  }

  private String convertFollowToJson(User follower, User influencer) {
    return String.format(
        "{\"followerId\":\"%s\",\"influencerId\":\"%s\"}", follower.getId(), influencer.getId());
  }

  private String convertUnfollowToJson(User follower, User influencer) {
    return String.format(
        "{\"followerId\":\"%s\",\"influencerId\":\"%s\"}", follower.getId(), influencer.getId());
  }
}
