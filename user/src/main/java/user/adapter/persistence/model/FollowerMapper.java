package user.adapter.persistence.model;

import user.domain.model.Follower;

public class FollowerMapper {
  public static Follower toDomain(FollowerEntity entity) {
    return new Follower(entity.getFollowerId(), entity.getInfluencerId(), entity.getCreatedAt());
  }

  public static FollowerEntity toEntity(Follower domain) {
    return new FollowerEntity(
        domain.getFollowerId(), domain.getInfluencerId(), domain.getCreatedAt());
  }
}
