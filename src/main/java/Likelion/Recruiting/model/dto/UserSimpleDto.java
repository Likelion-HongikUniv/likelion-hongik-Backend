package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.User;

import lombok.Builder;
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
        if(user.getId() == this.authorId)
            this.isAuthor = true;
        else this.isAuthor = false;
    }



}
