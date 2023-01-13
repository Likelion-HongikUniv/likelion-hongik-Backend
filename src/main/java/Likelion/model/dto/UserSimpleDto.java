package Likelion.model.dto;

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
}
