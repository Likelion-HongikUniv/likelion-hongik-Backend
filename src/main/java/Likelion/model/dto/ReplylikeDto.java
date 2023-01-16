package Likelion.model.dto;

import Likelion.model.entity.Profile;
import Likelion.model.entity.Reply;
import Likelion.model.entity.Replylike;
import Likelion.model.entity.User;
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

}
