package com.mosesidowu.expenseSecurity.data.repository;


import com.mosesidowu.expenseSecurity.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findUserByPassword(String password);
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);
}
