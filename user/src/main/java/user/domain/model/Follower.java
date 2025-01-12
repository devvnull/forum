package user.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Follower {
  private UUID followerId;
  private UUID influencerId;
  private OffsetDateTime createdAt;

  public Follower(UUID followerId, UUID influencerId, OffsetDateTime createdAt) {
    this.followerId = followerId;
    this.influencerId = influencerId;
    this.createdAt = createdAt;
  }

  public UUID getFollowerId() {
    return followerId;
  }

  public void setFollowerId(UUID followerId) {
    this.followerId = followerId;
  }

  public UUID getInfluencerId() {
    return influencerId;
  }

  public void setInfluencerId(UUID influencerId) {
    this.influencerId = influencerId;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Follower follower = (Follower) o;
    return Objects.equals(followerId, follower.followerId)
        && Objects.equals(influencerId, follower.influencerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(followerId, influencerId);
  }
}
