package Likelion.model.dto;

import Likelion.model.Profile;
import Likelion.model.Reply;
import Likelion.model.ReplyLike;
import Likelion.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyLikeDto {
    private Long replylike_id;
    private Reply reply;

    private User user;
    private Profile author;

    @Builder
    public ReplyLikeDto(Long replylike_id){
        this.replylike_id = replylike_id;
    }

    public ReplyLike toEntity(){
        ReplyLike replylike = ReplyLike.builder()
            .reply(reply)
            .author(author)
            .user(user)
            .replylike_id(replylike_id)
            .build();

        return replylike;
    }
}
