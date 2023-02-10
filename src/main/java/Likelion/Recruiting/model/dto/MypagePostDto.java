package Likelion.Recruiting.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class MypagePostDto {

    private Long postId;
    @Size(max = 20)
    private String title;
    private String author;
    private String authorImage;
    private String time;
    @Size(max = 20)
    private String body;
    private Integer likes;
    private Integer comments;

    @Builder
    public MypagePostDto(Long postId, String title, String author, String authorImage, String time, String body, Integer likes, Integer comments) {
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.authorImage = authorImage;
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
