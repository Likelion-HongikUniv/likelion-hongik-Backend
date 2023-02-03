package Likelion.Recruiting.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private final LocalDateTime createdTime= LocalDateTime.now();

    private Boolean isDeleted; //default 는 false로 설정하기

    @OneToMany(
            mappedBy = "reply",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ReplyLike> likeUsers = new ArrayList<>();
    @Builder
    public Reply(String body) {
        this.body = body;
        this.isDeleted = false;
    }

    public void update(String body){
        this.body = body;
    }

    public void delete(){
        this.isDeleted = true;
    }


    public void setAuthor(User user){
        this.author = user;
        user.getReplies().add(this);
    }
    public void setComment(Comment comment){
        this.comment = comment;
        comment.getReplies().add(this);
    }
}
