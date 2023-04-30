package Likelion.Recruiting.domain.user.dto;

import Likelion.Recruiting.domain.user.entity.User;

import lombok.Data;

@Data
public class UserSimpleDto {
    private Long authorId;
    private String nickname;
    private String profileImage;
    private Boolean isAuthor;

    public UserSimpleDto(Long id, String nickname, String profileImage,User user) {
        this.authorId = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
        if(user.getId().equals(this.authorId)) {
            this.isAuthor = true;
        }
        else this.isAuthor = false;
    }


}
