package com.beantimer.repo;

import com.beantimer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface UserRepository  extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

}
