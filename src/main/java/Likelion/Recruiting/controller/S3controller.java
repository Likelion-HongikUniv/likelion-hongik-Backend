package Likelion.Recruiting.controller;


import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.config.s3.PreSignedUrlService;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PageResponseDto;
import Likelion.Recruiting.model.dto.PostSimpleDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class S3controller {
    private final PreSignedUrlService preSignedUrlService;

    @GetMapping("/pre-signed-url/postImage")//카테고리에따른 게시글 가져오는 api
    public String getPostImagePresignedUrl() {
        UUID uuid = UUID.randomUUID();
        return preSignedUrlService.getPreSignedUrl("mutsa-recruiting", "post-image", uuid.toString());
    }
}
