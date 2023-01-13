package Likelion.model.dto;

import Likelion.model.Profile;
import Likelion.model.Reply;
import Likelion.model.Replylike;
import Likelion.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplylikeDto {
    private Long replylike_id;
    private Reply reply_id;

    private User user;
    private Profile author;

    @Builder
    public ReplylikeDto(Long replylike_id){
        this.replylike_id = replylike_id;
    }

    public Replylike toEntity(){
        Replylike replylike = Replylike.builder()
            .reply_id(reply_id)
            .author(author)
            .user(user)
            .replylike_id(replylike_id)
            .build();

        return replylike;
    }
}
