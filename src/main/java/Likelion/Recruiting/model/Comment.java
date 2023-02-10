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
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private final LocalDateTime createdTime = LocalDateTime.now(); //생성할떄 현재시각 넣기

    private Boolean isDeleted; //default 는 false로 설정하기

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

    @Builder
    public Comment(String body) {
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
        user.getComments().add(this);
    }
    public void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }

}

