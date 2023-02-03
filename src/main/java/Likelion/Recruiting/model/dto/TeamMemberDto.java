package Likelion.Recruiting.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TeamMemberDto {
    private Long userId;
    private String userName;
    private String profileImage;

    @Builder
    public TeamMemberDto(Long userId, String userName, String profileImage) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
    }
}

