package Likelion.Recruiting.admin.service;

import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.user.entity.enums.Role;
import Likelion.Recruiting.admin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    // 회원 정보 수정
    @Transactional
    public void updateUser(
            Long userId,
            String username,
            String nickname,
            String major,
            String part,
            Role role,
            String studentId) {

        User findUser = adminUserRepository.findOne(userId);
        findUser.setName(username);
        findUser.setNickname(nickname);
        findUser.setMajor(major);
        findUser.setPart(part);
        findUser.setRole(role);
        findUser.setStudentId(studentId);
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long userId) {

        User findUser = adminUserRepository.findOne(userId);

        adminUserRepository.deleteUser(findUser);
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<User> findUsers() {
        return adminUserRepository.findAll();
    }

    // 회원 1명 조회
    @Transactional(readOnly = true)
    public User findOne(Long userId) {
        return adminUserRepository.findOne(userId);
    }
}
