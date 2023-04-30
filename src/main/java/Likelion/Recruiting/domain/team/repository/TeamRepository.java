package Likelion.Recruiting.domain.team.repository;


import Likelion.Recruiting.domain.team.entity.Team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
