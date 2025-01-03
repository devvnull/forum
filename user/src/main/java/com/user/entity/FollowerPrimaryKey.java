package com.user.entity;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FollowerPrimaryKey implements Serializable {
  private String influencerId;
  private String followerId;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof FollowerPrimaryKey followerPrimaryKey)) return false;
    return Objects.equals(influencerId, followerPrimaryKey.influencerId)
        && Objects.equals(followerId, followerPrimaryKey.followerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(influencerId, followerId);
  }
}
