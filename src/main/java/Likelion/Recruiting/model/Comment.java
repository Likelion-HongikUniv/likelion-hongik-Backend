package Likelion.Recruiting.model;

import lombok.AccessLevel;
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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private Users author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDateTime createdTime; //생성할떄 현재시각 넣기

    private boolean isDeleted; //default 는 false로 설정하기

    @OneToMany(
            mappedBy = "comment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(
            mappedBy = "comment",
            cascade = CascadeType.ALL,
            orphanRemoval = true // 고아객체 (fk가 없어진 즉, 댓글좋아요 테이블에서 커맨트id column이 NULL 이 되면 그 열은 삭제
    )
    private List<CommentLike> likeUsers = new ArrayList<>();

    public Comment(String body, LocalDateTime createdTime, boolean isDeleted) {
        this.body = body;
        this.createdTime = LocalDateTime.now();
        this.isDeleted = false;
    }


    public void setUser(Users user){
        this.author = user;
        user.getComments().add(this);
    }
    public void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }
}