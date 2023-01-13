package com.recruit.test.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recruit.test.model.Profile;
import com.recruit.test.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileDto {

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("major")
    private String major;

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("part")
    private String part;

    @JsonProperty("phoneNum")
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
