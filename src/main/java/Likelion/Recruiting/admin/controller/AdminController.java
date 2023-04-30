package Likelion.Recruiting.admin.controller;

import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.user.dto.UserAllDto;
import Likelion.Recruiting.admin.service.AdminCommentService;
import Likelion.Recruiting.admin.service.AdminPostService;
import Likelion.Recruiting.admin.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class AdminController {

    private final AdminUserService adminUserService;
    private final AdminPostService adminPostService;
    private final AdminCommentService adminCommentService;

    public AdminController(AdminUserService adminUserService, AdminPostService adminPostService, AdminCommentService adminCommentService) {
        this.adminUserService = adminUserService;
        this.adminPostService = adminPostService;
        this.adminCommentService = adminCommentService;
    }

    @RequestMapping("/admin")
    private String main() {
        return "admin/main";
    }


    //--- User Controller ---//

    // 회원목록 조회
    @GetMapping("admin/users")
    public String userList(Model model) {
        List<User> users = adminUserService.findUsers();
        model.addAttribute("users", users);

        return "admin/users";
    }

    // 회원 정보 수정 - get : 수정 form 렌더링
    @GetMapping("admin/users/{userId}/edit")
    public String userForm(@PathVariable("userId") Long userId, Model model) {
        User user = (User) adminUserService.findOne(userId);

        //UserAllDto form = new UserAllDto(id, username, nickname, profileImageSrc, role, major, part, studentId, team);
        UserAllDto form = UserAllDto.builder()
                .userId(user.getId())
                .username(user.getName())
                .nickname(user.getNickname())
                .major(user.getMajor())
                .part(user.getPart())
                .studentId(user.getStudentId())
                .role((user.getRole()))
                .build();

        model.addAttribute("form", form);

        return "admin/updateUserForm";
    }

    // 회원 정보 수정 - post : 수정된 내용 저장
    @PostMapping("admin/users/{userId}/edit")
    public String updateUser(@PathVariable Long userId, @ModelAttribute("form") UserAllDto form) {

        adminUserService.updateUser(userId
                ,form.getUsername()
                ,form.getNickname()
                ,form.getMajor()
                ,form.getPart()
                ,form.getRole()
                ,form.getStudentId());

        return "redirect:/admin/users";
    }

    // 회원 삭제
    @GetMapping("admin/users/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId) {
        adminUserService.deleteUser(userId);

        return "redirect:/admin/users";
    }

    // User가 작성한 글/댓글 조회
    @GetMapping("admin/users/{userId}/writing")
    public String userWriting(@PathVariable("userId") Long userId, Model model) {
        User user = (User) adminUserService.findOne(userId);
        List<Post> posts = adminPostService.findPostsByUserId(userId);
        List<Comment> comments = adminCommentService.findCommentsByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);

        return "admin/userWriting";
    }

    // User가 작성한 글/댓글 조회 - 게시글 전체삭제
    @GetMapping("admin/users/{userId}/posts/delete")
    public String postDeleteAll(@PathVariable("userId") Long userId) {

        adminPostService.deletePostByUser(userId);

        return "redirect:/admin/users/{userId}/writing";

    }

    // User가 작성한 글/댓글 조회 - 댓글 전체삭제
    @GetMapping("admin/users/{userId}/comments/delete")
    public String commentDeleteAll(@PathVariable("userId") Long userId) {

        adminCommentService.deleteCommentByUser(userId);

        return "redirect:/admin/users/{userId}/writing";
    }

    // User가 작성한 글/댓글 조회 - 게시글 1개 삭제
    @GetMapping("admin/users/{userId}/posts/{postId}/delete")
    public String postDelete(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {

        adminPostService.deletePostOne(postId);

        return "redirect:/admin/users/{userId}/writing";
    }

    // User가 작성한 글/댓글 조회 - 댓글 1개 삭제
    @GetMapping("admin/users/{userId}/comments/{commentId}/delete")
    public String commentDelete(@PathVariable("userId") Long userId, @PathVariable("commentId") Long commentId) {

        adminCommentService.deleteCommentOne(commentId);

        return "redirect:/admin/users/{userId}/writing";
    }


}

