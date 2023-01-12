
package Likelion.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;


    @ManyToOne(fetch =LAZY) //
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne( fetch = LAZY) //
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Builder
    public CommentLike(Comment comment, Profile profile) {
        this.comment=comment;
        this.profile=profile;

    }


}
