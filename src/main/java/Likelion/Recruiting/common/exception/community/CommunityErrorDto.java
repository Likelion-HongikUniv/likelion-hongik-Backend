package Likelion.Recruiting.common.exception.community;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommunityErrorDto {
    private int code;
    private String message;

    @Builder
    public CommunityErrorDto(CommunityException communityException) {
        this.code = communityException.getCode();
        this.message = communityException.getMessage();
    }
}
