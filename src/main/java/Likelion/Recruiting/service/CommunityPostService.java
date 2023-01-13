package Likelion.Recruiting.service;

import Likelion.Recruiting.repository.AdminPost;
import Likelion.Recruiting.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostService {
    private final CommunityRepository communityRepository;
    public List<AdminPost> getAllPostByType(String subCategory){
        //subCategory = noti,free,qna 중 하나!
        List<AdminPost> postsByType = communityRepository.findByPart("post",subCategory).get();
        return postsByType;
    }
    public void deleteAllPostByType(String subCategory){
        communityRepository.deleteAll("post",subCategory);
    }
    public void deletePostById(String post_id)
    {
        communityRepository.deleteOneById(post_id);
    }

}
