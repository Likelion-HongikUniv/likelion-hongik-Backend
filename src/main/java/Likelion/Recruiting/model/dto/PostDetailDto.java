package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDetailDto {
    private Long id;
    private UserSimpleDto author;
    private String title;
    private String body;
    private List<String> ImageUrls;
    private LocalDateTime createdTime;
    private Long likeCount;
    private List<CommentDto> comments;

    public PostDetailDto(Post post, User user) {
        this.id =post.getId();
        this.author = new UserSimpleDto(post.getAuthor().getId(),post.getAuthor().getNickname(),post.getAuthor().getProfileImage(), user);
        this.title = post.getTitle();
        this.body = post.getBody();
        this.ImageUrls = post.getPostImages().stream()
                .map(postImages -> postImages.getUrl())
                .collect(Collectors.toList());
        this.createdTime = createdTime;
        this.likeCount = (long)post.getLikeUsers().size();
        this.comments = post.getComments().stream()
                .map(comment -> new CommentDto(comment,user))
                .collect(Collectors.toList());;
    }
}
