package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Reply;
import Likelion.Recruiting.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class ReplyDto {
    private Long id;
    private UserSimpleDto author;
    private String body;
    private boolean isDeleted;
    private LocalDateTime createdTime;
    private Long likeCount;

    public ReplyDto(Reply reply, User user) {
        this.id = reply.getId();
        this.author = new UserSimpleDto(reply.getAuthor().getId(), reply.getAuthor().getNickname(), reply.getAuthor().getProfileImage(), user);
        this.body = reply.getBody();
        this.isDeleted = reply.getIsDeleted();
        this.createdTime = reply.getCreatedTime();
        this.likeCount = (long)reply.getLikeUsers().size();
    }
}
