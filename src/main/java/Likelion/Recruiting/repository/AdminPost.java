package Likelion.Recruiting.repository;

import lombok.Data;

@Data
public class AdminPost {
    String post_id;
    String title;
    String writer;
    String date;
    String body;
    String part;
    public AdminPost(String s, String s1, String s2, String s3, String s4, String s5) {
        post_id = s;
        title = s1;
        writer = s2;
        date = s3;
        body = s4;
        part = s5;
    }
}