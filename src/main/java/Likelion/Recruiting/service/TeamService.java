package Likelion.Recruiting.service;

import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.community.CommunityException;
import Likelion.Recruiting.model.Team;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.repository.TeamRepository;
import Likelion.Recruiting.repository.UserRepository;
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
