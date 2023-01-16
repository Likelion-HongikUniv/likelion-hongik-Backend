package Likelion.model.dto;

import lombok.Data;

@Data
public class UserSimpleDto {
    private Long user_id;
    private String nickname;
    private String profileImage;

    public UserSimpleDto(Long user_id, String nickname, String profileImage) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
