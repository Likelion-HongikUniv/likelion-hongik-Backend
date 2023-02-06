package Likelion.Recruiting.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class ProfileDto {

    private String nickname;
    private String major;
    private String studentId;
    private String part;


    @Builder
    public ProfileDto(String nickname, String major, String studentId, String part) {
        this.nickname = nickname;
        this.major = major;
        this.studentId = studentId;
        this.part = part;
    }


}
