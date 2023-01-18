package Likelion.Recruiting.service;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.PostLike;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.repository.PostLikeRepository;
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
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public Page<PostDto> getLikedPost(Long userId, Pageable pageable){

        User user = userRepository.findById(userId).get();
        List<PostLike> postLikes = postLikeRepository.findByUser(user);
        Page<Post> posts = postRepository.findAllByLikeUsersIn(postLikes, pageable);
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
