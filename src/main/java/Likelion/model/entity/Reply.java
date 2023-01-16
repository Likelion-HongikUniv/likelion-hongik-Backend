package Likelion.model.entity;
import Likelion.model.dto.CommentDto;
import Likelion.model.dto.UserSimpleDto;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long reply_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private UserSimpleDto user_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private CommentDto comment_id;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDateTime createtime;

    private boolean isDeleted; //default 는 false로 설정하기

    @OneToMany(
            mappedBy = "reply",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Replylike> likeUsers = new ArrayList<>();

    @Builder
    public Reply(Long reply_id, String body, LocalDateTime createtime, CommentDto comment_id, UserSimpleDto user_id) {
        this.reply_id= reply_id;
        this.body = body;
        this.createtime = createtime;
        this.comment_id = comment_id ;
        this.user_id = user_id;
    }

//
//    public static indexController builder() {
//    }
//
    public void setUser(User user_id){
        user_id.getReplies().add(this);
    }
    public void setComment(Comment comment_id){
        comment_id.getReplies().add(this);
    }
}
