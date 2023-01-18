package Likelion.Recruiting.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImages {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Builder
    public PostImages(String url) {
        this.url = url;
    }

    public void setPost(Post post){
        this.post = post;
        post.getPostImages().add(this);
    }
}