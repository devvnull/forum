package user.domain.port;

import java.util.UUID;

public interface FollowerRepositoryPort {
  void createOrIgnore(UUID followerId, UUID influencerId);

  void delete(UUID followerId, UUID influencerId);

  boolean isFollowing(UUID followerId, UUID influencerId);
}
