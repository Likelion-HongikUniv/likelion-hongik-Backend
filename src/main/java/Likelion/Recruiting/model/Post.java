package Likelion.Recruiting.model;



import Likelion.Recruiting.model.dto.PostUpdateDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String thumbnailImage;

    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;

    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdTime= LocalDateTime.now();

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

    @Builder
    public Post(String title, String body, MainCategory mainCategory, SubCategory subCategory, String thumbnailImage) {
        this.title = title;
        this.body = body;
        this.thumbnailImage = thumbnailImage;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    public void setAuthor(User user){
        this.author = user;
        user.getPosts().add(this);
    }

    public void update(PostUpdateDto postUpdateDto){
        if(postUpdateDto.getTitle() != null)
            this.title = postUpdateDto.getTitle();
        if(postUpdateDto.getBody() != null)
            this.body = postUpdateDto.getBody();
        if(postUpdateDto.getThumbnailImage() != null)
            this.thumbnailImage = postUpdateDto.getThumbnailImage();
    }

    public void changeMainCategory(MainCategory mainCategory){
        this.mainCategory = mainCategory;
    }

    public void changeSubCategory(SubCategory subCategory){
        this.subCategory = subCategory;
    }

}
