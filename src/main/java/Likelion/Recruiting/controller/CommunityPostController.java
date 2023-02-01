package Likelion.Recruiting.controller;

import Likelion.Recruiting.repository.AdminPost;
import Likelion.Recruiting.service.CommunityPostService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityPostController {
    private final CommunityPostService communityPostService;
    // post id, 게시글 제목, 게시글 작성자, 게시글 유형, 게시글 body, date 전달
    // 모든 공지 게시판 글 조회

    @GetMapping("admin/community/post/{subCategory}")
    public String getAllPosts(@PathVariable(value="subCategory") String subCategory, Model model){
        List<AdminPost> posts= communityPostService.getAllPostByType(subCategory);
        model.addAttribute("posts",posts);
        model.addAttribute("type",subCategory);
        return "admin/community/admin_board";
    }

    //특정 공지 게시판 글 삭제
    @GetMapping("admin/community/post/{subCategory}/{post_id}")
    public String deletePost(@PathVariable("subCategory") String subCategory, @PathVariable("post_id") Long post_id){

        communityPostService.deletePostById(post_id);
        return "redirect:/admin/community/post/"+subCategory;
    }

    @GetMapping("admin/community/post/all/{subCategory}")
    public String deleteAllPosts(@PathVariable String subCategory){
        communityPostService.deleteAllPostByType();
        return "redirect:/admin/community/post/"+subCategory;
    }


}
