package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentDto {

    private Long id;
    private UserSimpleDto author;
    private String body;
    private boolean isDeleted;
    private LocalDateTime createdTime;
    private Long likeCount;
    private List<ReplyDto> replies;

    public CommentDto(Comment comment, User user) {
        this.id = comment.getId();
        this.author = new UserSimpleDto(comment.getAuthor().getId(),comment.getAuthor().getNickname(),comment.getAuthor().getProfileImage(), user);
        this.body = comment.getBody();
        this.isDeleted = comment.isDeleted();
        this.createdTime = comment.getCreatedTime();
        this.replies = comment.getReplies().stream()
                .map(reply -> new ReplyDto(reply,user))
                .collect(Collectors.toList());
    }
}
