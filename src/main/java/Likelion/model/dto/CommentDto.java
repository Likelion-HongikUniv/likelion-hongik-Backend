package Likelion.model.dto;

import Likelion.model.*;

import lombok.Builder;

import javax.naming.Name;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDto {



    private User user;

    private String body;
    private List<CommentLike> likeProfiles = new ArrayList<>();
    private LocalDateTime createdTime;



    @Builder
    public CommentDto( User user,String body, List<CommentLike> likeProfiles, LocalDateTime createdTime) {
        this.user=user;
        this.body = body;
        this.likeProfiles= likeProfiles;
        this.createdTime= createdTime;

    }

    public Comment toEntity(){
        Comment comment = Comment.builder()
                .user(user)

                .body(body)
                .likeProfiles(likeProfiles)
                .createdTime(createdTime)

                .build();

        return comment;
    }
}
