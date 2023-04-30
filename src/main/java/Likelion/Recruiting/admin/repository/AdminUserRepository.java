package Likelion.Recruiting.admin.repository;

import Likelion.Recruiting.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminUserRepository {

    private final EntityManager em;

    // User ID로 조회
    public User findOne(Long id) { return em.find(User.class, id); }

    // 모든 회원(User) 조회
    public List<User> findAll() {
        List<User> result = em.createQuery("select m from User m", User.class)
                .getResultList();

        return result;
    }

    // 회원 삭제
    public void deleteUser(User user) {
        em.remove(user);
    }



}
