package Likelion.Recruiting.model.dto;

import lombok.Getter;

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

    public PostDto(String title, String author, String profileImage, String time, String body, Integer likes, Integer comments) {
        this.title = title;
        this.author = author;
        this.profileImage = profileImage;
        this.time = time;
        this.body = body;
        this.likes = likes;
        this.comments = comments;
    }
}
