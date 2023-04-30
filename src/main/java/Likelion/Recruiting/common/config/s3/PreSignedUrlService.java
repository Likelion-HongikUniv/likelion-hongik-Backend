package Likelion.Recruiting.common.config.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PreSignedUrlService {

    private final AmazonS3 amazonS3;

    public String getPreSignedUrl(String bucket, String prefix, String fileName) {
        if (!prefix.equals("")) {
            fileName = prefix + "/" + fileName;
        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, fileName);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

//    - bucket: 버킷 이름
//    - prefix: 버킷 내 디렉토리 이름, 없으면 빈 문자열("")
//    - fileName: 업로드 하고자 하는 파일명
//
//    getGeneratePreSignedUrlRequest() 에서
//    .withMethod(HttpMethod.PUT)은 업로드용 Pre-Signed URL을 생성하기 때문에, PUT을 지정했다.
//    .withExpiration()은 URL의 유효 기간을 설정하는 것으로 getPreSignedUrlExpiration()에서 설정한다.
}
