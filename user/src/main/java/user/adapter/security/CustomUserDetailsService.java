package user.adapter.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import user.domain.model.User;
import user.domain.port.UserRepositoryPort;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepositoryPort userRepository;

  public CustomUserDetailsService(UserRepositoryPort userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

    return new CustomUserDetails(user);
  }
}
