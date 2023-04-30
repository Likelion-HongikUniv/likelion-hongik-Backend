package Likelion.Recruiting.domain.reply.dto;

import Likelion.Recruiting.domain.reply.entity.Reply;
import Likelion.Recruiting.domain.reply.entity.ReplyLike;
import Likelion.Recruiting.domain.user.dto.UserSimpleDto;
import Likelion.Recruiting.domain.user.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

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
