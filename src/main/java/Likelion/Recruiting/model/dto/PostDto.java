package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.Post;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Size;

@Getter
public class PostDto {

    @Size(max = 20)
    private String title;
    private String author;
    private String profileImage;
    private String time;
    @Size(max = 20)
    private String body;
    private Integer likes;
    private Integer comments;

    @Builder
    public PostDto(String title, String author, String profileImage, String time, String body, Integer likes, Integer comments) {
        this.title = title;
        this.author = author;
        this.profileImage = profileImage;
        this.time = time;
        this.body = body;
        this.likes = likes;
        this.comments = comments;
    }

//    public Page<PostDto> toDtoList(Page<Post> postList){
//        Page<PostDto> postDtoList = postList.map(m -> PostDto.builder()
//                .title(m.getTitle())
//                .author(m.getAuthor())
//                .profileImage
//                .time)
//    }

}
