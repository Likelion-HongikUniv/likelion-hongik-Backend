package Likelion.Recruiting.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import Likelion.Recruiting.model.Role;

public class IsMemberDto {

    @JsonProperty("isJoined")
    private boolean isJoined;

    @JsonProperty("role")
    private Role role;

    public IsMemberDto(boolean isJoined, Role role) {
        this.isJoined = isJoined;
        this.role = role;
    }
}
