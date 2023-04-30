package Likelion.Recruiting.domain.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCommentRequestDto {
    private String body;

    public CreateCommentRequestDto(String body) {
        this.body = body;
    }
}
