package com.beantimer.repo.dao;

import com.beantimer.entity.User;

import java.util.Optional;

public interface UserDAO {

    void addUser(User user);

    Optional<User> findByUsername(String userName);
}
 