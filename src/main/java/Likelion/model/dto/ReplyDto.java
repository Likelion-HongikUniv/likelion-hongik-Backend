package Likelion.model.dto;

import Likelion.model.*;
import Likelion.model.entity.Comment;
import Likelion.model.entity.Profile;
import Likelion.model.entity.Reply;
import Likelion.model.entity.User;
import lombok.AllArgsConstructor;
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
    private LocalDateTime createtime;
    private boolean isDeleted;

    private CommentDto comment_id;
    private UserSimpleDto user_id;

    /*ResponseDTO: Entity -> Dto*/
    public ReplyDto(Long reply_id, String body, LocalDateTime createtime, boolean isDeleted, CommentDto comment_id, UserSimpleDto user_id){
        this.reply_id = reply_id;
        this.body = body;
        this.createtime = createtime;
        this.isDeleted = isDeleted;
    }
    /*RequestDTO: DTO -> Entity*/
    public Reply toEntity(){
        Reply reply = Reply.builder()
                .reply_id(reply_id)
                .body(body)
                .createtime(LocalDateTime.now())
                .comment_id(comment_id)
                .user_id(user_id)
                .build();
        return reply;

    }


}
