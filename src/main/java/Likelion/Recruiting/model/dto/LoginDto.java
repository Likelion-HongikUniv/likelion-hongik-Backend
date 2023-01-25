package Likelion.Recruiting.model.dto;

import lombok.Builder;

public class LoginDto {

    private Long user_id;
    private String name;
    private String profileImage;

    private String jwt;

    @Builder
    public LoginDto(Long user_id, String name, String profileImage, String jwt) {
        this.user_id = user_id;
        this.name = name;
        this.profileImage = profileImage;
        this.jwt = jwt;
    }
}
