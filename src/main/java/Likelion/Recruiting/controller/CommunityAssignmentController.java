package Likelion.Recruiting.controller;

import Likelion.Recruiting.repository.AdminPost;
import Likelion.Recruiting.service.CommunityAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CommunityAssignmentController {

    private final CommunityAssignmentService communityAssignmentService;

    /////////////커뮤니티-> 과제
    ///모든 과제 조회///post id, title, 작성자, date, body 전달

    @GetMapping("admin/community/assignment/everyPart")
    public String getAllAssignmentPost(Model model){
        communityAssignmentService.findAllAssignment();
        return "";
    }
    //백엔드 과제 조회
    @GetMapping("admin/community/assignment/backend")
    public String getBackendAssignmentPost(Model model){
        List<AdminPost> ass = communityAssignmentService.findAssignmentByPart("BACKEND");
        model.addAttribute("assignment", ass);
        model.addAttribute("type", "backend");
        return "admin/community/admin_assignment";
    }
    //프론트엔드 과제 조회
    @GetMapping("admin/community/assignment/frontend")
    public String getFrontendAssignmentPost(Model model){
        List<AdminPost> ass = communityAssignmentService.findAssignmentByPart("FRONT");
        model.addAttribute("assignment", ass);
        model.addAttribute("type", "frontend");
        return "admin/community/admin_assignment";
    }
    //기획 디자인 과제 조회
    @GetMapping("admin/community/assignment/design")
    public String getDesignAssignmentPost(Model model){
        List<AdminPost> ass = communityAssignmentService.findAssignmentByPart("DESIGN");
        model.addAttribute("assignment", ass);
        model.addAttribute("type", "design");
        return "admin/community/admin_assignment";
    }

    /// 특정 과제 게시물 삭제하기
    @GetMapping("admin/community/assignment/one/{post_id}/{part}")
    public String deleteAssignmentPost(@PathVariable(value ="post_id") Long post_id,@PathVariable(value = "part") String part,Model model){

        communityAssignmentService.deleteAssignment(post_id);
        return "redirect:/admin/community/assignment/"+part;
    }
    // 모든 과제 게시물 전체 삭제하기
    @GetMapping("admin/community/assignment/all/{subCategory}")
    public String deleteAllAssignmentPosts(@PathVariable(value = "subCategory") String subCategory){
        String part = "";
        if(Objects.equals(subCategory, "frontend")){part="FRONT";}
        else if (Objects.equals(subCategory, "backend")) {part="BACKEND";}
        else if(Objects.equals(subCategory, "design")) {part = "DESIGN";}
        communityAssignmentService.deleteAllAssignment(part);
        return "redirect:/admin/community/assignment/"+subCategory;
        //게시물 삭제 후 전체 과제물 조회 페이지로 리다이렉트!
    }
}
