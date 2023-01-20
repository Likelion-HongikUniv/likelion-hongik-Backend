package Likelion.Recruiting.service;

import Likelion.Recruiting.repository.AdminPost;
import Likelion.Recruiting.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityProjectService {
    private final CommunityRepository communityRepository;
    public List<AdminPost> getProjectByTeam(Long team_id){
        List<AdminPost> projects = communityRepository.findAllByTeam(team_id);
        return projects;
    }

//    public Project getProjectByTeamAndPart(String team,String part){
//        Project project = new Project();
//        return project;
//    }

    public void deleteProjectById(Long post_id){
        communityRepository.deleteOneById(post_id);
    }
    public void deleteAllProjectPosts(){
        communityRepository.deleteAll("PROJECT");
    }
    ////팀,post id, 파트, 게시글 제목, 게시글 작성자, date, body

}
