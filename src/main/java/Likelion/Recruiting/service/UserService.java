package Likelion.Recruiting.service;

import Likelion.Recruiting.domain.User;
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

    // 회원 전체 조회
    @Transactional(readOnly = true) // 읽기에 readOnly=ture 해주면 최적화 해준대. 쓰기에는 넣으면 안됨
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findOne(Long memberId) {
        return userRepository.findOne(memberId);
    }
}
