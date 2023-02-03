package Likelion.Recruiting.service;

import Likelion.Recruiting.model.Team;
import Likelion.Recruiting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Team findByUserId(Long userId){
        return teamRepository.findByUserId(userId);
    }
}
