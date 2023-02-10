package Likelion.Recruiting.controller.admin;

import Likelion.Recruiting.repository.admin.AdminPost;
import Likelion.Recruiting.service.admin.CommunityProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityProjectController {
    private final CommunityProjectService communityProjectService;

    ////팀,post id, 파트, 게시글 제목, 게시글 작성자, date, body

    ///팀별 프로젝트 게시물 조회
    @GetMapping("admin/community/project/projects/{team_id}")
    public String getAllProjectPost(@PathVariable("team_id") Long team_id,Model model){
        List<AdminPost> projects = communityProjectService.getProjectByTeam(team_id);
        String team_name = communityProjectService.getTeamName(team_id);
        model.addAttribute("projects", projects);
        model.addAttribute("team_name",team_name);
        return "admin/community/admin_project";
    }

    /// 특정 프로젝트 게시물 삭제하기
    @GetMapping("admin/community/project/one/{team_id}/{post_id}")
    public String deleteProjectPost(@PathVariable("team_id") Long team_id,@PathVariable("post_id") Long post_id){
        communityProjectService.deleteProjectById(post_id);

        return  "redirect:/admin/community/project/projects/"+team_id;
    }
    // 모든 프로젝트 게시물 전체 삭제하기 or team을 삭제하기?!!!?!?!
    @PostMapping("admin/community/project/team_name/{team_id}")
    public String changeTeamName(@PathVariable Long team_id, @RequestParam String team_name){

        communityProjectService.changeTeamName(team_id,team_name);
        return "redirect:/admin/community/project/projects/"+team_id;
    }

    @GetMapping("admin/community/project/team/{team_id}")
    public String deleteTeam(@PathVariable Long team_id){

        communityProjectService.deleteTeam(team_id);
        return "redirect:/admin";
    }

    @GetMapping("admin/community/project/posts/{team_id}")
    public String deleteAllTeamProject(@PathVariable Long team_id){

        communityProjectService.deleteAllTeamProject(team_id);
        return "redirect:/admin/community/project/projects/"+team_id;
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
