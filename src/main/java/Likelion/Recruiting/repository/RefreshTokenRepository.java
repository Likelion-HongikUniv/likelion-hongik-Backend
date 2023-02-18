package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

   Optional<RefreshToken> findByUserId(Long user_id);

   RefreshToken findByRefreshToken(String rt);
}
