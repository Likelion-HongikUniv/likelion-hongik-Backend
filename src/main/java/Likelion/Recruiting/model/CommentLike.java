package Likelion.Recruiting.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @Id
    @GeneratedValue
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        user.getLikeComments().add(this);
        comment.getLikeUsers().add(this);
    }
    public void dislike(){
        this.user.getLikeComments().remove(this);
        this.user = null;
        this.comment.getLikeUsers().remove(this);
        this.comment = null;
    }
}