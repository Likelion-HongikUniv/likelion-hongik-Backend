package Likelion.Recruiting.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String body;

    private LocalDateTime createdTime; //생성할떄 현재시각 넣기

    private boolean isDeleted; //default 는 false로 설정하기

    @OneToMany(
            mappedBy = "comment",
            cascade = CascadeType.ALL,
            orphanRemoval = true // 고아객체 (fk가 없어진 즉, 댓글좋아요 테이블에서 커맨트id column이 NULL 이 되면 그 열은 삭제
    )
    private List<CommentLike> likeProfiles = new ArrayList<>();
}
