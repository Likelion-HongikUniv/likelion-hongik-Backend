package com.recruit.test.repository;

import com.recruit.test.model.Profile;
import com.recruit.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public Profile findById(int id);
//    public User findByUser();
}
