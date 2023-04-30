package Likelion.Recruiting.domain.user.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue
    @Column(name = "rtId")
    private Long id;

    @Column(nullable = false, name = "userId")
    private Long userId;

    @Column(nullable = false, name = "refreshToken")
    private String refreshToken;

    @Builder
    public RefreshToken(Long user_id, String refreshToken) {
        this.userId = user_id;
        this.refreshToken = refreshToken;
    }
}
