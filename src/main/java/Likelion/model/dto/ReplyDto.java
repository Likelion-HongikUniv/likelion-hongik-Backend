package Likelion.model.dto;

import Likelion.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {
    private Long replyId;
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    private boolean isDeleted;

    private Comments comments;
    private Profile author;
    private User user;


    @Builder
    /* Entity -> Dto*/
    public ReplyDto(Long replyId, String createDate, boolean isDeleted){
        this.replyId = replyId;
        this.createDate = createDate;
        this.isDeleted = isDeleted;
    }
    /*Dto -> Entity*/
    public Reply toEntity(){
        Reply reply = Reply.builder()
                .user(user)
                .author(author)
                .comments(comments)
                .replyId(replyId)
                .createDate(createDate)
                .isDeleted(isDeleted)
                .build();

        return reply;
    }

}
