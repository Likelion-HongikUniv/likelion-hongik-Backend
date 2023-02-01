package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public void deleteUser(User user) {
        em.remove(user);
    }

    public List<User> findAll() {
        List<User> result = em.createQuery("select m from User m", User.class)
                .getResultList();

        return result;
    }

}
