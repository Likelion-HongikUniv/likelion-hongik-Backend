package Likelion.Recruiting.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NavbarDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("profileImage")
    private String profileImage;

    @Builder
    public NavbarDto(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }
}
