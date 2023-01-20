package Likelion.Recruiting.model;


import Likelion.Recruiting.model.enums.LType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private LType lType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean isJoind;


    //-------------------여기서부터 커스터마이징-------------
    private String profileImage;

    private String nickname;

    private String major;

    private String studentId;

    private String part;

    private String phoneNum;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_name")
    private Team team;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostLike> likePosts = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CommentLike> likeComments = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ReplyLike> likeReplis = new ArrayList<>();


    @Builder
    public Users(String name, String email, LType lType, Role role) {
        this.name = name;
        this.email = email;
        this.lType = lType;
        this.role = role;
        this.isJoind = false;
    }

    public Users update(String name, String profileImage){
        this.name = name;
        this.profileImage = profileImage;

        return this;
    }
    public Users profileUpdate(String nickname, String major, String studentId, String part, String phoneNum){
        this.nickname =nickname;
        this.major = major;
        this.studentId = studentId;
        this.part = part;
        this.phoneNum = phoneNum;

        return this;
    }
    public String getRoleKey(){
        // user.getRole() == USER, GUEST
        // user.getRoleKey() == ROLE_USER, ROLE_GUEST

        return this.getRole().getKey();
    }
}