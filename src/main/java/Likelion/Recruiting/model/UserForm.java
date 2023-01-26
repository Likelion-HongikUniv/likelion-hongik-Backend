package Likelion.Recruiting.model;

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

    private String role;

    private Team team;

}
