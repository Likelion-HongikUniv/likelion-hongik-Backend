package Likelion.Recruiting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {
    @Id
    @GeneratedValue
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
        user.getLikePosts().add(this);
        post.getLikeUsers().add(this);
    }
    public void dislike(){
        this.user.getLikePosts().remove(this);
        this.user = null;
        this.post.getLikeUsers().remove(this);
        this.post = null;
    }
}