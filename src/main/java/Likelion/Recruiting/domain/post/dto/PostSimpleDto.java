package Likelion.Recruiting.domain.post.dto;


import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.entity.PostLike;
import Likelion.Recruiting.domain.user.dto.UserSimpleDto;
import Likelion.Recruiting.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSimpleDto {
    private Long postId;
    private String title;
    private UserSimpleDto author;
    private String body;
    private String thumbNailImage;
    private Boolean isLiked;
    private Long likeCount;
    private Long commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;

    public PostSimpleDto(Post post, User user) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.author = new UserSimpleDto(post.getAuthor().getId(),post.getAuthor().getNickname(),post.getAuthor().getProfileImage(), user);
        this.body = post.getBody();
        this.thumbNailImage = post.getThumbnailImage();
        this.isLiked = false;
        for(PostLike postLike:post.getLikeUsers()){
            if(postLike.getUser().getId() == user.getId()) {
                this.isLiked = true;
                break;
            }
        }
        this.likeCount = (long)post.getLikeUsers().size();
        long count = 0;
        for(Comment comment:post.getComments()){
            count += (comment.getReplies().size() + 1);
        }
        this.commentCount = count;
        this.createdTime = post.getCreatedTime();
    }
}
