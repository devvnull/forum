package com.user.service;

import com.user.repository.FollowerRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerService {
  @Autowired private FollowerRepository followerRepository;

  public void follow(UUID followerId, UUID influencerId) throws IllegalArgumentException {
    this.followerRepository.createOrIgnore(followerId, influencerId);
  }

  public void unfollow(UUID followerId, UUID followedId) {
    this.followerRepository.delete(followerId, followedId);
  }

  public boolean isFollowing(UUID followerId, UUID influencerId) {
    return this.followerRepository.isFollowing(followerId, influencerId);
  }
}
