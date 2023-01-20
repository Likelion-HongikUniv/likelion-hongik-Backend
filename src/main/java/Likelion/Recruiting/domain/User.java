package Likelion.Recruiting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
// @Entity(name="Member") 이렇게하면 JPA에서는 User를 Member 라는 이름으로 사용가능
@Getter @Setter
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String studentId;

    private String major;

    private String part;

    private String phoneNum;

    private String role;

    private Long team_id;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
