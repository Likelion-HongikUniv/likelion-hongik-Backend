
package Likelion.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;


    @ManyToOne(fetch = LAZY) //
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY) //
    @JoinColumn(name = "user_id")
    private User user;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body; // 댓글 내용


    @CreatedDate
    private LocalDateTime createdTime;

    private boolean isDeleted; //default 는 false로 설정하기

    @OneToMany(
            mappedBy = "comment",
            cascade = CascadeType.ALL,
            orphanRemoval = true // 고아객체 (fk가 없어진 즉, 댓글좋아요 테이블에서 커맨트id column이 NULL 이 되면 그 열은 삭제
    )
    private List<CommentLike> likeProfiles = new ArrayList<>();

    @Builder
    public Comment( Post post, User user, String body, List<CommentLike> likeProfiles, LocalDateTime createdTime, boolean isDeleted) {
        this.post = post;
        this.user=user;
        this.body = body;
        this.likeProfiles=likeProfiles;
        this.createdTime= createdTime;
        this.isDeleted=isDeleted;
    }


}
