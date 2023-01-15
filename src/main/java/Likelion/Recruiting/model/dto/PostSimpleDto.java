package Likelion.Recruiting.model.dto;


import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
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

    public PostSimpleDto(Post post, User user, Long likeCount, Long commentCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = new UserSimpleDto(post.getAuthor().getId(),post.getAuthor().getNickname(),post.getAuthor().getProfileImage(), user);
        this.body = post.getBody();
        if (post.getPostImages().isEmpty() == false)
            this.thumbNailUrl = String.valueOf(post.getPostImages().get(0));
        else this.thumbNailUrl = null;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdTime = post.getCreatedTime();
    }
}
