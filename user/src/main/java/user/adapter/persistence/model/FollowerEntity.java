package user.adapter.persistence.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;

@Entity
@IdClass(FollowerPrimaryKey.class)
@Table(name = "followers")
public class FollowerEntity {
  @Id
  @Column(name = "follower_id", columnDefinition = "UUID")
  private UUID followerId;

  @Id
  @Column(name = "influencer_id", columnDefinition = "UUID")
  private UUID influencerId;

  @CreatedDate
  @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
  private OffsetDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "follower_id", nullable = false)
  private UserEntity follower;

  @ManyToOne
  @JoinColumn(name = "influencer_id", nullable = false)
  private UserEntity influencer;

  public FollowerEntity(UUID followerId, UUID influencerId, OffsetDateTime createdAt) {
    this.followerId = followerId;
    this.influencerId = influencerId;
    this.createdAt = createdAt;
  }

  public FollowerEntity() {}

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
}
