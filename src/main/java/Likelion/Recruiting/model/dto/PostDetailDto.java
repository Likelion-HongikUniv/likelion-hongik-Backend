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
    private String title;
    private String body;
    private List<String> ImageUrls;
    private LocalDateTime createdTime;
    private Boolean isLiked;
    private Long likeCount;
    private List<CommentDto> comments;

    public PostDetailDto(Post post, User user) {
        this.postId =post.getId();
        this.author = new UserSimpleDto(post.getAuthor().getId(),post.getAuthor().getNickname(),post.getAuthor().getProfileImage(), user);
        this.title = post.getTitle();
        this.body = post.getBody();
        this.ImageUrls = post.getPostImages().stream()
                .map(postImages -> postImages.getUrl())
                .collect(Collectors.toList());
        this.createdTime = post.getCreatedTime();
        this.isLiked = false;
        for(PostLike postLike:post.getLikeUsers()){
            if(postLike.getUser().equals(user)) {
                this.isLiked = true;
                break;
            }
        }
        this.likeCount = (long)post.getLikeUsers().size();
    }
//    public PostDetailDto(Post post, User user) {
//        this.postId =post.getId();
//        this.author = new UserSimpleDto(post.getAuthor().getId(),post.getAuthor().getNickname(),post.getAuthor().getProfileImage(), user);
//        this.title = post.getTitle();
//        this.body = post.getBody();
//        this.ImageUrls = post.getPostImages().stream()
//                .map(postImages -> postImages.getUrl())
//                .collect(Collectors.toList());
//        this.createdTime = post.getCreatedTime();
//        this.isLiked = false;
//        for(PostLike postLike:post.getLikeUsers()){
//            if(postLike.getUser().equals(user)) {
//                this.isLiked = true;
//                break;
//            }
//        }
//        this.likeCount = (long)post.getLikeUsers().size();
//        this.comments = post.getComments().stream()
//                .map(comment -> new CommentDto(comment,user))
//                .collect(Collectors.toList());
//    }
}
