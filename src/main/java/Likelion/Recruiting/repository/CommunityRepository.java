package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

//mainCategory = {Board,Assignment,Project} , subCategory = {Backend, Frontend,Design, Free,QnA,Notice}
@Repository
@RequiredArgsConstructor
public class CommunityRepository  {

    private final EntityManager em;


    public List<AdminPost> findAll(String category){

        return em.createQuery(
                "select new Likelion.Recruiting.repository.AdminPost(p.id,p.title,u.name,p.createdTime,p.body,u.part) " +
                " from Post p join p.author u" +
                " where p.mainCategory = :main",AdminPost.class)
                .setParameter("main",MainCategory.valueOf(category))
                .getResultList();
    }


    public List<AdminPost> findByCategoryAndSubCategory(String mainCategory, String subCategory){

        return em.createQuery(
                "select new Likelion.Recruiting.repository.AdminPost(p.id,p.title,u.name,p.createdTime,p.body,u.part) " +
                " from Post p join p.author u" +
                " where p.mainCategory = :mainCategory and p.subCategory = :subCategory", AdminPost.class)
                .setParameter("mainCategory",MainCategory.valueOf(mainCategory))
                .setParameter("subCategory", SubCategory.valueOf(subCategory))
                .getResultList();
    }
    // post 테이블에서 mainCategory가 maincategory이고, subCategory가 subCategory인 게시물과, 글쓴이의 이름,글쓴이의 id


   public List<AdminPost> findByUser(String category, String name){
        return em.createQuery(
                "select new Likelion.Recruiting.repository.AdminPost(p.id,p.title,u.name,p.createdTime,p.body,u.part) " +
                " from Post p join p.author u" +
                " where p.mainCategory = :category and u.name = :name", AdminPost.class)
                .setParameter("category",MainCategory.valueOf(category))
                .setParameter("name",name)
                .getResultList();
    }



    @Transactional
    public void deleteOneById(Long post_id){
        Query query = em.createQuery("delete from Post p where p.id=:post_id").setParameter("post_id",post_id);
        int rows = query.executeUpdate();

    }

    public List<AdminPost> findAllByTeam(Long team_id){
        return em.createQuery(
                "select new Likelion.Recruiting.repository.AdminPost(p.id,p.title,u.name,p.createdTime,p.body,u.part) " +
                " from Post p join p.author u join u.team t" +
                " where p.mainCategory = :project and t.id =:team_id", AdminPost.class)
                .setParameter("project",MainCategory.PROJECT)
                .setParameter("team_id",team_id)
                .getResultList();
    }
    //Team의 id가 team_id인 팀에 속해있는 user들이 쓴, post의 mainCategory가 project인 포스트 객체 + 글쓴이의 이름과 id, part



    @Transactional
    public void deleteAll(String mainCateogry){
        em.createQuery("delete from Post p" +
                " where p.mainCategory=:mainCategory").setParameter("mainCategory",MainCategory.valueOf(mainCateogry));
    }
}
