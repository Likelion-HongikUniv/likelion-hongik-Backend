package Likelion.Recruiting.domain.reply.entity;

import Likelion.Recruiting.domain.user.entity.User;
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

    public ReplyLike(User user, Reply reply) {
        this.user = user;
        this.reply = reply;
        user.getLikeReplis().add(this);
        reply.getLikeUsers().add(this);
    }
    public void dislike(){
        this.user.getLikeReplis().remove(this);
        this.user = null;
        this.reply.getLikeUsers().remove(this);
        this.reply = null;
    }
}
