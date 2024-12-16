package com.user.service;

import com.user.entity.User;
import com.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(String username, String firstName, String lastName, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, firstName, lastName, hashedPassword);
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return this.userRepository.findByUsername(username) != null;
    }
}
