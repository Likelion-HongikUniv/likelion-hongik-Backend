package Likelion.Recruiting.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePostResponseDto {
    private Long id;
    private String message;

    public CreatePostResponseDto(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}