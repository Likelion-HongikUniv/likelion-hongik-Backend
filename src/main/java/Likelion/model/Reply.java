package Likelion.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(name = "reply_body")
    private String body;

    @Column(name = "created_date")
    private String createDate;

    @Column(name = "isDeleted")
    private boolean isDeleted; //default 는 false로 설정하기

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comments comment;

    @OneToMany(
            mappedBy = "reply",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<ReplyLike> likeProfiles = new ArrayList<>();

}
