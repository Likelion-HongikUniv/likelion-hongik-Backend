package Likelion.model.repository;

import Likelion.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public Profile findByUser_Id(int user_id);

}
