package Likelion.Recruiting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_name")
    private String name;


    @OneToMany(mappedBy = "team")
    private List<Profile> profileList;

}
