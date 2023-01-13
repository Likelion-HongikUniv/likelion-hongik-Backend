package Likelion.Recruiting.domain;


import Likelion.Recruiting.domain.enums.MainCategory;
import Likelion.Recruiting.domain.enums.SubCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private String title;

    private String body;

    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;

    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    private LocalDateTime createdTime;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostLike> likeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImages> postImages;

    @Builder
    public Post(String title, String body, String mainCategory, String subCategory) {
        this.title = title;
        this.body = body;
        this.mainCategory = MainCategory.valueOf(mainCategory);
        this.subCategory = SubCategory.valueOf(subCategory);
    }

    public void setUser(User user){
        this.author = user;
        user.getPosts().add(this);
    }


    public void changeMainCategory(MainCategory mainCategory){
        this.mainCategory = mainCategory;
    }

    public void changeSubCategory(SubCategory subCategory){
        this.subCategory = subCategory;
    }
}
