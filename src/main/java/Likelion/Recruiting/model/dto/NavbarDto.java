package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class NavbarDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("profileImage")
    private String profileImage;

    @JsonProperty("isJoined")
    private boolean isJoined;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("JWT")
    private String JWT;

    @Builder
    public NavbarDto(Long id, String name, String profileImage, boolean isJoined, Role role, String JWT) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.isJoined = isJoined;
        this.role = role;
        this.JWT = JWT;
    }
}
