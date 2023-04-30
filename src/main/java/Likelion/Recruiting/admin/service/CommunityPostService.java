package Likelion.Recruiting.admin.service;

import Likelion.Recruiting.admin.AdminPost;
import Likelion.Recruiting.admin.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostService {
    private final CommunityRepository communityRepository;
    public List<AdminPost> getAllPostByType(String subCategory){
        //subCategory = noti,free,qna 중 하나!
         List<AdminPost> postsByType = communityRepository.findByCategoryAndSubCategory("BOARD",subCategory);
        return postsByType;
    }
    public void deleteAllPostByType(String subCategory){
        communityRepository.deleteAll("BOARD",subCategory);
    }
    public void deletePostById(Long post_id)
    {
        communityRepository.deleteOneById(post_id);
    }

}
