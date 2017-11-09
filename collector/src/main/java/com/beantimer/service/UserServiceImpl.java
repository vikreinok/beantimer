package com.beantimer.service;

import com.beantimer.entity.User;
import com.beantimer.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
