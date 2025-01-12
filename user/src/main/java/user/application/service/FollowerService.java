package user.application.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import user.domain.port.FollowerRepositoryPort;

@Service
public class FollowerService {
  private final FollowerRepositoryPort followerRepositoryPort;

  public FollowerService(FollowerRepositoryPort followerRepositoryPort) {
    this.followerRepositoryPort = followerRepositoryPort;
  }

  public void follow(UUID followerId, UUID influencerId) {
    followerRepositoryPort.createOrIgnore(followerId, influencerId);
  }

  public void unfollow(UUID followerId, UUID influencerId) {
    followerRepositoryPort.delete(followerId, influencerId);
  }

  public boolean isFollowing(UUID followerId, UUID influencerId) {
    return followerRepositoryPort.isFollowing(followerId, influencerId);
  }
}
