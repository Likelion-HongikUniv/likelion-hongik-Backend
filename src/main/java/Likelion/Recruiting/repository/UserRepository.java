package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Override
    @Query("select u from User u join fetch u.team")
    List<User> findAll();
    List<User> findAllByTeamId(Long teamId);
}
