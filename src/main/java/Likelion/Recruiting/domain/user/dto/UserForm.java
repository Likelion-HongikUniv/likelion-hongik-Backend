package Likelion.Recruiting.domain.user.dto;

import Likelion.Recruiting.domain.team.entity.Team;
import Likelion.Recruiting.domain.user.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserForm {

    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String studentId;

    private String major;

    private String part;

    private String phoneNum;

    private Role role;

    private Team team;

}
