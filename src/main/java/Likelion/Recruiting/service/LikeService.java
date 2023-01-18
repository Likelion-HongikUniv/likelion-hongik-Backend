package Likelion.Recruiting.service;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<PostDto> getLikedPost(Long userId){

        List<Post> posts = postRepository.findAllByLikeUsersIn(userId);
        User user = userRepository.findById(userId).get();

        List<PostDto> postDto = posts.stream()
                .map(p -> new PostDto(p.getTitle(), p.getAuthor().getName(), user.getProfileImage(), p.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")), p.getBody(), p.getLikeUsers().size(), p.getComments().size()))
                .collect(Collectors.toList());
        return postDto;

    }
}
