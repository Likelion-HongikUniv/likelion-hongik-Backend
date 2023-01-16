package Likelion.model.entity;



import Likelion.model.entity.enums.MainCategory;
import Likelion.model.entity.enums.SubCategory;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public void setUser(User user){
        this.user = user;
        user.getPosts().add(this);
    }


    public void changeMainCategory(MainCategory mainCategory){
        this.mainCategory = mainCategory;
    }

    public void changeSubCategory(SubCategory subCategory){
        this.subCategory = subCategory;
    }
}
