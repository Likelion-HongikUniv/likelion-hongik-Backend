<<<<<<<< HEAD:src/main/java/com/recruit/test/model/repository/UserRepository.java
package Likelion.model.repository;
========
package com.recruit.test.model;
>>>>>>>> jimin:src/main/java/com/recruit/test/model/UserRepository.java

import Likelion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    public User findByUsername(String username);
}
