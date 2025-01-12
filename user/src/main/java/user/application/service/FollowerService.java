package user.application.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import user.domain.model.User;
import user.domain.port.FollowerRepositoryPort;
import user.domain.port.UserRepositoryPort;
import user.domain.port.messaging.UserEventsPort;

@Service
public class FollowerService {
  private final FollowerRepositoryPort followerRepositoryPort;
  private final UserRepositoryPort userRepositoryPort;
  private final UserEventsPort userEventsPort;

  public FollowerService(
      FollowerRepositoryPort followerRepositoryPort,
      UserRepositoryPort userRepositoryPort,
      UserEventsPort userEventsPort) {
    this.followerRepositoryPort = followerRepositoryPort;
    this.userRepositoryPort = userRepositoryPort;
    this.userEventsPort = userEventsPort;
  }

  public void follow(UUID followerId, UUID influencerId) {
    followerRepositoryPort.createOrIgnore(followerId, influencerId);
    User follower = userRepositoryPort.findById(followerId).orElseThrow();
    User influencer = userRepositoryPort.findById(influencerId).orElseThrow();
    userEventsPort.publishUserFollowedEvent(follower, influencer);
  }

  public void unfollow(UUID followerId, UUID influencerId) {
    followerRepositoryPort.delete(followerId, influencerId);
    User follower = userRepositoryPort.findById(followerId).orElseThrow();
    User influencer = userRepositoryPort.findById(influencerId).orElseThrow();
    userEventsPort.publishUserUnfollowedEvent(follower, influencer);
  }

  public boolean isFollowing(UUID followerId, UUID influencerId) {
    return followerRepositoryPort.isFollowing(followerId, influencerId);
  }
}
