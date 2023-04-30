package Likelion.Recruiting.domain.team.controller;


import Likelion.Recruiting.common.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.domain.team.entity.Team;
import Likelion.Recruiting.domain.team.dto.TeamMemberResponseDto;
import Likelion.Recruiting.domain.team.service.TeamService;
import Likelion.Recruiting.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class TeamController {
    private final UserService userService;
    private final TeamService teamService;

    @GetMapping("/team/members")//팀 멤버불러오기
    public TeamMemberResponseDto getTeamMember(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser) {
        Team team = teamService.findTeam(customOauthUser.getUser().getId());
        return userService.getTeamMembers(team.getId());
    }
}
