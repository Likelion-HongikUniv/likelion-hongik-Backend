package Likelion.Recruiting.domain.user.dto;

import Likelion.Recruiting.domain.user.entity.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
public class UserAllDto {

    private Long userId;
    private String username;
    private String nickname;
    private String profileImageSrc;
    private Role role;
    private String major;
    private String part;
    private String studentId;
    private String team;

    @Builder
    public UserAllDto(Long userId, String username, String nickname, String profileImageSrc, Role role, String major, String part, String studentId, String team) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.profileImageSrc = profileImageSrc;
        this.role = role;
        this.major = major;
        this.part = part;
        this.studentId = studentId;
        this.team = team;
    }
}
