package Likelion.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replylike_id")
    private Long replylike_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile author;

    @Builder
    public ReplyLike(User user, Profile author, Reply reply_id, Long replylike_id){
        this.user = user;
        this.author = author;
        this.reply_id = reply_id;
        this.replylike_id = replylike_id;
    }

}