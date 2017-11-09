package com.beantimer.service;

import com.beantimer.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String userName);

    User addUser(User user);
}
 