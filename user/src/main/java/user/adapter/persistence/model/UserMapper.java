package user.adapter.persistence.model;

import user.domain.model.User;

public class UserMapper {
  public static User toDomain(UserEntity entity) {
    return new User(
        entity.getId(),
        entity.getUsername(),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getPassword(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }

  public static UserEntity toEntity(User user) {
    UserEntity entity = new UserEntity();
    entity.setId(user.getId());
    entity.setUsername(user.getUsername());
    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setPassword(user.getPassword());
    entity.setCreatedAt(user.getCreatedAt());
    entity.setUpdatedAt(user.getUpdatedAt());

    return entity;
  }
}
