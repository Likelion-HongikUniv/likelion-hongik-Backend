package Likelion.model.dto;

import Likelion.model.entity.Post;
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

    public PostSimpleDto(Post post, Long likeCount, Long commentCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = new UserSimpleDto(post.getAuthor());
        this.body = post.getBody();
        if (post.getPostImages().isEmpty() == false)
            this.thumbNailUrl = String.valueOf(post.getPostImages().get(0));
        else this.thumbNailUrl = null;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdTime = post.getCreatedTime();
    }
}
