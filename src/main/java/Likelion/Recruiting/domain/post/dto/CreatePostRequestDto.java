package Likelion.Recruiting.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePostRequestDto {
    private String title;
    private String body;
    private String thumbnailImage;

    public CreatePostRequestDto(String title, String body,String thumbnailImage) {
        this.title = title;
        this.body = body;
        this.thumbnailImage = thumbnailImage;
    }
}