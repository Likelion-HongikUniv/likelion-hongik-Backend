package Likelion.service;

import Likelion.model.User;
import com.recruit.test.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUser(String email){
        return userRepository.findByEmail(email).get();
    }
}
