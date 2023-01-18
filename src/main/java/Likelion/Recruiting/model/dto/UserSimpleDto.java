package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.User;

import lombok.Builder;
import lombok.Data;

@Data
public class UserSimpleDto {
    private Long id;
    private String nickname;
    private String profileImage;
    private Boolean isAuthor;

    public UserSimpleDto(Long id, String nickname, String profileImage,User user) {
        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
        if(user.getId() == this.id)
            this.isAuthor = true;
        else this.isAuthor = false;
    }



}
