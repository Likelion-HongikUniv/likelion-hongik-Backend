package Likelion.Recruiting.domain.team.service;

import Likelion.Recruiting.common.exception.ErrorCode;
import Likelion.Recruiting.common.exception.community.CommunityException;
import Likelion.Recruiting.domain.team.entity.Team;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.team.repository.TeamRepository;
import Likelion.Recruiting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Team findTeam(Long userId){
        User user = userRepository.findById(userId).get();
        Optional<Team> team = Optional.ofNullable(user.getTeam());
        return team.orElseThrow(() -> new CommunityException(ErrorCode.NO_TEAM));
    }
}
