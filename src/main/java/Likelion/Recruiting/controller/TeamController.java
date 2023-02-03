package Likelion.Recruiting.controller;


import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.Team;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.dto.TeamMemberResponseDto;
import Likelion.Recruiting.service.TeamService;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class TeamController {
    private final UserService userService;
    private final TeamService teamService;

    @GetMapping("/team/members")//팀 멤버불러오기
    public TeamMemberResponseDto getPostDetail(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser) {
        Team team = teamService.findTeam(customOauthUser.getUser().getId());
        return userService.getTeamMembers(team.getId());
    }
}
