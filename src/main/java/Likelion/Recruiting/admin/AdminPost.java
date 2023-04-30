package Likelion.Recruiting.admin;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class AdminPost {
    Long post_id;
    String title;
    String writer;
    LocalDateTime date;
    String body;
    String part;
    public AdminPost(Long s, String s1, String s2, LocalDateTime s3, String s4, String s5) {
        post_id = s;
        title = s1;
        writer = s2;
        date = s3;
        body = s4;
        part = s5;
    }

    public AdminPost() {

    }
}