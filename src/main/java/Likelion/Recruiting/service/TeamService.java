package Likelion.Recruiting.service;

import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.community.CommunityException;
import Likelion.Recruiting.model.Team;
import Likelion.Recruiting.repository.TeamRepository;
import Likelion.Recruiting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Team findTeam(Long userId){
        Long teamId = userRepository.findById(userId).get().getTeam().getId();
        return teamRepository.findById(teamId).orElseThrow(() -> new CommunityException(ErrorCode.NO_TEAM));
    }
}
