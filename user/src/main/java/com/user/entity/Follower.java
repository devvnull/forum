package com.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Entity
@Data
@IdClass(FollowerPrimaryKey.class)
@Table(name = "followers")
public class Follower {
  @Id
  @Column(name = "follower_id", columnDefinition = "UUID")
  private UUID followerId;

  @Id
  @Column(name = "influencer_id", columnDefinition = "UUID")
  private UUID influencerId;

  @CreatedDate
  @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "follower_id", nullable = false)
  private User follower;

  @ManyToOne
  @JoinColumn(name = "influencer_id", nullable = false)
  private User influencer;
}
