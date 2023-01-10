package Likelion.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

//    @Column
//    private String provider;
//    @Column
//    private String providerId;

    @Builder
    public User(String username, String name, String email, String picture, Role role) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }


    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.getRole().getKey();
//        return this.getRolekey();
    }
}
