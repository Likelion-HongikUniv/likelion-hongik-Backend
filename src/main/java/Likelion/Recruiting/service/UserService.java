package Likelion.Recruiting.service;

import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.model.dto.ProfileDto;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public User findUser(String email){
        return userRepository.findByEmail(email).get();
    }

    public void insertDetail(String email, ProfileDto profileDto) {

        // 해당 유저 찾기
        User user = userRepository.findByEmail(email).get();

        // 유저에 추가정보 넣기
        user = user.profileUpdate(profileDto.getNickname(), profileDto.getMajor(), profileDto.getStudentId(), profileDto.getPart());
        userRepository.save(user);

        System.out.println("profile save 완료");
    }
    public User editProfile(String email, ProfileDto profileDto){
        // 해당 유저 찾기
        User user = userRepository.findByEmail(email).get();

        // 유저에 추가정보 넣기
        user = user.profileUpdate(profileDto.getNickname(), profileDto.getMajor(), profileDto.getStudentId(), profileDto.getPart());
        userRepository.save(user);

        System.out.println("profile edit 완료");
        return user;
    }
    public User validateNickname(String nickname){
        User user = userRepository.findByNickname(nickname);
        System.out.println("user = " + user);

        return user;
    }

    public NavbarDto navProfile(String email){
        // 해당 유저 찾기
        User user = userRepository.findByEmail(email).get();

        return NavbarDto.builder()
                .id(user.getId())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .isJoined(user.isJoind())
                .role(user.getRole())
                .build();
    }
}
