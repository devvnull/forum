package user.domain.port;

import java.util.Optional;
import java.util.UUID;
import user.domain.model.User;

public interface UserRepositoryPort {
  User save(User user);

  Optional<User> findById(UUID id);

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);
}
