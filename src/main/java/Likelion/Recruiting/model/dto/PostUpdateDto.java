package Likelion.Recruiting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateDto {
    private String title;
    private String body;
    private String thumbnailImage;

}
