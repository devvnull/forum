package com.user.repository;

import com.user.entity.Follower;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FollowerRepository extends JpaRepository<Follower, String> {
  String CREATE_OR_IGNORE =
      "INSERT INTO followers (follower_id, influencer_id) "
          + "VALUES (:followerId, :influencerId)"
          + "ON CONFLICT (follower_id, influencer_id) DO NOTHING";

  String DELETE_FOLLOWER =
      "DELETE FROM followers WHERE follower_id = :followerId AND influencer_id = :influencerId";

  String IS_FOLLOWING =
      "SELECT COUNT(f.follower_id) > 0 FROM followers f WHERE f.follower_id = :followerId AND f.influencer_id = :influencerId";

  @Modifying
  @Transactional
  @Query(value = CREATE_OR_IGNORE, nativeQuery = true)
  void createOrIgnore(UUID followerId, UUID influencerId);

  @Modifying
  @Transactional
  @Query(value = DELETE_FOLLOWER, nativeQuery = true)
  void delete(UUID followerId, UUID influencerId);

  @Query(value = IS_FOLLOWING, nativeQuery = true)
  boolean isFollowing(UUID followerId, UUID influencerId);
}
