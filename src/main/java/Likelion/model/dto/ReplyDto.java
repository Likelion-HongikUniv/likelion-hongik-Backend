package Likelion.model.dto;

import Likelion.model.Comments;
import Likelion.model.Posts;
import Likelion.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDto {
    private Long ReplyId;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    private Comments comments;
    private Posts posts;
    private User user;
}
