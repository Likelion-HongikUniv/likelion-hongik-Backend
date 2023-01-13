package Likelion.Recruiting.service;

import Likelion.Recruiting.domain.Post;
import Likelion.Recruiting.domain.PostImages;
import Likelion.Recruiting.repository.PostImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostImagesService {
    private final PostImagesRepository postImagesRepository;

    @Transactional
    public void PutPostImages(Post post, String imageUrl) {
        PostImages image = new PostImages(imageUrl);
        image.setPost(post);
    }
}
