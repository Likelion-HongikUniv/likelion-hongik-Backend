package Likelion.Recruiting.repository;

import Likelion.Recruiting.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,String> {
}
