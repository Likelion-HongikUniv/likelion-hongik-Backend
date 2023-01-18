package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Override
    List<User> findAll();

//    User findById();
}
