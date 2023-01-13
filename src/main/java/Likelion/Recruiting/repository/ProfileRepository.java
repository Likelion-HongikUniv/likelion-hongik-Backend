package Likelion.Recruiting.repository;

import Likelion.Recruiting.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
}
