package Likelion.Recruiting.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommunityRepository {
    private final EntityManager em;

    public List<AdminPost> findAll(String category){
        List<AdminPost> temp = new ArrayList<>();
        temp.add(new AdminPost("1","게시물1","김민영","2월 13일","안녕하신가~~1","assigment"));
        temp.add(new AdminPost("2","게시물2","김민지","2월 15일","안녕하신가~~2","assigment"));
        return temp;
    }
    public Optional<List<AdminPost>> findByPart(String category, String subCategory){
        List<AdminPost> temp = new ArrayList<>();
        temp.add(new AdminPost("3","게시물33333333333333333333333333333333333333333333","김민영","2월 13일","안녕하신가~~1nqewnflkasnkwwertqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqql","assigment"));
        temp.add(new AdminPost("4","게시물44444444444444444444444444444444444444444444444","김민지","2월 15일","안녕하신가~~2qewertrqerqeqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","assigment"));
        return Optional.of(temp);
    }
    public List<AdminPost> findByUser(String category, String name){
        List<AdminPost> temp = new ArrayList<>();
        temp.add(new AdminPost("1","게시물1","김민영","2월 13일","안녕하신가~~1","assigment"));
        temp.add(new AdminPost("3","게시물3","김민영","2월 15일","안녕하신가~~2","assigment"));
        return temp;
    }
    public void deleteAll(String category,String subCategory){

    }
    public void deleteOneById(String post_id){

    }

    public List<AdminPost> findAllByTeam(String team_id){
        List<AdminPost> temp = new ArrayList<>();
        temp.add(new AdminPost("1","게시물1","김민영","2월 13일","안녕하신가~~1","backend"));
        temp.add(new AdminPost("3","게시물3","김민영","2월 15일","안녕하신가~~2","frontend"));
        return temp;
    }


}
