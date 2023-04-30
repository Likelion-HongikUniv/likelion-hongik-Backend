package Likelion.Recruiting.common.config.s3;


import Likelion.Recruiting.common.config.s3.PreSignedUrlService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class S3controller {
    private final PreSignedUrlService preSignedUrlService;

    @GetMapping("/pre-signed-url/postImage")
    public String getPostImagePresignedUrl() {
        UUID uuid = UUID.randomUUID();
        return preSignedUrlService.getPreSignedUrl("mutsa-recruiting", "post-image", uuid.toString());
    }
    @GetMapping("/pre-signed-url/profileImage")
    public String getProfileImagePresignedUrl() {
        UUID uuid = UUID.randomUUID();
        return preSignedUrlService.getPreSignedUrl("mutsa-recruiting", "profile-image", uuid.toString());
    }
    @GetMapping("/pre-signed-url/thumbnailImage")
    public String getthumbnailImagePresignedUrl() {
        UUID uuid = UUID.randomUUID();
        return preSignedUrlService.getPreSignedUrl("mutsa-recruiting", "thumbnail-image", uuid.toString());
    }
}
