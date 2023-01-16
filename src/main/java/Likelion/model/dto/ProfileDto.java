package Likelion.model.dto;

import Likelion.model.entity.Profile;
import Likelion.model.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileDto {

    private String nickname;
    private String major;
    private String studentId;
    private String part;
    private String phoneNum;

    @Builder
    public ProfileDto(String nickname, String major, String studentId, String part, String phoneNum) {
        this.nickname = nickname;
        this.major = major;
        this.studentId = studentId;
        this.part = part;
        this.phoneNum = phoneNum;
    }

    public Profile toEntity(User user){
        Profile profile = Profile.builder()
                .user(user)
                .nickname(nickname)
                .major(major)
                .studentId(studentId)
                .part(part)
                .phoneNum(phoneNum)
                .build();

        return profile;
    }
}
