package Likelion.Recruiting.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
public class ProfileDto {

    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("major")
    private String major;
    @JsonProperty("studentId")
    private String studentId;
    @JsonProperty("part")
    private String part;


    @Builder
    public ProfileDto(String nickname, String major, String studentId, String part) {
        this.nickname = nickname;
        this.major = major;
        this.studentId = studentId;
        this.part = part;
    }


}
