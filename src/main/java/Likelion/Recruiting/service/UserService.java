package Likelion.Recruiting.service;

import Likelion.Recruiting.model.dto.ProfileDto;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUser(String email){
        return userRepository.findByEmail(email).get();
    }

    public void insertDetail(String email, ProfileDto profileDto) {

        // 해당 유저 찾기
        User user = userRepository.findByEmail(email).get();

        // 유저에 추가정보 넣기
        user = user.profileUpdate(profileDto.getNickname(), profileDto.getMajor(), profileDto.getStudentId(), profileDto.getPart(), profileDto.getPhoneNum());
        System.out.println("user.getMajor() = " + user.getMajor());
        userRepository.save(user);

        System.out.println("profile save 완료");
    }
}
