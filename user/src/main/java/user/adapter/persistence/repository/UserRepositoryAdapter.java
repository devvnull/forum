package user.adapter.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import user.adapter.persistence.model.UserEntity;
import user.adapter.persistence.model.UserMapper;
import user.domain.model.User;
import user.domain.port.UserRepositoryPort;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
  private final JpaUserRepository jpaUserRepository;

  public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
    this.jpaUserRepository = jpaUserRepository;
  }

  @Override
  public User save(User user) {
    UserEntity entity = UserMapper.toEntity(user);
    UserEntity savedEntity = jpaUserRepository.save(entity);
    return UserMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return jpaUserRepository.findById(id).map(UserMapper::toDomain);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return jpaUserRepository.findByUsername(username).map(UserMapper::toDomain);
  }

  @Override
  public boolean existsByUsername(String username) {
    return jpaUserRepository.findByUsername(username).isPresent();
  }
}
