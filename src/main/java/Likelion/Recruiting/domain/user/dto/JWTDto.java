package Likelion.Recruiting.domain.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class JWTDto {

    private String JWT;

    public JWTDto(String JWT) {
        this.JWT = JWT;
    }
}
