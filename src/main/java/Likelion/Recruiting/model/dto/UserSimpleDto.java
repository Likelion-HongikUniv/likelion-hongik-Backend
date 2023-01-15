package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.User;

import lombok.Data;

@Data
public class UserSimpleDto {
    private Long id;
    private String nickname;
    private String profileImage;

    public UserSimpleDto(Long id, String nickname, String profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public UserSimpleDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }
}
