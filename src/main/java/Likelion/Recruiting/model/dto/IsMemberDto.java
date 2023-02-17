package Likelion.Recruiting.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import Likelion.Recruiting.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
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
