package com.recruit.test.service;

import com.recruit.test.model.User;
import com.recruit.test.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    public User login()
}
