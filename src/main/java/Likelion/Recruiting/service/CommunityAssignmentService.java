package Likelion.Recruiting.service;

import Likelion.Recruiting.repository.admin.AdminPost;
import Likelion.Recruiting.repository.admin.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityAssignmentService {
    ///post id, title, 작성자, date, body 전달
    private final CommunityRepository communityRepository;
    public List<AdminPost> findAllAssignment(){
        List<AdminPost> ass = communityRepository.findAll("HOMEWORK");
        return ass;
    }
    public List<AdminPost> findAssignmentByPart(String part){
        List<AdminPost> ass = communityRepository.findByCategoryAndSubCategory("HOMEWORK",part);
        return ass;
    }
    public List<AdminPost> findAssignmentByUser(String user_name){
        List<AdminPost> ass = communityRepository.findByUser("HOMEWORK",user_name);

        return ass;
    }
    public void deleteAllAssignment(String subCategory){
        communityRepository.deleteAll("HOMEWORK",subCategory);
    }
    public void deleteAssignment(Long post_id){
        communityRepository.deleteOneById(post_id);
    }

}
