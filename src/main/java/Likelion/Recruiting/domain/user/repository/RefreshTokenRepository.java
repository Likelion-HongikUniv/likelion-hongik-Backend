package Likelion.Recruiting.domain.user.repository;

import Likelion.Recruiting.domain.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

   Optional<RefreshToken> findByUserId(Long user_id);

   RefreshToken findByRefreshToken(String rt);
}
