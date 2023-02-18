package Likelion.Recruiting.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MembeSimpleDto {
    private Long userId;
    private String userName;
    private String profileImage;

    @Builder
    public MembeSimpleDto(Long userId, String userName, String profileImage) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
    }
}

