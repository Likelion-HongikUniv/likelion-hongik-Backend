package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.PostLike;
import Likelion.Recruiting.model.User;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDetailDto {
    private Long postId;
    private UserSimpleDto author;
    private String thumbnailImage;
    private String title;
    private String body;
    private String createdTime;
    private Boolean isLiked;
    private Long likeCount;
    private List<CommentDto> comments;

    public PostDetailDto(Post post, User user) {
        this.postId =post.getId();
        this.author = new UserSimpleDto(post.getAuthor().getId(),post.getAuthor().getNickname(),post.getAuthor().getProfileImage(), user);
        this.title = post.getTitle();
        this.thumbnailImage = post.getThumbnailImage();
        this.body = post.getBody();
        this.createdTime = post.getCreatedTime().toString();
        this.isLiked = false;
        for(PostLike postLike:post.getLikeUsers()){
            if(postLike.getUser().getId() == user.getId()) {
                this.isLiked = true;
                break;
            }
        }
        this.likeCount = (long)post.getLikeUsers().size();
        this.comments = post.getComments().stream()
                .map(comment -> new CommentDto(comment, user))
                .collect(Collectors.toList());
    }
}
