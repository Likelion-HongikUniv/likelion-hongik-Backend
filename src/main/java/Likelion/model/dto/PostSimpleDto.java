package Likelion.Recruiting.service.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class PostSimpleDto {
    private Long id;
    private String title;
    private UserSimpleDto author;
    private String body;
    private String thumbNailUrl;
    private Long likeCount;
    private Long commentCount;
    private LocalDateTime createdTime;

    public PostSimpleDto(Long id, String title, UserSimpleDto author, String body, String thumbNailUrl, Long likeCount, Long commentCount, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumbNailUrl = thumbNailUrl;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdTime = createdTime;
    }
}
