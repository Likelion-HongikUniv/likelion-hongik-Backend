package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class ReplyDto {
    private Long replyId;
    private UserSimpleDto author;
    private String body;
    private boolean isDeleted;
    private LocalDateTime createdTime;
    private Boolean isLiked;
    private Long likeCount;

    public ReplyDto(Reply reply, User user) {
        this.replyId = reply.getId();
        this.author = new UserSimpleDto(reply.getAuthor().getId(), reply.getAuthor().getNickname(), reply.getAuthor().getProfileImage(), user);
        this.body = reply.getBody();
        this.isDeleted = reply.getIsDeleted();
        this.createdTime = reply.getCreatedTime();
        this.isLiked = false;
        for(ReplyLike replyLike:reply.getLikeUsers()){
            if(replyLike.getUser().getId() == user.getId()) {
                this.isLiked = true;
                break;
            }
        }
        this.likeCount = (long)reply.getLikeUsers().size();
    }
}
