package Likelion.Recruiting.service;

import Likelion.Recruiting.config.auth.jwt.JwtTokenProvider;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.UserException;
import Likelion.Recruiting.model.Team;
import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.model.dto.ProfileDto;
import Likelion.Recruiting.model.dto.TeamMemberResponseDto;
import Likelion.Recruiting.model.dto.UserAllDto;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.repository.TeamRepository;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public User findUser(String email){
        return userRepository.findByEmail(email).get();
    }

    @Transactional
    public void insertDetail(String email, ProfileDto profileDto) {

        // 해당 유저 찾기
        User user = userRepository.findByEmail(email).get();

        // 유저에 추가정보 넣기
        user = user.profileUpdate(profileDto.getNickname(), profileDto.getMajor(), profileDto.getStudentId(), profileDto.getPart());
        userRepository.save(user);

        System.out.println("profile save 완료");
    }

    @Transactional
    public User editProfile(String email, ProfileDto profileDto){
        // 해당 유저 찾기
        User user = userRepository.findByEmail(email).get();

        // 유저에 추가정보 넣기
        user = user.profileUpdate(profileDto.getNickname(), profileDto.getMajor(), profileDto.getStudentId(), profileDto.getPart());
        userRepository.save(user);

        return user;
    }
    public User validateNickname(String nickname){
        User user = userRepository.findByNickname(nickname);

        return user;
    }

    @Transactional
    public User editProfileImage(String email, String profileUrl){
        // 해당 유저 찾기
        User user = userRepository.findByEmail(email).get();

        user.update(user.getName(),profileUrl);
        return user;
    }

    public NavbarDto takeJwt(Long uid){
        // 해당 유저 찾기
        User user = userRepository.findById(uid).get();

        String email = user.getEmail();
        Role role = user.getRole();

        // JWT 만들기
        String token = jwtTokenProvider.createToken(email, role);

        return NavbarDto.builder()
                .id(user.getId())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .isJoined(user.isJoind())
                .role(user.getRole())
                .JWT(token)
                .build();
    }

    public TeamMemberResponseDto getTeamMembers(Long teamId){
        List<User> teamMembers = userRepository.findAllByTeamId(teamId);
        Team team = teamRepository.findById(teamId).get();
        return TeamMemberResponseDto.builder()
                .teamId(teamId)
                .teamName(team.getName())
                .memberCount((long)teamMembers.size())
                .users(teamMembers)
                .build();
    }

    public UserAllDto getAllAboutUser(String email){
        User user = userRepository.findByEmail(email).get();

        if (user.isJoind() == true && user.getRole() == Role.USER){ // 추가 정보 받은 멋사회원이라면
            if(user.getTeam() != null) {
                return UserAllDto.builder()
                        .userId(user.getId())
                        .username(user.getName())
                        .nickname(user.getNickname())
                        .profileImageSrc(user.getProfileImage())
                        .role(user.getRole())
                        .major(user.getMajor())
                        .part(user.getPart())
                        .studentId(user.getStudentId())
                        .team(user.getTeam().getName())
                        .build();
            }
            else {
                return UserAllDto.builder()
                        .userId(user.getId())
                        .username(user.getName())
                        .nickname(user.getNickname())
                        .profileImageSrc(user.getProfileImage())
                        .role(user.getRole())
                        .major(user.getMajor())
                        .part(user.getPart())
                        .studentId(user.getStudentId())
                        .team(null)
                        .build();
            }
        }
        else if(user.isJoind() == false && user.getRole() == Role.USER){
            System.out.println("false user");
            throw new UserException(ErrorCode.NO_DATA_USER.getErrorCode(), ErrorCode.NO_DATA_USER.getErrorMessage());

        }
        else{
            System.out.println("false guest");
            throw new UserException(ErrorCode.NOT_USER.getErrorCode(), ErrorCode.NOT_USER.getErrorMessage());
        }

    }
}
