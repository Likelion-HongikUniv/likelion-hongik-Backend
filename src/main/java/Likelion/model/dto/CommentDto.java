//package Likelion.model.dto;
//
//import Likelion.model.*;
//
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CommentDto {
//
//
//    private Post post;
//    private String Nickname;
//
//    private String picture;
//    private String body;
//    private List<CommentLike> likeProfiles = new ArrayList<>();
//    private LocalDateTime createdTime;
//
//
//
//    @Builder
//    public CommentDto(Post posts, String nickname,String picture, String body, List<CommentLike> likeProfiles, LocalDateTime createdTime) {
//        this.post = posts;
//        this.Nickname=nickname;
//        this.picture=picture;
//        this.body = body;
//        this.likeProfiles= likeProfiles;
//        this.createdTime= createdTime;
//
//    }
//
//    public Comment toEntity(){
//        Comment comment = Comment.builder()
//                .post(post)
//
//                .body(body)
//                .likeProfiles(likeProfiles)
//                .createdTime(createdTime)
//
//                .build();
//
//        return comment;
//    }
//}
