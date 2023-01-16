package Likelion.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class PostSimpleDto {
    private Long id;
    private String title;
    private UserSimpleDto user_id;
    private String body;
    private String thumbNailUrl;
    private Long likeCount;
    private Long commentCount;
    private LocalDateTime createdTime;

    public PostSimpleDto(Long id, String title, UserSimpleDto user_id, String body, String thumbNailUrl, Long likeCount, Long commentCount, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.user_id = user_id;
        this.body = body;
        this.thumbNailUrl = thumbNailUrl;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdTime = createdTime;
    }
}
