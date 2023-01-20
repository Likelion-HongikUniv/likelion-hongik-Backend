package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Users findOne(Long id) {
        return em.find(Users.class, id);
    }

    public void deleteUser(Users user) {
        em.remove(user);
    }

    public List<Users> findAll() {
        List<Users> result = em.createQuery("select m from Users m", Users.class)
                .getResultList();

        return result;
    }

    public List<Users> findByName(String name) {
        return em.createQuery("select m from Users m where m.name = :name", Users.class)
                .setParameter("name", name)
                .getResultList();
    }
}
