package user.adapter.persistence.repository;

import java.util.UUID;
import org.springframework.stereotype.Component;
import user.domain.port.FollowerRepositoryPort;

@Component
class FollowerRepositoryAdapter implements FollowerRepositoryPort {
  private final JpaFollowerRepository jpaFollowerRepository;

  public FollowerRepositoryAdapter(JpaFollowerRepository jpaFollowerRepository) {
    this.jpaFollowerRepository = jpaFollowerRepository;
  }

  @Override
  public void createOrIgnore(UUID followerId, UUID influencerId) {
    jpaFollowerRepository.createOrIgnore(followerId, influencerId);
  }

  @Override
  public void delete(UUID followerId, UUID influencerId) {
    jpaFollowerRepository.delete(followerId, influencerId);
  }

  @Override
  public boolean isFollowing(UUID followerId, UUID influencerId) {
    return jpaFollowerRepository.isFollowing(followerId, influencerId);
  }
}
