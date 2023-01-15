package Likelion.Recruiting.service;


import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.PostImages;
import Likelion.Recruiting.repository.PostImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class PostImagesService {
    @Autowired
    private final PostImagesRepository postImagesRepository;
}
