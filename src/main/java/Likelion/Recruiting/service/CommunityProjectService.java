package Likelion.Recruiting.service;

import Likelion.Recruiting.repository.admin.AdminPost;
import Likelion.Recruiting.repository.admin.CommunityRepository;
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
        communityRepository.deleteAll("PROJECT","PROJECT");
    }

    public String getTeamName(Long team_id){
        return communityRepository.getTeamName(team_id);
    }

    public void changeTeamName(Long team_id, String team_name) { communityRepository.changeTeamName(team_id,team_name);}

    public void deleteTeam(Long team_id) {
        communityRepository.deleteTeam(team_id);
    }

    public void deleteAllTeamProject(Long team_id) {
        communityRepository.deleteAllTeamProject(team_id);
    }
}
