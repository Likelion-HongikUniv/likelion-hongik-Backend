package Likelion.Recruiting.service;

import Likelion.Recruiting.repository.AdminPost;
import Likelion.Recruiting.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityAssignmentService {
    ///post id, title, 작성자, date, body 전달
    private final CommunityRepository communityRepository;
    public List<AdminPost> findAllAssignment(){
        List<AdminPost> ass = communityRepository.findAll("assignment");
        return ass;
    }
    public List<AdminPost> findAssignmentByPart(String part){
        List<AdminPost> ass = communityRepository.findByPart("assignment",part).get();
        return ass;
    }
    public List<AdminPost> findAssignmentByUser(String user_name){
        List<AdminPost> ass = communityRepository.findByUser("assignment",user_name);
        return ass;
    }
    public void deleteAllAssignment(){
        communityRepository.deleteAll("assignment","all");
    }
    public void deleteAssignment(String post_id){
        communityRepository.deleteOneById(post_id);
    }

}
