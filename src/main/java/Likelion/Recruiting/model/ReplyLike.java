package Likelion.Recruiting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyLike {
    @Id
    @GeneratedValue
    @Column(name = "reply_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void makeReplyLike(User user, Reply reply) {
        this.user = user;
        this.reply = reply;
        user.getLikeReplis().add(this);
        reply.getLikeUsers().add(this);
    }
}
