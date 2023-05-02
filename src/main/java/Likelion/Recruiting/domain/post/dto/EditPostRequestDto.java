package Likelion.Recruiting.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditPostRequestDto {
    private String title;
    private String body;
    private String thumbnailImage;

    public EditPostRequestDto(String body) {
        this.body = body;
    }
}