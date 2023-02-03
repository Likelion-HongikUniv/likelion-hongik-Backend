package Likelion.Recruiting.service.admin;

import Likelion.Recruiting.model.User;
import Likelion.Recruiting.repository.admin.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 정보 수정
    @Transactional
    public void updateUser(
            Long userId,
            String name,
            String email,
            String major,
            String nickname,
            String part,
            String studentId) {
        User findUser = userRepository.findOne(userId);
        findUser.setName(name);
        findUser.setEmail(email);
        findUser.setMajor(major);
        findUser.setNickname(nickname);
        findUser.setPart(part);
        findUser.setStudentId(studentId);
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long userId) {

        User findUser = userRepository.findOne(userId);

        userRepository.deleteUser(findUser);
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    // 회원 1명 조회
    @Transactional(readOnly = true)
    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }
}
