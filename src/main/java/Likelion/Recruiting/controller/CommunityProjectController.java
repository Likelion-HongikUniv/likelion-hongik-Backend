package Likelion.Recruiting.controller;

import Likelion.Recruiting.repository.AdminPost;
import Likelion.Recruiting.service.CommunityProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityProjectController {
    private final CommunityProjectService communityProjectService;

    ////팀,post id, 파트, 게시글 제목, 게시글 작성자, date, body

    ///팀별 프로젝트 게시물 조회
    @GetMapping("admin/community/project/{team_id}")
    public String getAllProjectPost(@PathVariable("team_id") String team_id,Model model){
        List<AdminPost> projects = communityProjectService.getProjectByTeam(team_id);
        String team_name = "우왕왕";
        model.addAttribute("projects", projects);
        model.addAttribute("team_name",team_name);
        return "admin/community/admin_project";
    }

    /// 특정 프로젝트 게시물 삭제하기
    @GetMapping("admin/community/project/{team_id}/{post_id}")
    public String deleteProjectPost(@PathVariable("team_id") String team_id,@PathVariable("post_id") String post_id){
        communityProjectService.deleteProjectById(post_id);

        return  "redirect:/admin/community/project/"+team_id;
    }
    // 모든 프로젝트 게시물 전체 삭제하기 or team을 삭제하기?!!!?!?!
    @DeleteMapping("admin/community/project/all/{team_id}")
    public String deleteAllProjectPosts(@PathVariable String team_id){
        communityProjectService.deleteAllProjectPosts();
        return "redirect:/";
    }

    //    //팀 별 백엔드 프로젝트 게시물 조회
//    @GetMapping("admin/community/project/backend/")
//    public String getBackendProjectPost(@PathVariable String team_id){
//        CommunityProjectService.Project project = communityProjectService.getProjectByTeamAndPart(team_id, "backend");
//        return "";
//    }
//    //팀 별 프론트엔드 프로젝트 게시물 조회
//    @GetMapping("admin/community/project/frontend/")
//    public String getFrontendProjectPost(@PathVariable String team_id){
//        CommunityProjectService.Project project = communityProjectService.getProjectByTeamAndPart(team_id, "frontend");
//        return "";
//    }
//    //팀 별 기획 디자인 프로젝트 게시물 조회
//    @GetMapping("admin/community/project/design/")
//    public String getDesignProjectPost(@PathVariable String team_id){
//        CommunityProjectService.Project project = communityProjectService.getProjectByTeamAndPart(team_id, "design");
//        return "";
//    }
}
