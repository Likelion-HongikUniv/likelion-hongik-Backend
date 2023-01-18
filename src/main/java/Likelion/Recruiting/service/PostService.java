package Likelion.Recruiting.service;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public Page<PostDto> getMyAllPost(String email, Pageable pageable){
        // 해당 이메일가진 User 객체 가져오기
        User user = userRepository.findByEmail(email).get();

        Page<Post> posts = postRepository.findAllByAuthor(user, pageable);

        Page<PostDto> postDto = posts.map(p -> PostDto.builder()
                .title(p.getTitle())
                .author(p.getAuthor().getName())
                .profileImage(user.getProfileImage())
                .time(p.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                .body(p.getBody())
                .likes(p.getLikeUsers().size())
                .comments(p.getComments().size())
                .build());
        return postDto;
    }
}
