package Likelion.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private int id;


    @OneToOne(fetch = FetchType.LAZY) // user와 추가정보는 cascade이고 지연로딩
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String nickname;

    @Column
    private String major;

    @Column
    private String studentId;

    @Column
    private String part;

    @Column
    private String phoneNum;

    @Builder
    public Profile(User user, String nickname, String major, String studentId, String part, String phoneNum) {
        this.user = user;
        this.nickname = nickname;
        this.major = major;
        this.studentId = studentId;
        this.part = part;
        this.phoneNum = phoneNum;
    }
}
