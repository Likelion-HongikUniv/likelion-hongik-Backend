package Likelion.Recruiting.domain;

import Likelion.Recruiting.domain.enums.LType;
import Likelion.Recruiting.domain.enums.Role;
import lombok.Getter;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String loginId;

    @Enumerated(EnumType.STRING)
    private LType lType;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
