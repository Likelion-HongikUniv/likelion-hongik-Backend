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
        model.addAttribute("team_id",team_id);
        return "admin/community/admin_project";
    }

    /// 특정 프로젝트 게시물 삭제하기
    @GetMapping("admin/community/project/one/{team_id}/{post_id}")
    public String deleteProjectPost(@PathVariable("team_id") Long team_id,@PathVariable("post_id") Long post_id){
        communityProjectService.deleteProjectById(post_id);

        return  "redirect:/admin/community/project/projects/"+team_id;
    }
    // 팀 이름 변경하기
    @PostMapping("admin/community/project/team_name/{team_id}")
    public String changeTeamName(@PathVariable Long team_id, @RequestParam String team_name){

        communityProjectService.changeTeamName(team_id,team_name);
        return "redirect:/admin/community/project/projects/"+team_id;
    }

    // 팀 관련 게시글 삭제하고, 팀 삭제하기, user의 team은 null로 변경.
    @GetMapping("admin/community/project/team/{team_id}")
    public String deleteTeam(@PathVariable Long team_id){
        communityProjectService.deleteAllTeamProject(team_id); //team 관련 게시글 모두 삭제
        communityProjectService.deleteTeam(team_id); // user의 팀 속성 먼저 null로 바꾸고, team 객체에서 team 삭제.
        return "redirect:/admin";
    }

    @GetMapping("admin/community/project/posts/{team_id}")
    public String deleteAllTeamProject(@PathVariable Long team_id){

        communityProjectService.deleteAllTeamProject(team_id);
        return "redirect:/admin/community/project/projects/"+team_id;
    }

}
