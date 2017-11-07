package com.beantimer.repo;

import com.beantimer.entity.User;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface UserRepository extends AbstractIdEntityRepository<User> {
}
