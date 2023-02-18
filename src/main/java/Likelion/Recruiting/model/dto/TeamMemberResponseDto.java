package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeamMemberResponseDto {
    private Long teamId;
    private String teamName;
    private Long memberCount;

    private List<MembeSimpleDto> members = new ArrayList<>();

    @Builder
    public TeamMemberResponseDto(Long teamId, String teamName, Long memberCount, List<User> users) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.memberCount = memberCount;
        this.members = users.stream()
                .map(u -> MembeSimpleDto.builder()
                        .userId(u.getId())
                        .userName(u.getName())
                        .profileImage(u.getProfileImage())
                        .build()).collect(Collectors.toList());
    }
}
