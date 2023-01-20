package Likelion.Recruiting.service;

import Likelion.Recruiting.model.Users;
import Likelion.Recruiting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUser(Long userId, String name, String email) {
        Users findUser = userRepository.findOne(userId);
        findUser.setName(name);
        findUser.setEmail(email);
    }

    @Transactional
    public void deleteUser(Long userId) {

        Users findUser = userRepository.findOne(userId);

        userRepository.deleteUser(findUser);
    }


    // 회원 전체 조회
    @Transactional(readOnly = true) // 읽기에 readOnly=ture 해주면 최적화 해준대. 쓰기에는 넣으면 안됨
    public List<Users> findUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Users findOne(Long userId) {
        return userRepository.findOne(userId);
    }
}
