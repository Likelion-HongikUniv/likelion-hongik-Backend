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
    private Long reply_id;
    private String body;
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    private boolean isDeleted;

    private Comments comments;
    private Profile author;
    private User user;


    @Builder
    /* Entity -> Dto*/
    public ReplyDto(Long reply_id, String createDate, String body, boolean isDeleted){
        this.reply_id = reply_id;
        this.createDate = createDate;
        this.isDeleted = isDeleted;
        this.body = body;
    }
    /*Dto -> Entity*/
    public Reply toEntity(){
        Reply reply = Reply.builder()
                .user(user)
                .author(author)
                .comments(comments)
                .reply_id(reply_id)
                .createDate(createDate)
                .isDeleted(isDeleted)
                .build();

        return reply;
    }

}
