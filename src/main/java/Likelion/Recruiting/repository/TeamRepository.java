package Likelion.model.repository;


import Likelion.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,String> {
}
