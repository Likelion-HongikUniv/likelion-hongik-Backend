package Likelion.model.entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_name")
    private String name;


    @OneToMany(mappedBy = "team")
    private List<User> userList;


    public Team(String name) {
        this.name = name;
    }
}
