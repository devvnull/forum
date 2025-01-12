package user.domain.port.messaging;

import user.domain.model.User;

public interface UserEventsPort {
  void publishUserCreatedEvent(User user);

  void publishUserFollowedEvent(User follower, User influencer);

  void publishUserUnfollowedEvent(User follower, User influencer);
}
