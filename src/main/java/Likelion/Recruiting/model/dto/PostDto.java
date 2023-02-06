package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Size;

@Data
public class PostDto {

    private Long postId;
    @Size(max = 20)
    private String title;
    private String author;
    private String time;
    @Size(max = 20)
    private String body;
    private Integer likes;
    private Integer comments;

    @Builder
    public PostDto(Long postId, String title, String author, String time, String body, Integer likes, Integer comments) {
        this.postId = postId;
        this.title = title;
        this.author = author;
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
